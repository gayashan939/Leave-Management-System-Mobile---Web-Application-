package com.leaveflow.app.data.local.dao

import androidx.room.*
import com.leaveflow.app.data.local.entity.LeaveRequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveRequestDao {

    // ── Create ──────────────────────────────────────────────────────────────
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeaveRequest(request: LeaveRequestEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLeaveRequests(requests: List<LeaveRequestEntity>)

    // ── Read ─────────────────────────────────────────────────────────────────
    @Query("SELECT * FROM leave_requests WHERE id = :id LIMIT 1")
    suspend fun getLeaveRequestById(id: String): LeaveRequestEntity?

    @Query("SELECT * FROM leave_requests WHERE employeeId = :employeeId ORDER BY createdAt DESC")
    fun getLeaveRequestsByEmployee(employeeId: String): Flow<List<LeaveRequestEntity>>

    @Query("SELECT * FROM leave_requests WHERE status = 'PENDING' ORDER BY createdAt ASC")
    fun getPendingLeaveRequests(): Flow<List<LeaveRequestEntity>>

    @Query("SELECT * FROM leave_requests ORDER BY createdAt DESC")
    fun getAllLeaveRequests(): Flow<List<LeaveRequestEntity>>

    @Query("SELECT * FROM leave_requests WHERE syncStatus = 'PENDING_SYNC' OR syncStatus = 'FAILED'")
    suspend fun getUnsyncedRequests(): List<LeaveRequestEntity>

    @Query("SELECT COUNT(*) FROM leave_requests WHERE status = :status")
    fun getCountByStatus(status: String): Flow<Int>

    // ── Update ───────────────────────────────────────────────────────────────
    @Update
    suspend fun updateLeaveRequest(request: LeaveRequestEntity)

    @Query("""
        UPDATE leave_requests
        SET status = :status,
            managerId = :managerId,
            managerComment = :comment,
            syncStatus = 'PENDING_SYNC',
            updatedAt = :updatedAt
        WHERE id = :id
    """)
    suspend fun updateLeaveStatus(
        id: String,
        status: String,
        managerId: String,
        comment: String?,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("UPDATE leave_requests SET syncStatus = :syncStatus WHERE id = :id")
    suspend fun updateSyncStatus(id: String, syncStatus: String)

    // ── Delete ───────────────────────────────────────────────────────────────
    @Query("DELETE FROM leave_requests WHERE id = :id AND status = 'REJECTED'")
    suspend fun deleteRejectedRequest(id: String)

    @Delete
    suspend fun deleteLeaveRequest(request: LeaveRequestEntity)

    // ── Summary queries for HR ────────────────────────────────────────────────
    @Query("SELECT COUNT(*) FROM leave_requests WHERE status = 'APPROVED'")
    fun getApprovedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'")
    fun getPendingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM leave_requests WHERE status = 'REJECTED'")
    fun getRejectedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM leave_requests")
    fun getTotalCount(): Flow<Int>
}
