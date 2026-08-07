package com.leaveflow.app.data.repository

import android.content.Context
import com.leaveflow.app.data.local.dao.LeaveBalanceDao
import com.leaveflow.app.data.local.dao.LeaveRequestDao
import com.leaveflow.app.data.local.dao.SyncQueueDao
import com.leaveflow.app.data.local.entity.LeaveBalanceEntity
import com.leaveflow.app.data.local.entity.LeaveRequestEntity
import com.leaveflow.app.data.local.entity.SyncQueueEntity
import com.leaveflow.app.data.local.AppDatabase
import androidx.room.withTransaction
import com.leaveflow.app.domain.model.LeaveBalance
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.util.Constants
import com.leaveflow.app.util.DateUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import com.leaveflow.app.worker.SyncWorker

@Singleton
class LeaveRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val leaveRequestDao: LeaveRequestDao,
    private val leaveBalanceDao: LeaveBalanceDao,
    private val syncQueueDao: SyncQueueDao,
    private val database: AppDatabase
) {

    // ── Read ──────────────────────────────────────────────────────────────────

    fun getLeavesByEmployee(employeeId: String): Flow<List<LeaveRequest>> =
        leaveRequestDao.getLeaveRequestsByEmployee(employeeId).map { list ->
            list.map { it.toDomain() }
        }

    fun getPendingLeaveRequests(): Flow<List<LeaveRequest>> =
        leaveRequestDao.getPendingLeaveRequests().map { list ->
            list.map { it.toDomain() }
        }

    fun getAllLeaveRequests(): Flow<List<LeaveRequest>> =
        leaveRequestDao.getAllLeaveRequests().map { list ->
            list.map { it.toDomain() }
        }

    fun getBalanceByEmployee(employeeId: String): Flow<LeaveBalance?> =
        leaveBalanceDao.getBalanceByEmployee(employeeId).map { it?.toDomain() }

    fun getApprovedCount(): Flow<Int> = leaveRequestDao.getApprovedCount()
    fun getPendingCount(): Flow<Int>  = leaveRequestDao.getPendingCount()
    fun getRejectedCount(): Flow<Int> = leaveRequestDao.getRejectedCount()
    fun getTotalCount(): Flow<Int>    = leaveRequestDao.getTotalCount()

    // ── Create ────────────────────────────────────────────────────────────────

    suspend fun submitLeaveRequest(
        employeeId: String,
        employeeName: String,
        department: String,
        leaveType: String,
        startDate: String,
        endDate: String,
        reason: String,
        contactNumber: String,
        photoPath: String?,
        latitude: Double?,
        longitude: Double?
    ): Result<LeaveRequest> {
        // Validation
        if (reason.length < Constants.MIN_REASON_LENGTH)
            return Result.Error("Reason must be at least ${Constants.MIN_REASON_LENGTH} characters.")
        if (!DateUtil.isValidFormat(startDate) || !DateUtil.isValidFormat(endDate))
            return Result.Error("Invalid date format. Use yyyy-MM-dd.")
        if (!DateUtil.isValidRange(startDate, endDate))
            return Result.Error("End date cannot be before start date.")

        val days = DateUtil.calculateDays(startDate, endDate)

        // Balance check (skip for NOPAY)
        if (leaveType != Constants.LEAVE_NOPAY) {
            val balance = leaveBalanceDao.getBalanceByEmployeeOnce(employeeId)
            val available = when (leaveType) {
                Constants.LEAVE_ANNUAL  -> (balance?.annualTotal  ?: 0) - (balance?.annualUsed  ?: 0) - (balance?.annualPending  ?: 0)
                Constants.LEAVE_CASUAL  -> (balance?.casualTotal  ?: 0) - (balance?.casualUsed  ?: 0) - (balance?.casualPending  ?: 0)
                Constants.LEAVE_MEDICAL -> (balance?.medicalTotal ?: 0) - (balance?.medicalUsed ?: 0) - (balance?.medicalPending ?: 0)
                else -> Int.MAX_VALUE
            }
            if (days > available)
                return Result.Error("Insufficient $leaveType leave balance. Available: $available day(s), Requested: $days day(s).")
        }

        val requestId = UUID.randomUUID().toString()
        val entity = LeaveRequestEntity(
            id            = requestId,
            employeeId    = employeeId,
            employeeName  = employeeName,
            department    = department,
            leaveType     = leaveType,
            startDate     = startDate,
            endDate       = endDate,
            reason        = reason,
            contactNumber = contactNumber,
            numberOfDays  = days,
            status        = Constants.STATUS_PENDING,
            photoPath     = photoPath,
            latitude      = latitude,
            longitude     = longitude,
            syncStatus    = Constants.SYNC_PENDING
        )

        database.withTransaction {
            leaveRequestDao.insertLeaveRequest(entity)
            leaveBalanceDao.addPendingDays(employeeId, leaveType, days)
            syncQueueDao.enqueue(
                SyncQueueEntity(
                    id        = UUID.randomUUID().toString(),
                    requestId = requestId,
                    operation = "CREATE",
                    payload   = entity.toSyncPayload()
                )
            )
        }
        SyncWorker.triggerManualSync(context)

        return Result.Success(entity.toDomain())
    }

    // ── Update (Manager approval/rejection) ──────────────────────────────────

    suspend fun updateLeaveStatus(
        requestId: String,
        status: String,
        managerId: String,
        comment: String?
    ): Result<Unit> {
        val request = leaveRequestDao.getLeaveRequestById(requestId)
            ?: return Result.Error("Leave request not found.")
        if (request.status != Constants.STATUS_PENDING) {
            return Result.Error("This leave request has already been processed.")
        }
        if (status != Constants.STATUS_APPROVED && status != Constants.STATUS_REJECTED) {
            return Result.Error("Status must be APPROVED or REJECTED.")
        }

        database.withTransaction {
            leaveRequestDao.updateLeaveStatus(requestId, status, managerId, comment)
            when (status) {
                Constants.STATUS_APPROVED ->
                    leaveBalanceDao.approveDays(request.employeeId, request.leaveType, request.numberOfDays)
                Constants.STATUS_REJECTED ->
                    leaveBalanceDao.rejectDays(request.employeeId, request.leaveType, request.numberOfDays)
            }
            syncQueueDao.enqueue(
                SyncQueueEntity(
                    id        = UUID.randomUUID().toString(),
                    requestId = requestId,
                    operation = "UPDATE",
                    payload   = "{}"
                )
            )
        }
        SyncWorker.triggerManualSync(context)

        return Result.Success(Unit)
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    suspend fun deleteRejectedRequest(requestId: String): Result<Unit> {
        val request = leaveRequestDao.getLeaveRequestById(requestId)
            ?: return Result.Error("Leave request not found.")
        if (request.status != Constants.STATUS_REJECTED) {
            return Result.Error("Only rejected requests can be deleted.")
        }
        database.withTransaction {
            leaveRequestDao.deleteRejectedRequest(requestId)
            syncQueueDao.removeByRequestId(requestId)
            syncQueueDao.enqueue(
                SyncQueueEntity(
                    id        = UUID.randomUUID().toString(),
                    requestId = requestId,
                    operation = "DELETE",
                    payload   = "{}"
                )
            )
        }
        SyncWorker.triggerManualSync(context)
        return Result.Success(Unit)
    }

    suspend fun getUnsyncedRequests(): List<LeaveRequestEntity> =
        leaveRequestDao.getUnsyncedRequests()

    suspend fun updateSyncStatus(id: String, syncStatus: String) =
        leaveRequestDao.updateSyncStatus(id, syncStatus)

    suspend fun ensureBalanceExists(employeeId: String) {
        val existing = leaveBalanceDao.getBalanceByEmployeeOnce(employeeId)
        if (existing == null) {
            leaveBalanceDao.insertBalance(LeaveBalanceEntity(employeeId = employeeId))
        }
    }
}

// ── Mapping helpers ───────────────────────────────────────────────────────────

private fun LeaveRequestEntity.toDomain() = LeaveRequest(
    id            = id,
    employeeId    = employeeId,
    employeeName  = employeeName,
    department    = department,
    leaveType     = leaveType,
    startDate     = startDate,
    endDate       = endDate,
    reason        = reason,
    contactNumber = contactNumber,
    numberOfDays  = numberOfDays,
    status        = status,
    photoPath     = photoPath,
    latitude      = latitude,
    longitude     = longitude,
    syncStatus    = syncStatus,
    managerId     = managerId,
    managerComment = managerComment,
    createdAt     = createdAt
)

private fun LeaveBalanceEntity.toDomain() = LeaveBalance(
    employeeId    = employeeId,
    annualTotal   = annualTotal,   annualUsed  = annualUsed,   annualPending  = annualPending,
    casualTotal   = casualTotal,   casualUsed  = casualUsed,   casualPending  = casualPending,
    medicalTotal  = medicalTotal,  medicalUsed = medicalUsed,  medicalPending = medicalPending,
    noPayUsed     = noPayUsed,     noPayPending = noPayPending
)

private fun LeaveRequestEntity.toSyncPayload(): String = """
    {"id":"$id","employee_id":"$employeeId","employee_name":"$employeeName",
    "department":"$department","leave_type":"$leaveType","start_date":"$startDate",
    "end_date":"$endDate","reason":"${reason.replace("\"","'")}","contact_number":"$contactNumber",
    "number_of_days":$numberOfDays,"status":"$status",
    "latitude":${latitude ?: "null"},"longitude":${longitude ?: "null"}}
""".trimIndent().replace("\n", "")
