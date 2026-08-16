package com.leaveflow.app;

import androidx.hilt.work.HiltWorkerFactory;
import com.leaveflow.app.data.repository.SyncRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class LeaveFlowApplication_MembersInjector implements MembersInjector<LeaveFlowApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<SyncRepository> syncRepositoryProvider;

  private LeaveFlowApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<SyncRepository> syncRepositoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.syncRepositoryProvider = syncRepositoryProvider;
  }

  @Override
  public void injectMembers(LeaveFlowApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectSyncRepository(instance, syncRepositoryProvider.get());
  }

  public static MembersInjector<LeaveFlowApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<SyncRepository> syncRepositoryProvider) {
    return new LeaveFlowApplication_MembersInjector(workerFactoryProvider, syncRepositoryProvider);
  }

  @InjectedFieldSignature("com.leaveflow.app.LeaveFlowApplication.workerFactory")
  public static void injectWorkerFactory(LeaveFlowApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.leaveflow.app.LeaveFlowApplication.syncRepository")
  public static void injectSyncRepository(LeaveFlowApplication instance,
      SyncRepository syncRepository) {
    instance.syncRepository = syncRepository;
  }
}
