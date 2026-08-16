package com.leaveflow.app.data.local.dao

import androidx.room.*
import com.leaveflow.app.data.local.entity.BlockedDateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedDateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlockedDate(entity: BlockedDateEntity)

    /** Live list of all blocked date ranges, newest first. */
    @Query("SELECT * FROM leave_blocked_dates ORDER BY createdAt DESC")
    fun getAllBlockedDates(): Flow<List<BlockedDateEntity>>

    /** One-shot read used inside submit-leave validation. */
    @Query("SELECT * FROM leave_blocked_dates ORDER BY createdAt DESC")
    suspend fun getAllBlockedDatesOnce(): List<BlockedDateEntity>

    @Query("DELETE FROM leave_blocked_dates WHERE id = :id")
    suspend fun deleteBlockedDateById(id: String)
}
