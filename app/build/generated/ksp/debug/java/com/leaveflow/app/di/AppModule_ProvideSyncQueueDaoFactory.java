package com.leaveflow.app.di;

import com.leaveflow.app.data.local.AppDatabase;
import com.leaveflow.app.data.local.dao.SyncQueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class AppModule_ProvideSyncQueueDaoFactory implements Factory<SyncQueueDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideSyncQueueDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SyncQueueDao get() {
    return provideSyncQueueDao(dbProvider.get());
  }

  public static AppModule_ProvideSyncQueueDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideSyncQueueDaoFactory(dbProvider);
  }

  public static SyncQueueDao provideSyncQueueDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSyncQueueDao(db));
  }
}
