package com.leaveflow.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Tracks leave balance per employee.
 * Entitlements (defaults): Annual=20, Casual=10, Medical=14, NoPay=unlimited
 */
@Entity(
    tableName = "leave_balances",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LeaveBalanceEntity(
    @PrimaryKey val employeeId: String,
    // Annual Leave
    val annualTotal: Int = 20,
    val annualUsed: Int = 0,
    val annualPending: Int = 0,
    // Casual Leave
    val casualTotal: Int = 10,
    val casualUsed: Int = 0,
    val casualPending: Int = 0,
    // Medical Leave
    val medicalTotal: Int = 14,
    val medicalUsed: Int = 0,
    val medicalPending: Int = 0,
    // No-Pay Leave (no fixed cap – track usage only)
    val noPayUsed: Int = 0,
    val noPayPending: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    val annualRemaining: Int get() = annualTotal - annualUsed - annualPending
    val casualRemaining: Int get() = casualTotal - casualUsed - casualPending
    val medicalRemaining: Int get() = medicalTotal - medicalUsed - medicalPending
}
