package com.speedmenu.tablet.data.repository;

import com.speedmenu.tablet.data.datasource.local.LocalDataSource;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource;
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
public final class MenuRepositoryImpl_Factory implements Factory<MenuRepositoryImpl> {
  private final Provider<RemoteDataSource> remoteDataSourceProvider;

  private final Provider<LocalDataSource> localDataSourceProvider;

  public MenuRepositoryImpl_Factory(Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<LocalDataSource> localDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
    this.localDataSourceProvider = localDataSourceProvider;
  }

  @Override
  public MenuRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get(), localDataSourceProvider.get());
  }

  public static MenuRepositoryImpl_Factory create(
      Provider<RemoteDataSource> remoteDataSourceProvider,
      Provider<LocalDataSource> localDataSourceProvider) {
    return new MenuRepositoryImpl_Factory(remoteDataSourceProvider, localDataSourceProvider);
  }

  public static MenuRepositoryImpl newInstance(RemoteDataSource remoteDataSource,
      LocalDataSource localDataSource) {
    return new MenuRepositoryImpl(remoteDataSource, localDataSource);
  }
}
