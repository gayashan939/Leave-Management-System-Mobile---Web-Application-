package com.leaveflow.app.data.local.dao

import androidx.room.*
import com.leaveflow.app.data.local.entity.SyncQueueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enqueue(item: SyncQueueEntity)

    @Query("SELECT * FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED' ORDER BY createdAt ASC")
    suspend fun getPendingItems(): List<SyncQueueEntity>

    @Query("SELECT * FROM sync_queue ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<SyncQueueEntity>>

    @Query("UPDATE sync_queue SET status = :status, retryCount = retryCount + 1, lastAttempt = :timestamp WHERE id = :id")
    suspend fun updateStatus(id: String, status: String, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM sync_queue WHERE status = 'SYNCED'")
    suspend fun clearSynced()

    @Query("DELETE FROM sync_queue WHERE requestId = :requestId")
    suspend fun removeByRequestId(requestId: String)

    @Query("SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED'")
    fun getPendingCount(): Flow<Int>
}
