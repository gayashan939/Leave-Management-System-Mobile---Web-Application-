package com.leaveflow.app.di;

import com.leaveflow.app.data.local.AppDatabase;
import com.leaveflow.app.data.local.dao.LeaveRequestDao;
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
public final class AppModule_ProvideLeaveRequestDaoFactory implements Factory<LeaveRequestDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideLeaveRequestDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LeaveRequestDao get() {
    return provideLeaveRequestDao(dbProvider.get());
  }

  public static AppModule_ProvideLeaveRequestDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideLeaveRequestDaoFactory(dbProvider);
  }

  public static LeaveRequestDao provideLeaveRequestDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLeaveRequestDao(db));
  }
}
