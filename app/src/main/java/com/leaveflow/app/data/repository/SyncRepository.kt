package com.leaveflow.app.data.repository

import android.content.Context
import com.leaveflow.app.data.local.dao.SyncQueueDao
import com.leaveflow.app.data.remote.ApiService
import com.leaveflow.app.data.remote.model.LeaveRequestPayload
import com.leaveflow.app.data.remote.model.StatusUpdateRequest
import com.leaveflow.app.util.NetworkUtil
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val syncQueueDao: SyncQueueDao,
    private val leaveRepository: LeaveRepository,
    private val apiService: ApiService
) {

    private val gson = Gson()

    val pendingSyncCount: Flow<Int> = syncQueueDao.getPendingCount()

    /**
     * Processes all pending/failed items in the sync queue.
     * Called by SyncWorker when network becomes available.
     */
    suspend fun syncAll() {
        if (!NetworkUtil.isNetworkAvailable(context)) return

        val items = syncQueueDao.getPendingItems()
        for (item in items) {
            try {
                val success = when (item.operation) {
                    "CREATE" -> syncCreate(item.requestId, item.payload)
                    "UPDATE" -> syncUpdate(item.payload)
                    "DELETE" -> syncDelete(item.requestId)
                    else     -> false
                }

                if (success) {
                    syncQueueDao.updateStatus(item.id, "SYNCED")
                    leaveRepository.updateSyncStatus(item.requestId, "SYNCED")
                } else {
                    syncQueueDao.updateStatus(item.id, "FAILED")
                    leaveRepository.updateSyncStatus(item.requestId, "FAILED")
                }
            } catch (e: Exception) {
                syncQueueDao.updateStatus(item.id, "FAILED")
                leaveRepository.updateSyncStatus(item.requestId, "FAILED")
            }
        }

        // Clean up successfully synced queue items
        syncQueueDao.clearSynced()
    }

    private suspend fun syncCreate(requestId: String, payload: String): Boolean {
        return try {
            val p = gson.fromJson(payload, Map::class.java)
            val request = LeaveRequestPayload(
                id            = p["id"] as? String ?: requestId,
                employeeId    = p["employee_id"] as? String ?: "",
                employeeName  = p["employee_name"] as? String ?: "",
                department    = p["department"] as? String ?: "",
                leaveType     = p["leave_type"] as? String ?: "",
                startDate     = p["start_date"] as? String ?: "",
                endDate       = p["end_date"] as? String ?: "",
                reason        = p["reason"] as? String ?: "",
                contactNumber = p["contact_number"] as? String ?: "",
                numberOfDays  = (p["number_of_days"] as? Double)?.toInt() ?: 0,
                latitude      = p["latitude"] as? Double,
                longitude     = p["longitude"] as? Double
            )
            val response = apiService.submitLeaveRequest(request)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun syncUpdate(payload: String): Boolean {
        return try {
            val p = gson.fromJson(payload, Map::class.java)
            val id = p["id"] as? String ?: return false
            val update = StatusUpdateRequest(
                status        = p["status"] as? String ?: "",
                managerId     = p["manager_id"] as? String ?: "",
                managerComment = p["manager_comment"] as? String
            )
            val response = apiService.updateLeaveStatus(id, update)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun syncDelete(requestId: String): Boolean {
        return try {
            val response = apiService.deleteLeaveRequest(requestId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
