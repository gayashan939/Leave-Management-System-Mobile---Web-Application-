package com.leaveflow.app.data.repository;

import com.leaveflow.app.data.firebase.FirebaseService;
import com.leaveflow.app.data.local.dao.LeaveBalanceDao;
import com.leaveflow.app.data.local.dao.LeaveRequestDao;
import com.leaveflow.app.data.local.dao.SyncQueueDao;
import com.leaveflow.app.data.local.dao.UserDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class SyncRepository_Factory implements Factory<SyncRepository> {
  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<LeaveRequestDao> leaveRequestDaoProvider;

  private final Provider<LeaveBalanceDao> leaveBalanceDaoProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<LeaveRepository> leaveRepositoryProvider;

  private final Provider<FirebaseService> firebaseProvider;

  private SyncRepository_Factory(Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider, Provider<UserDao> userDaoProvider,
      Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<FirebaseService> firebaseProvider) {
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.leaveRequestDaoProvider = leaveRequestDaoProvider;
    this.leaveBalanceDaoProvider = leaveBalanceDaoProvider;
    this.userDaoProvider = userDaoProvider;
    this.leaveRepositoryProvider = leaveRepositoryProvider;
    this.firebaseProvider = firebaseProvider;
  }

  @Override
  public SyncRepository get() {
    return newInstance(syncQueueDaoProvider.get(), leaveRequestDaoProvider.get(), leaveBalanceDaoProvider.get(), userDaoProvider.get(), leaveRepositoryProvider.get(), firebaseProvider.get());
  }

  public static SyncRepository_Factory create(Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<LeaveRequestDao> leaveRequestDaoProvider,
      Provider<LeaveBalanceDao> leaveBalanceDaoProvider, Provider<UserDao> userDaoProvider,
      Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<FirebaseService> firebaseProvider) {
    return new SyncRepository_Factory(syncQueueDaoProvider, leaveRequestDaoProvider, leaveBalanceDaoProvider, userDaoProvider, leaveRepositoryProvider, firebaseProvider);
  }

  public static SyncRepository newInstance(SyncQueueDao syncQueueDao,
      LeaveRequestDao leaveRequestDao, LeaveBalanceDao leaveBalanceDao, UserDao userDao,
      LeaveRepository leaveRepository, FirebaseService firebase) {
    return new SyncRepository(syncQueueDao, leaveRequestDao, leaveBalanceDao, userDao, leaveRepository, firebase);
  }
}
