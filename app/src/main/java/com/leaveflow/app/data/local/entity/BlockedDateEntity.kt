package com.leaveflow.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an HR-defined period during which leave submission is blocked.
 * Status: Local-only (not synced to Firebase in this version).
 */
@Entity(tableName = "leave_blocked_dates")
data class BlockedDateEntity(
    @PrimaryKey val id: String,
    val startDate: String,      // "yyyy-MM-dd"
    val endDate: String,        // "yyyy-MM-dd"
    val reason: String,
    val createdBy: String,      // HR user display name
    val createdAt: Long = System.currentTimeMillis()
)
