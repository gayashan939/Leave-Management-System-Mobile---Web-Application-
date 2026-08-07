package com.leaveflow.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class LeaveFlowApplication_MembersInjector implements MembersInjector<LeaveFlowApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public LeaveFlowApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<LeaveFlowApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new LeaveFlowApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(LeaveFlowApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.leaveflow.app.LeaveFlowApplication.workerFactory")
  public static void injectWorkerFactory(LeaveFlowApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
