package com.hyangmoa.feature_fcm;

import com.hyangmoa.core_domain.repository.FcmRepository;
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
    "KotlinInternalInJava"
})
public final class AlarmViewModel_Factory implements Factory<AlarmViewModel> {
  private final Provider<FcmRepository> fcmRepositoryProvider;

  public AlarmViewModel_Factory(Provider<FcmRepository> fcmRepositoryProvider) {
    this.fcmRepositoryProvider = fcmRepositoryProvider;
  }

  @Override
  public AlarmViewModel get() {
    return newInstance(fcmRepositoryProvider.get());
  }

  public static AlarmViewModel_Factory create(Provider<FcmRepository> fcmRepositoryProvider) {
    return new AlarmViewModel_Factory(fcmRepositoryProvider);
  }

  public static AlarmViewModel newInstance(FcmRepository fcmRepository) {
    return new AlarmViewModel(fcmRepository);
  }
}
