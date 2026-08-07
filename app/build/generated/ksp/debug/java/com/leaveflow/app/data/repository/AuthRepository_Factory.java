package com.leaveflow.app.data.repository;

import android.content.Context;
import com.leaveflow.app.data.local.dao.UserDao;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<UserDao> userDaoProvider;

  public AuthRepository_Factory(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider) {
    this.contextProvider = contextProvider;
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(contextProvider.get(), userDaoProvider.get());
  }

  public static AuthRepository_Factory create(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider) {
    return new AuthRepository_Factory(contextProvider, userDaoProvider);
  }

  public static AuthRepository newInstance(Context context, UserDao userDao) {
    return new AuthRepository(context, userDao);
  }
}
