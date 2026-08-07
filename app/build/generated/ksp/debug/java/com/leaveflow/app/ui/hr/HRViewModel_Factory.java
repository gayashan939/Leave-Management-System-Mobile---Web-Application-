package com.leaveflow.app.ui.hr;

import com.leaveflow.app.data.repository.LeaveRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HRViewModel_Factory implements Factory<HRViewModel> {
  private final Provider<LeaveRepository> leaveRepositoryProvider;

  public HRViewModel_Factory(Provider<LeaveRepository> leaveRepositoryProvider) {
    this.leaveRepositoryProvider = leaveRepositoryProvider;
  }

  @Override
  public HRViewModel get() {
    return newInstance(leaveRepositoryProvider.get());
  }

  public static HRViewModel_Factory create(Provider<LeaveRepository> leaveRepositoryProvider) {
    return new HRViewModel_Factory(leaveRepositoryProvider);
  }

  public static HRViewModel newInstance(LeaveRepository leaveRepository) {
    return new HRViewModel(leaveRepository);
  }
}
