package com.leaveflow.app.ui.employee;

import android.content.Context;
import com.leaveflow.app.data.repository.LeaveRepository;
import com.leaveflow.app.data.repository.SyncRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class EmployeeViewModel_Factory implements Factory<EmployeeViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<LeaveRepository> leaveRepositoryProvider;

  private final Provider<SyncRepository> syncRepositoryProvider;

  public EmployeeViewModel_Factory(Provider<Context> contextProvider,
      Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<SyncRepository> syncRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.leaveRepositoryProvider = leaveRepositoryProvider;
    this.syncRepositoryProvider = syncRepositoryProvider;
  }

  @Override
  public EmployeeViewModel get() {
    return newInstance(contextProvider.get(), leaveRepositoryProvider.get(), syncRepositoryProvider.get());
  }

  public static EmployeeViewModel_Factory create(Provider<Context> contextProvider,
      Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<SyncRepository> syncRepositoryProvider) {
    return new EmployeeViewModel_Factory(contextProvider, leaveRepositoryProvider, syncRepositoryProvider);
  }

  public static EmployeeViewModel newInstance(Context context, LeaveRepository leaveRepository,
      SyncRepository syncRepository) {
    return new EmployeeViewModel(context, leaveRepository, syncRepository);
  }
}
