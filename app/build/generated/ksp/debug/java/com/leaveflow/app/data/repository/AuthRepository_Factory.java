package com.leaveflow.app.data.repository;

import android.content.Context;
import com.leaveflow.app.data.firebase.FirebaseService;
import com.leaveflow.app.data.local.dao.UserDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<FirebaseService> firebaseProvider;

  private AuthRepository_Factory(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider, Provider<FirebaseService> firebaseProvider) {
    this.contextProvider = contextProvider;
    this.userDaoProvider = userDaoProvider;
    this.firebaseProvider = firebaseProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(contextProvider.get(), userDaoProvider.get(), firebaseProvider.get());
  }

  public static AuthRepository_Factory create(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider, Provider<FirebaseService> firebaseProvider) {
    return new AuthRepository_Factory(contextProvider, userDaoProvider, firebaseProvider);
  }

  public static AuthRepository newInstance(Context context, UserDao userDao,
      FirebaseService firebase) {
    return new AuthRepository(context, userDao, firebase);
  }
}
