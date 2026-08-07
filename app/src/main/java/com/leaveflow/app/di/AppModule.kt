package com.leaveflow.app.di

import android.content.Context
import androidx.work.WorkManager
import com.leaveflow.app.data.local.AppDatabase
import com.leaveflow.app.data.local.dao.LeaveBalanceDao
import com.leaveflow.app.data.local.dao.LeaveRequestDao
import com.leaveflow.app.data.local.dao.SyncQueueDao
import com.leaveflow.app.data.local.dao.UserDao
import com.leaveflow.app.data.remote.ApiService
import com.leaveflow.app.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ── Room Database ─────────────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideLeaveRequestDao(db: AppDatabase): LeaveRequestDao = db.leaveRequestDao()

    @Provides
    fun provideLeaveBalanceDao(db: AppDatabase): LeaveBalanceDao = db.leaveBalanceDao()

    @Provides
    fun provideSyncQueueDao(db: AppDatabase): SyncQueueDao = db.syncQueueDao()

    // ── Retrofit ──────────────────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitClient.apiService

    // ── WorkManager ───────────────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}
