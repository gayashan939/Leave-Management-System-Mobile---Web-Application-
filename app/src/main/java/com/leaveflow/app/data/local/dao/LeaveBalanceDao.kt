package com.leaveflow.app.data.local.dao

import androidx.room.*
import com.leaveflow.app.data.local.entity.LeaveBalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaveBalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balance: LeaveBalanceEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBalances(balances: List<LeaveBalanceEntity>)

    @Query("SELECT * FROM leave_balances WHERE employeeId = :employeeId LIMIT 1")
    fun getBalanceByEmployee(employeeId: String): Flow<LeaveBalanceEntity?>

    @Query("SELECT * FROM leave_balances WHERE employeeId = :employeeId LIMIT 1")
    suspend fun getBalanceByEmployeeOnce(employeeId: String): LeaveBalanceEntity?

    @Query("SELECT * FROM leave_balances")
    fun getAllBalances(): Flow<List<LeaveBalanceEntity>>

    @Update
    suspend fun updateBalance(balance: LeaveBalanceEntity)

    /**
     * Increments pending days when a new leave request is submitted.
     */
    @Query("""
        UPDATE leave_balances
        SET annualPending  = CASE WHEN :leaveType = 'ANNUAL'  THEN annualPending  + :days ELSE annualPending  END,
            casualPending  = CASE WHEN :leaveType = 'CASUAL'  THEN casualPending  + :days ELSE casualPending  END,
            medicalPending = CASE WHEN :leaveType = 'MEDICAL' THEN medicalPending + :days ELSE medicalPending END,
            noPayPending   = CASE WHEN :leaveType = 'NOPAY'   THEN noPayPending   + :days ELSE noPayPending   END,
            lastUpdated    = :timestamp
        WHERE employeeId = :employeeId
    """)
    suspend fun addPendingDays(
        employeeId: String,
        leaveType: String,
        days: Int,
        timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Moves days from pending → used when a leave request is approved.
     */
    @Query("""
        UPDATE leave_balances
        SET annualPending  = CASE WHEN :leaveType = 'ANNUAL'  THEN MAX(0, annualPending  - :days) ELSE annualPending  END,
            annualUsed     = CASE WHEN :leaveType = 'ANNUAL'  THEN annualUsed  + :days ELSE annualUsed  END,
            casualPending  = CASE WHEN :leaveType = 'CASUAL'  THEN MAX(0, casualPending  - :days) ELSE casualPending  END,
            casualUsed     = CASE WHEN :leaveType = 'CASUAL'  THEN casualUsed  + :days ELSE casualUsed  END,
            medicalPending = CASE WHEN :leaveType = 'MEDICAL' THEN MAX(0, medicalPending - :days) ELSE medicalPending END,
            medicalUsed    = CASE WHEN :leaveType = 'MEDICAL' THEN medicalUsed + :days ELSE medicalUsed END,
            noPayPending   = CASE WHEN :leaveType = 'NOPAY'   THEN MAX(0, noPayPending   - :days) ELSE noPayPending   END,
            noPayUsed      = CASE WHEN :leaveType = 'NOPAY'   THEN noPayUsed   + :days ELSE noPayUsed   END,
            lastUpdated    = :timestamp
        WHERE employeeId = :employeeId
    """)
    suspend fun approveDays(
        employeeId: String,
        leaveType: String,
        days: Int,
        timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Returns reserved (pending) days to available balance when a request is rejected.
     */
    @Query("""
        UPDATE leave_balances
        SET annualPending  = CASE WHEN :leaveType = 'ANNUAL'  THEN MAX(0, annualPending  - :days) ELSE annualPending  END,
            casualPending  = CASE WHEN :leaveType = 'CASUAL'  THEN MAX(0, casualPending  - :days) ELSE casualPending  END,
            medicalPending = CASE WHEN :leaveType = 'MEDICAL' THEN MAX(0, medicalPending - :days) ELSE medicalPending END,
            noPayPending   = CASE WHEN :leaveType = 'NOPAY'   THEN MAX(0, noPayPending   - :days) ELSE noPayPending   END,
            lastUpdated    = :timestamp
        WHERE employeeId = :employeeId
    """)
    suspend fun rejectDays(
        employeeId: String,
        leaveType: String,
        days: Int,
        timestamp: Long = System.currentTimeMillis()
    )
}
