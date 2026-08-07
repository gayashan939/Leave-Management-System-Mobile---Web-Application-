package com.leaveflow.app.data.repository;

import com.leaveflow.app.data.local.dao.LeaveBalanceDao;
import com.leaveflow.app.data.local.dao.LeaveRequestDao;
import com.leaveflow.app.data.local.dao.SyncQueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
    "cast"
})
public final class LeaveRepository_Factory implements Factory<LeaveRepository> {
  private final Provider<LeaveRequestDao> leaveRequestDaoProvider;

  private final Provider<LeaveBalanceDao> leaveBalanceDaoProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  public LeaveRepository_Factory(Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider) {
    this.leaveRequestDaoProvider = leaveRequestDaoProvider;
    this.leaveBalanceDaoProvider = leaveBalanceDaoProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
  }

  @Override
  public LeaveRepository get() {
    return newInstance(leaveRequestDaoProvider.get(), leaveBalanceDaoProvider.get(), syncQueueDaoProvider.get());
  }

  public static LeaveRepository_Factory create(Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider) {
    return new LeaveRepository_Factory(leaveRequestDaoProvider, leaveBalanceDaoProvider, syncQueueDaoProvider);
  }

  public static LeaveRepository newInstance(LeaveRequestDao leaveRequestDao,
      LeaveBalanceDao leaveBalanceDao, SyncQueueDao syncQueueDao) {
    return new LeaveRepository(leaveRequestDao, leaveBalanceDao, syncQueueDao);
  }
}
