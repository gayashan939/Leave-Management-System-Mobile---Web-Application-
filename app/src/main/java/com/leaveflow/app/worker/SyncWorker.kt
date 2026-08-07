package com.leaveflow.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.leaveflow.app.data.repository.SyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

/**
 * WorkManager worker that synchronises pending local records with the remote API.
 * Triggered automatically when the network becomes available.
 * Supports manual trigger via SyncRepository.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncRepository: SyncRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            syncRepository.syncAll()
            Result.success()
        } catch (e: Exception) {
            // Retry up to 3 times with exponential back-off
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val WORK_NAME_PERIODIC = "LeaveFlowPeriodicSync"
        const val WORK_NAME_ONE_SHOT = "LeaveFlowOneShotSync"

        /**
         * Schedules a periodic sync every 15 minutes (minimum WorkManager interval),
         * constrained to run only when the network is available.
         */
        fun schedulePeriodicSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME_PERIODIC,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        /**
         * Triggers an immediate one-shot sync (e.g. from the manual sync button).
         */
        fun triggerManualSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME_ONE_SHOT,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }
    }
}
