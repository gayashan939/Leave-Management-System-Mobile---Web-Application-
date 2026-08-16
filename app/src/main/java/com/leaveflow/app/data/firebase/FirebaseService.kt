package com.leaveflow.app.data.firebase

import android.content.Context
import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.Timestamp
import com.google.firebase.storage.FirebaseStorage
import com.leaveflow.app.data.local.entity.LeaveBalanceEntity
import com.leaveflow.app.data.local.entity.LeaveRequestEntity
import com.leaveflow.app.data.local.entity.UserEntity
import com.leaveflow.app.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The single Firebase boundary for the application. Room remains the local
 * source of truth; this class owns cloud authentication and business writes.
 */
@Singleton
class FirebaseService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val USERS = "users"
        const val LEAVE_REQUESTS = "leaveRequests"
        const val LEAVE_BALANCES = "leaveBalances"
        private const val ATTACHMENTS = "leave-attachments"
    }

    val isConfigured: Boolean
        get() = FirebaseApp.getApps(context).isNotEmpty()

    val auth: FirebaseAuth
        get() = configured { FirebaseAuth.getInstance() }

    val firestore: FirebaseFirestore
        get() = configured { FirebaseFirestore.getInstance() }

    val storage: FirebaseStorage
        get() = configured { FirebaseStorage.getInstance() }

    private fun <T> configured(block: () -> T): T {
        check(isConfigured) {
            "Firebase is not configured. Add app/google-services.json and rebuild the app."
        }
        return block()
    }

    suspend fun signIn(email: String, password: String): UserEntity {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: error("Firebase did not return a user account.")
        return getUser(uid) ?: run {
            auth.signOut()
            error("This account has no LeaveFlow user profile.")
        }
    }

    suspend fun getUser(uid: String): UserEntity? {
        val document = firestore.collection(USERS).document(uid).get().await()
        return document.toUserEntity()
    }

    suspend fun getUsersForRole(role: String, uid: String): List<UserEntity> {
        if (role == Constants.ROLE_EMPLOYEE) {
            return listOfNotNull(getUser(uid))
        }
        return firestore.collection(USERS).get().await().documents.mapNotNull { it.toUserEntity() }
    }

    fun leaveQuery(role: String, uid: String): Query {
        val collection = firestore.collection(LEAVE_REQUESTS)
        return if (role == Constants.ROLE_EMPLOYEE) {
            collection.whereEqualTo("employeeId", uid)
        } else {
            collection
        }
    }

    suspend fun getLeaves(role: String, uid: String): List<RemoteLeave> =
        leaveQuery(role, uid).get().await().documents.mapNotNull { it.toRemoteLeave() }

    suspend fun getBalances(role: String, uid: String): List<LeaveBalanceEntity> {
        val snapshots = if (role == Constants.ROLE_EMPLOYEE) {
            listOf(firestore.collection(LEAVE_BALANCES).document(uid).get().await())
        } else {
            firestore.collection(LEAVE_BALANCES).get().await().documents
        }
        return snapshots.mapNotNull { it.toBalanceEntity() }
    }

    /** Idempotently creates a leave and reserves its pending balance. */
    suspend fun createLeave(entity: LeaveRequestEntity) {
        val attachment = uploadAttachment(entity)
        val requestRef = firestore.collection(LEAVE_REQUESTS).document(entity.id)
        val balanceRef = firestore.collection(LEAVE_BALANCES).document(entity.employeeId)

        firestore.runTransaction { transaction ->
            val existing = transaction.get(requestRef)
            if (existing.exists()) {
                if (attachment != null && existing.getString("photoUrl").isNullOrBlank()) {
                    transaction.update(
                        requestRef,
                        mapOf(
                            "photoUrl" to attachment.url,
                            "attachmentPath" to attachment.path,
                            "updatedAt" to FieldValue.serverTimestamp()
                        )
                    )
                }
                return@runTransaction
            }

            val balanceSnapshot = transaction.get(balanceRef)
            val balance = balanceSnapshot.toBalanceEntity()
                ?: LeaveBalanceEntity(employeeId = entity.employeeId)
            val updatedBalance = balance.withPendingDelta(entity.leaveType, entity.numberOfDays)

            transaction.set(requestRef, entity.toCreateMap(attachment))
            transaction.set(balanceRef, updatedBalance.toRemoteMap(), SetOptions.merge())
        }.await()
    }

    /** Idempotently changes status and updates the matching balance atomically. */
    suspend fun updateLeaveStatus(entity: LeaveRequestEntity) {
        val requestRef = firestore.collection(LEAVE_REQUESTS).document(entity.id)
        val balanceRef = firestore.collection(LEAVE_BALANCES).document(entity.employeeId)

        firestore.runTransaction { transaction ->
            val request = transaction.get(requestRef)
            check(request.exists()) { "The leave request does not exist in Firebase." }

            val remoteStatus = request.getString("status")
            if (remoteStatus == entity.status) return@runTransaction
            if (remoteStatus != Constants.STATUS_PENDING) {
                throw SyncConflictException(
                    "The leave request was already processed on another device."
                )
            }

            val balanceSnapshot = transaction.get(balanceRef)
            val balance = balanceSnapshot.toBalanceEntity()
                ?: LeaveBalanceEntity(employeeId = entity.employeeId)
            val updatedBalance = balance.withStatusApplied(
                entity.leaveType,
                entity.numberOfDays,
                entity.status
            )

            transaction.update(
                requestRef,
                mapOf(
                    "status" to entity.status,
                    "managerId" to entity.managerId,
                    "managerComment" to entity.managerComment,
                    "updatedAt" to FieldValue.serverTimestamp(),
                    "revision" to ((request.getLong("revision") ?: 0L) + 1L)
                )
            )
            transaction.set(balanceRef, updatedBalance.toRemoteMap(), SetOptions.merge())
        }.await()
    }

    /** A tombstone ensures that deletion propagates to every signed-in device. */
    suspend fun deleteRejectedLeave(requestId: String) {
        val ref = firestore.collection(LEAVE_REQUESTS).document(requestId)
        var employeeId: String? = null
        firestore.runTransaction { transaction ->
            val request = transaction.get(ref)
            if (!request.exists() || request.getBoolean("deleted") == true) return@runTransaction
            check(request.getString("status") == Constants.STATUS_REJECTED) {
                "Only rejected requests can be deleted."
            }
            employeeId = request.getString("employeeId")
            transaction.update(
                ref,
                mapOf(
                    "deleted" to true,
                    "updatedAt" to FieldValue.serverTimestamp(),
                    "revision" to ((request.getLong("revision") ?: 0L) + 1L)
                )
            )
        }.await()

        employeeId?.let { owner ->
            runCatching { storage.reference.child("$ATTACHMENTS/$owner/$requestId/evidence.jpg").delete().await() }
        }
    }

    private suspend fun uploadAttachment(entity: LeaveRequestEntity): Attachment? {
        val path = entity.photoPath ?: return null
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return Attachment(path = "$ATTACHMENTS/${entity.employeeId}/${entity.id}/evidence.jpg", url = path)
        }
        val file = File(path)
        if (!file.exists()) return null
        val remotePath = "$ATTACHMENTS/${entity.employeeId}/${entity.id}/evidence.jpg"
        val reference = storage.reference.child(remotePath)
        reference.putFile(Uri.fromFile(file)).await()
        return Attachment(remotePath, reference.downloadUrl.await().toString())
    }
}

