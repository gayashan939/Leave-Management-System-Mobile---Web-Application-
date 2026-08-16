package com.leaveflow.app.ui.hr;

import com.leaveflow.app.data.repository.BlockedDateRepository;
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
public final class HRViewModel_Factory implements Factory<HRViewModel> {
  private final Provider<LeaveRepository> leaveRepositoryProvider;

  private final Provider<BlockedDateRepository> blockedDateRepositoryProvider;

  private HRViewModel_Factory(Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<BlockedDateRepository> blockedDateRepositoryProvider) {
    this.leaveRepositoryProvider = leaveRepositoryProvider;
    this.blockedDateRepositoryProvider = blockedDateRepositoryProvider;
  }

  @Override
  public HRViewModel get() {
    return newInstance(leaveRepositoryProvider.get(), blockedDateRepositoryProvider.get());
  }

  public static HRViewModel_Factory create(Provider<LeaveRepository> leaveRepositoryProvider,
      Provider<BlockedDateRepository> blockedDateRepositoryProvider) {
    return new HRViewModel_Factory(leaveRepositoryProvider, blockedDateRepositoryProvider);
  }

  public static HRViewModel newInstance(LeaveRepository leaveRepository,
      BlockedDateRepository blockedDateRepository) {
    return new HRViewModel(leaveRepository, blockedDateRepository);
  }
}
