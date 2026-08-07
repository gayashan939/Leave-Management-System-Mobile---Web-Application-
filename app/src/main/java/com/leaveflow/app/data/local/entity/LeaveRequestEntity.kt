package com.leaveflow.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a leave request stored locally in Room.
 * Status:     PENDING | APPROVED | REJECTED
 * SyncStatus: PENDING_SYNC | SYNCED | FAILED
 * LeaveType:  ANNUAL | CASUAL | MEDICAL | NOPAY
 */
@Entity(
    tableName = "leave_requests",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["employeeId"])]
)
data class LeaveRequestEntity(
    @PrimaryKey val id: String,                   // UUID – also used as API unique key
    val employeeId: String,                        // FK → users.id
    val employeeName: String,
    val department: String,
    val leaveType: String,                         // ANNUAL | CASUAL | MEDICAL | NOPAY
    val startDate: String,                         // "yyyy-MM-dd"
    val endDate: String,                           // "yyyy-MM-dd"
    val reason: String,
    val contactNumber: String,
    val numberOfDays: Int,
    val status: String = "PENDING",                // PENDING | APPROVED | REJECTED
    val photoPath: String? = null,                 // Absolute path in private storage
    val latitude: Double? = null,
    val longitude: Double? = null,
    val syncStatus: String = "PENDING_SYNC",       // PENDING_SYNC | SYNCED | FAILED
    val managerId: String? = null,
    val managerComment: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
