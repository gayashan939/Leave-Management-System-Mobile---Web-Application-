package com.leaveflow.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.leaveflow.app.data.firebase.FirebaseService
import com.leaveflow.app.data.firebase.RemoteLeave
import com.leaveflow.app.data.firebase.SyncConflictException
import com.leaveflow.app.data.local.dao.LeaveBalanceDao
import com.leaveflow.app.data.local.dao.LeaveRequestDao
import com.leaveflow.app.data.local.dao.SyncQueueDao
import com.leaveflow.app.data.local.dao.UserDao
import com.leaveflow.app.data.local.entity.UserEntity
import com.leaveflow.app.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/** Coordinates the explicit Room outbox with Firebase's realtime cloud store. */
@Singleton
class SyncRepository @Inject constructor(
    private val syncQueueDao: SyncQueueDao,
    private val leaveRequestDao: LeaveRequestDao,
    private val leaveBalanceDao: LeaveBalanceDao,
    private val userDao: UserDao,
    private val leaveRepository: LeaveRepository,
    private val firebase: FirebaseService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val syncMutex = Mutex()
    private val listeners = mutableListOf<ListenerRegistration>()
    private var authListener: FirebaseAuth.AuthStateListener? = null

    val pendingSyncCount: Flow<Int> = syncQueueDao.getPendingCount()

    /**
     * Pushes queued local operations, then reconciles Room from Firestore.
     * Returns false when WorkManager should retry.
     */
    suspend fun syncAll(): Boolean = syncMutex.withLock {
        if (!firebase.isConfigured) return@withLock true
        val firebaseUser = firebase.auth.currentUser ?: return@withLock true

        var allSucceeded = true
        for (item in syncQueueDao.getPendingItems()) {
            val result = runCatching {
                when (item.operation) {
                    "CREATE" -> {
                        val request = leaveRequestDao.getLeaveRequestById(item.requestId)
                            ?: error("Local leave request is missing.")
                        firebase.createLeave(request)
                    }
                    "UPDATE" -> {
                        val request = leaveRequestDao.getLeaveRequestById(item.requestId)
                            ?: error("Local leave request is missing.")
                        firebase.updateLeaveStatus(request)
                    }
                    "DELETE" -> firebase.deleteRejectedLeave(item.requestId)
                    else -> error("Unsupported sync operation: ${item.operation}")
                }
            }
            // A manager on another device won the status race. Drop the local
            // operation and let the following pull apply the authoritative value.
            val success = result.isSuccess || result.exceptionOrNull()?.isSyncConflict() == true

            if (success) {
                syncQueueDao.updateStatus(item.id, Constants.SYNC_SYNCED)
                leaveRepository.updateSyncStatus(item.requestId, Constants.SYNC_SYNCED)
            } else {
                allSucceeded = false
                syncQueueDao.updateStatus(item.id, Constants.SYNC_FAILED)
                leaveRepository.updateSyncStatus(item.requestId, Constants.SYNC_FAILED)
            }
        }
        syncQueueDao.clearSynced()

        if (allSucceeded) {
            runCatching { pullRemote(firebaseUser.uid) }
                .onFailure { allSucceeded = false }
        }
        allSucceeded
    }

    /** Starts role-scoped foreground listeners and restarts them after login/logout. */
    fun startRealtimeSync() {
        if (!firebase.isConfigured || authListener != null) return
        val listener = FirebaseAuth.AuthStateListener { auth ->
            clearSnapshotListeners()
            val uid = auth.currentUser?.uid ?: return@AuthStateListener
            scope.launch {
                syncMutex.withLock {
                    runCatching { pullRemote(uid) }
                    registerSnapshotListeners(uid)
                }
            }
        }
        authListener = listener
        firebase.auth.addAuthStateListener(listener)
    }

    private suspend fun pullRemote(uid: String) {
        val profile = firebase.getUser(uid) ?: error("Firebase user profile is missing.")
        userDao.insertUsers(firebase.getUsersForRole(profile.role, uid))

        firebase.getLeaves(profile.role, uid).forEach { applyRemoteLeave(it) }

        // Do not overwrite optimistic local balance changes while an outbox item exists.
        if (syncQueueDao.getPendingCountOnce() == 0) {
            firebase.getBalances(profile.role, uid).forEach { leaveBalanceDao.insertBalance(it) }
        }
    }

    private suspend fun applyRemoteLeave(remote: RemoteLeave) {
        val local = leaveRequestDao.getLeaveRequestById(remote.entity.id)
        if (local?.syncStatus == Constants.SYNC_PENDING || local?.syncStatus == Constants.SYNC_FAILED) {
            return
        }
        if (remote.deleted) {
            leaveRequestDao.deleteLeaveRequestById(remote.entity.id)
            return
        }

        // A profile listener normally inserts this first. The fallback protects
        // the Room foreign key if callbacks arrive in a different order.
        if (userDao.getUserById(remote.entity.employeeId) == null) {
            userDao.insertUser(
                UserEntity(
                    id = remote.entity.employeeId,
                    name = remote.entity.employeeName,
                    email = "${remote.entity.employeeId}@pending.leaveflow",
                    role = Constants.ROLE_EMPLOYEE,
                    department = remote.entity.department,
                    employeeId = remote.entity.employeeId
                )
            )
        }
        leaveRequestDao.insertLeaveRequest(remote.entity)
    }

    private suspend fun registerSnapshotListeners(uid: String) {
        val profile = firebase.getUser(uid) ?: return
        val refresh: (Any?, Exception?) -> Unit = { _, error ->
            if (error == null) {
                scope.launch {
                    syncMutex.withLock { runCatching { pullRemote(uid) } }
                }
            }
        }

        listeners += firebase.leaveQuery(profile.role, uid).addSnapshotListener(refresh)
        if (profile.role == Constants.ROLE_EMPLOYEE) {
            listeners += firebase.firestore.collection(FirebaseService.USERS)
                .document(uid).addSnapshotListener(refresh)
            listeners += firebase.firestore.collection(FirebaseService.LEAVE_BALANCES)
                .document(uid).addSnapshotListener(refresh)
        } else {
            listeners += firebase.firestore.collection(FirebaseService.USERS)
                .addSnapshotListener(refresh)
            listeners += firebase.firestore.collection(FirebaseService.LEAVE_BALANCES)
                .addSnapshotListener(refresh)
        }
    }

    private fun clearSnapshotListeners() {
        listeners.forEach { it.remove() }
        listeners.clear()
    }
}

private fun Throwable.isSyncConflict(): Boolean =
    generateSequence(this) { it.cause }.any { it is SyncConflictException }
