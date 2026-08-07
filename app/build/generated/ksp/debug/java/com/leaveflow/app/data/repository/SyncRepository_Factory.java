package com.leaveflow.app.data.repository;

import android.content.Context;
import com.leaveflow.app.data.local.dao.SyncQueueDao;
import com.leaveflow.app.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "cast"
})
public final class SyncRepository_Factory implements Factory<SyncRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<LeaveRepository> leaveRepositoryProvider;

  private final Provider<ApiService> apiServiceProvider;

  public SyncRepository_Factory(Provider<Context> contextProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<LeaveRepository> leaveRepositoryProvider, Provider<ApiService> apiServiceProvider) {
    this.contextProvider = contextProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.leaveRepositoryProvider = leaveRepositoryProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public SyncRepository get() {
    return newInstance(contextProvider.get(), syncQueueDaoProvider.get(), leaveRepositoryProvider.get(), apiServiceProvider.get());
  }

  public static SyncRepository_Factory create(Provider<Context> contextProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<LeaveRepository> leaveRepositoryProvider, Provider<ApiService> apiServiceProvider) {
    return new SyncRepository_Factory(contextProvider, syncQueueDaoProvider, leaveRepositoryProvider, apiServiceProvider);
  }

  public static SyncRepository newInstance(Context context, SyncQueueDao syncQueueDao,
      LeaveRepository leaveRepository, ApiService apiService) {
    return new SyncRepository(context, syncQueueDao, leaveRepository, apiService);
  }
}
