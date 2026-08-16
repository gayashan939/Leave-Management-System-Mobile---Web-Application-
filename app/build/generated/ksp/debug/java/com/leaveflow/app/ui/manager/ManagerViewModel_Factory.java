package com.leaveflow.app.ui.manager;

import com.leaveflow.app.data.repository.LeaveRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ManagerViewModel_Factory implements Factory<ManagerViewModel> {
  private final Provider<LeaveRepository> leaveRepositoryProvider;

  private ManagerViewModel_Factory(Provider<LeaveRepository> leaveRepositoryProvider) {
    this.leaveRepositoryProvider = leaveRepositoryProvider;
  }

  @Override
  public ManagerViewModel get() {
    return newInstance(leaveRepositoryProvider.get());
  }

  public static ManagerViewModel_Factory create(Provider<LeaveRepository> leaveRepositoryProvider) {
    return new ManagerViewModel_Factory(leaveRepositoryProvider);
  }

  public static ManagerViewModel newInstance(LeaveRepository leaveRepository) {
    return new ManagerViewModel(leaveRepository);
  }
}
