package com.leaveflow.app.data.repository;

import android.content.Context;
import com.leaveflow.app.data.local.AppDatabase;
import com.leaveflow.app.data.local.dao.BlockedDateDao;
import com.leaveflow.app.data.local.dao.LeaveBalanceDao;
import com.leaveflow.app.data.local.dao.LeaveRequestDao;
import com.leaveflow.app.data.local.dao.SyncQueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LeaveRepository_Factory implements Factory<LeaveRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<LeaveRequestDao> leaveRequestDaoProvider;

  private final Provider<LeaveBalanceDao> leaveBalanceDaoProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<BlockedDateDao> blockedDateDaoProvider;

  private final Provider<AppDatabase> databaseProvider;

  private LeaveRepository_Factory(Provider<Context> contextProvider,
      Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider, Provider<BlockedDateDao> blockedDateDaoProvider,
      Provider<AppDatabase> databaseProvider) {
    this.contextProvider = contextProvider;
    this.leaveRequestDaoProvider = leaveRequestDaoProvider;
    this.leaveBalanceDaoProvider = leaveBalanceDaoProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.blockedDateDaoProvider = blockedDateDaoProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LeaveRepository get() {
    return newInstance(contextProvider.get(), leaveRequestDaoProvider.get(), leaveBalanceDaoProvider.get(), syncQueueDaoProvider.get(), blockedDateDaoProvider.get(), databaseProvider.get());
  }

  public static LeaveRepository_Factory create(Provider<Context> contextProvider,
      Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider, Provider<BlockedDateDao> blockedDateDaoProvider,
      Provider<AppDatabase> databaseProvider) {
    return new LeaveRepository_Factory(contextProvider, leaveRequestDaoProvider, leaveBalanceDaoProvider, syncQueueDaoProvider, blockedDateDaoProvider, databaseProvider);
  }

  public static LeaveRepository newInstance(Context context, LeaveRequestDao leaveRequestDao,
      LeaveBalanceDao leaveBalanceDao, SyncQueueDao syncQueueDao, BlockedDateDao blockedDateDao,
      AppDatabase database) {
    return new LeaveRepository(context, leaveRequestDao, leaveBalanceDao, syncQueueDao, blockedDateDao, database);
  }
}