data class RemoteLeave(
    val entity: LeaveRequestEntity,
    val deleted: Boolean
)

class SyncConflictException(message: String) : IllegalStateException(message)

private data class Attachment(val path: String, val url: String)

private fun DocumentSnapshot.toUserEntity(): UserEntity? {
    if (!exists()) return null
    return UserEntity(
        id = id,
        name = getString("name") ?: return null,
        email = getString("email") ?: return null,
        passwordHash = "",
        role = getString("role") ?: return null,
        department = getString("department") ?: "",
        employeeId = getString("employeeId") ?: "",
        managerId = getString("managerId"),
        createdAt = timestampMillis("createdAt")
    )
}

private fun DocumentSnapshot.toRemoteLeave(): RemoteLeave? {
    if (!exists()) return null
    val employeeId = getString("employeeId") ?: return null
    return RemoteLeave(
        entity = LeaveRequestEntity(
            id = id,
            employeeId = employeeId,
            employeeName = getString("employeeName") ?: "Unknown",
            department = getString("department") ?: "",
            leaveType = getString("leaveType") ?: return null,
            startDate = getString("startDate") ?: return null,
            endDate = getString("endDate") ?: return null,
            reason = getString("reason") ?: "",
            contactNumber = getString("contactNumber") ?: "",
            numberOfDays = (getLong("numberOfDays") ?: 0L).toInt(),
            status = getString("status") ?: Constants.STATUS_PENDING,
            photoPath = getString("photoUrl"),
            latitude = getDouble("latitude"),
            longitude = getDouble("longitude"),
            syncStatus = Constants.SYNC_SYNCED,
            managerId = getString("managerId"),
            managerComment = getString("managerComment"),
            createdAt = timestampMillis("createdAt"),
            updatedAt = timestampMillis("updatedAt")
        ),
        deleted = getBoolean("deleted") == true
    )
}

