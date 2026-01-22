package com.speedmenu.tablet.ui.screens.placeholder;

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
public final class PlaceholderViewModel_Factory implements Factory<PlaceholderViewModel> {
  @Override
  public PlaceholderViewModel get() {
    return newInstance();
  }

  public static PlaceholderViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PlaceholderViewModel newInstance() {
    return new PlaceholderViewModel();
  }

  private static final class InstanceHolder {
    private static final PlaceholderViewModel_Factory INSTANCE = new PlaceholderViewModel_Factory();
  }
}
