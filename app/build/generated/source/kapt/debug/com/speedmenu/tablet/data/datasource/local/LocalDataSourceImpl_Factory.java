package com.speedmenu.tablet.data.datasource.local;

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
public final class LocalDataSourceImpl_Factory implements Factory<LocalDataSourceImpl> {
  @Override
  public LocalDataSourceImpl get() {
    return newInstance();
  }

  public static LocalDataSourceImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocalDataSourceImpl newInstance() {
    return new LocalDataSourceImpl();
  }

  private static final class InstanceHolder {
    private static final LocalDataSourceImpl_Factory INSTANCE = new LocalDataSourceImpl_Factory();
  }
}