private fun DocumentSnapshot.toBalanceEntity(): LeaveBalanceEntity? {
    if (!exists()) return null
    return LeaveBalanceEntity(
        employeeId = getString("employeeId") ?: id,
        annualTotal = intValue("annualTotal", 20),
        annualUsed = intValue("annualUsed"),
        annualPending = intValue("annualPending"),
        casualTotal = intValue("casualTotal", 10),
        casualUsed = intValue("casualUsed"),
        casualPending = intValue("casualPending"),
        medicalTotal = intValue("medicalTotal", 14),
        medicalUsed = intValue("medicalUsed"),
        medicalPending = intValue("medicalPending"),
        noPayUsed = intValue("noPayUsed"),
        noPayPending = intValue("noPayPending"),
        lastUpdated = timestampMillis("updatedAt")
    )
}

private fun DocumentSnapshot.intValue(field: String, default: Int = 0): Int =
    (getLong(field) ?: default.toLong()).toInt()

private fun DocumentSnapshot.timestampMillis(field: String): Long = when (val value = get(field)) {
    is Timestamp -> value.toDate().time
    is Number -> value.toLong()
    else -> System.currentTimeMillis()
}

private fun LeaveRequestEntity.toCreateMap(attachment: Attachment?): Map<String, Any?> = mapOf(
    "employeeId" to employeeId,
    "employeeName" to employeeName,
    "department" to department,
    "leaveType" to leaveType,
    "startDate" to startDate,
    "endDate" to endDate,
    "reason" to reason,
    "contactNumber" to contactNumber,
    "numberOfDays" to numberOfDays,
    "status" to Constants.STATUS_PENDING,
    "latitude" to latitude,
    "longitude" to longitude,
    "photoUrl" to attachment?.url,
    "attachmentPath" to attachment?.path,
    "managerId" to null,
    "managerComment" to null,
    "deleted" to false,
    "revision" to 1L,
    "createdAt" to FieldValue.serverTimestamp(),
    "updatedAt" to FieldValue.serverTimestamp()
)

private fun LeaveBalanceEntity.toRemoteMap(): Map<String, Any> = mapOf(
    "employeeId" to employeeId,
    "annualTotal" to annualTotal,
    "annualUsed" to annualUsed,
    "annualPending" to annualPending,
    "casualTotal" to casualTotal,
    "casualUsed" to casualUsed,
    "casualPending" to casualPending,
    "medicalTotal" to medicalTotal,
    "medicalUsed" to medicalUsed,
    "medicalPending" to medicalPending,
    "noPayUsed" to noPayUsed,
    "noPayPending" to noPayPending,
    "updatedAt" to FieldValue.serverTimestamp()
)

private fun LeaveBalanceEntity.withPendingDelta(type: String, days: Int) = when (type) {
    Constants.LEAVE_ANNUAL -> copy(annualPending = annualPending + days)
    Constants.LEAVE_CASUAL -> copy(casualPending = casualPending + days)
    Constants.LEAVE_MEDICAL -> copy(medicalPending = medicalPending + days)
    else -> copy(noPayPending = noPayPending + days)
}

private fun LeaveBalanceEntity.withStatusApplied(type: String, days: Int, status: String): LeaveBalanceEntity {
    val approved = status == Constants.STATUS_APPROVED
    return when (type) {
        Constants.LEAVE_ANNUAL -> copy(
            annualPending = (annualPending - days).coerceAtLeast(0),
            annualUsed = annualUsed + if (approved) days else 0
        )
        Constants.LEAVE_CASUAL -> copy(
            casualPending = (casualPending - days).coerceAtLeast(0),
            casualUsed = casualUsed + if (approved) days else 0
        )
        Constants.LEAVE_MEDICAL -> copy(
            medicalPending = (medicalPending - days).coerceAtLeast(0),
            medicalUsed = medicalUsed + if (approved) days else 0
        )
        else -> copy(
            noPayPending = (noPayPending - days).coerceAtLeast(0),
            noPayUsed = noPayUsed + if (approved) days else 0
        )
    }
}
