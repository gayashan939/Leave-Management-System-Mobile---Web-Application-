package com.leaveflow.app.data.repository

import com.leaveflow.app.data.local.dao.BlockedDateDao
import com.leaveflow.app.data.local.entity.BlockedDateEntity
import com.leaveflow.app.domain.model.BlockedDateRange
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.util.DateUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockedDateRepository @Inject constructor(
    private val blockedDateDao: BlockedDateDao
) {

    /** Live stream of all blocked date ranges. */
    fun getAllBlockedDates(): Flow<List<BlockedDateRange>> =
        blockedDateDao.getAllBlockedDates().map { list -> list.map { it.toDomain() } }

    /**
     * Creates a new blocked date period.
     * Basic validation: startDate must be a valid date and not after endDate.
     */
    suspend fun addBlockedDate(
        startDate: String,
        endDate: String,
        reason: String,
        createdBy: String
    ): Result<Unit> {
        if (!DateUtil.isValidFormat(startDate) || !DateUtil.isValidFormat(endDate))
            return Result.Error("Invalid date format. Use yyyy-MM-dd.")
        if (!DateUtil.isValidRange(startDate, endDate))
            return Result.Error("End date cannot be before start date.")
        if (reason.isBlank())
            return Result.Error("Please provide a reason for blocking this period.")

        val entity = BlockedDateEntity(
            id        = UUID.randomUUID().toString(),
            startDate = startDate,
            endDate   = endDate,
            reason    = reason.trim(),
            createdBy = createdBy,
            createdAt = System.currentTimeMillis()
        )
        blockedDateDao.insertBlockedDate(entity)
        return Result.Success(Unit)
    }

    /** Removes a previously created blocked date range. */
    suspend fun removeBlockedDate(id: String): Result<Unit> {
        blockedDateDao.deleteBlockedDateById(id)
        return Result.Success(Unit)
    }
}

// ── Mapping helpers ────────────────────────────────────────────────────────────

private fun BlockedDateEntity.toDomain() = BlockedDateRange(
    id        = id,
    startDate = startDate,
    endDate   = endDate,
    reason    = reason,
    createdBy = createdBy,
    createdAt = createdAt
)
