package com.leaveflow.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Queue of records that need to be synced to the remote API.
 * Operation: CREATE | UPDATE | DELETE
 * Status:    PENDING_SYNC | SYNCED | FAILED
 */
@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey val id: String,
    val requestId: String,
    val operation: String,          // CREATE | UPDATE | DELETE
    val payload: String,            // JSON string of the payload
    val retryCount: Int = 0,
    val lastAttempt: Long? = null,
    val status: String = "PENDING_SYNC",  // PENDING_SYNC | SYNCED | FAILED
    val createdAt: Long = System.currentTimeMillis()
)
