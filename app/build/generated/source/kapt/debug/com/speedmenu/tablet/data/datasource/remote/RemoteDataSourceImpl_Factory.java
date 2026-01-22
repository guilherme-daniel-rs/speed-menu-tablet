package com.speedmenu.tablet.data.datasource.remote;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
    "KotlinInternalInJava"
})
public final class RemoteDataSourceImpl_Factory implements Factory<RemoteDataSourceImpl> {
  @Override
  public RemoteDataSourceImpl get() {
    return newInstance();
  }

  public static RemoteDataSourceImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RemoteDataSourceImpl newInstance() {
    return new RemoteDataSourceImpl();
  }

  private static final class InstanceHolder {
    private static final RemoteDataSourceImpl_Factory INSTANCE = new RemoteDataSourceImpl_Factory();
  }
}
