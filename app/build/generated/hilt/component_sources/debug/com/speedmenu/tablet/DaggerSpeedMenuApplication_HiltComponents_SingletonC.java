package com.speedmenu.tablet;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.speedmenu.tablet.core.di.AppModule;
import com.speedmenu.tablet.core.di.ConfigModule;
import com.speedmenu.tablet.core.di.ConfigModule_ProvideAppConfigDataStoreFactory;
import com.speedmenu.tablet.core.di.ConfigModule_ProvideAppConfigRepositoryFactory;
import com.speedmenu.tablet.core.di.ConfigModule_ProvideAppConfigSourceFactory;
import com.speedmenu.tablet.core.di.ConfigModule_ProvideRestaurantSessionFactory;
import com.speedmenu.tablet.data.local.AppConfigDataStore;
import com.speedmenu.tablet.data.local.RestaurantSession;
import com.speedmenu.tablet.data.preferences.GamePreferences;
import com.speedmenu.tablet.data.repository.OrderRepositoryImpl;
import com.speedmenu.tablet.data.repository.RatingRepositoryImpl;
import com.speedmenu.tablet.domain.repository.AppConfigRepository;
import com.speedmenu.tablet.domain.repository.AppConfigSource;
import com.speedmenu.tablet.domain.repository.OrderRepository;
import com.speedmenu.tablet.domain.repository.RatingRepository;
import com.speedmenu.tablet.ui.games.flappy.FlappyViewModel;
import com.speedmenu.tablet.ui.games.flappy.FlappyViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.screens.aiassistant.AiAssistantViewModel;
import com.speedmenu.tablet.ui.screens.aiassistant.AiAssistantViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderViewModel;
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.screens.rateplace.RatePlaceViewModel;
import com.speedmenu.tablet.ui.screens.rateplace.RatePlaceViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.screens.splash.SplashViewModel;
import com.speedmenu.tablet.ui.screens.splash.SplashViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel;
import com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.viewmodel.CartViewModel;
import com.speedmenu.tablet.ui.viewmodel.CartViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.viewmodel.QrScannerViewModel;
import com.speedmenu.tablet.ui.viewmodel.QrScannerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.viewmodel.ViewOrderViewModel;
import com.speedmenu.tablet.ui.viewmodel.ViewOrderViewModel_HiltModules_KeyModule_ProvideFactory;
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel;
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.SetBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DaggerSpeedMenuApplication_HiltComponents_SingletonC {
  private DaggerSpeedMenuApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder appModule(AppModule appModule) {
      Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder configModule(ConfigModule configModule) {
      Preconditions.checkNotNull(configModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    public SpeedMenuApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SpeedMenuApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements SpeedMenuApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SpeedMenuApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SpeedMenuApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SpeedMenuApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SpeedMenuApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SpeedMenuApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SpeedMenuApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SpeedMenuApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SpeedMenuApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends SpeedMenuApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SpeedMenuApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(10).add(AiAssistantViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AppConfigViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(CartViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(FlappyViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(PlaceholderViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(QrScannerViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(RatePlaceViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SplashViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ViewOrderViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(WaiterViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends SpeedMenuApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AiAssistantViewModel> aiAssistantViewModelProvider;

    private Provider<AppConfigViewModel> appConfigViewModelProvider;

    private Provider<CartViewModel> cartViewModelProvider;

    private Provider<FlappyViewModel> flappyViewModelProvider;

    private Provider<PlaceholderViewModel> placeholderViewModelProvider;

    private Provider<QrScannerViewModel> qrScannerViewModelProvider;

    private Provider<RatePlaceViewModel> ratePlaceViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<ViewOrderViewModel> viewOrderViewModelProvider;

    private Provider<WaiterViewModel> waiterViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.aiAssistantViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.appConfigViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.cartViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.flappyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.placeholderViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.qrScannerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.ratePlaceViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.viewOrderViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.waiterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, Provider<ViewModel>>newMapBuilder(10).put("com.speedmenu.tablet.ui.screens.aiassistant.AiAssistantViewModel", ((Provider) aiAssistantViewModelProvider)).put("com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel", ((Provider) appConfigViewModelProvider)).put("com.speedmenu.tablet.ui.viewmodel.CartViewModel", ((Provider) cartViewModelProvider)).put("com.speedmenu.tablet.ui.games.flappy.FlappyViewModel", ((Provider) flappyViewModelProvider)).put("com.speedmenu.tablet.ui.screens.placeholder.PlaceholderViewModel", ((Provider) placeholderViewModelProvider)).put("com.speedmenu.tablet.ui.viewmodel.QrScannerViewModel", ((Provider) qrScannerViewModelProvider)).put("com.speedmenu.tablet.ui.screens.rateplace.RatePlaceViewModel", ((Provider) ratePlaceViewModelProvider)).put("com.speedmenu.tablet.ui.screens.splash.SplashViewModel", ((Provider) splashViewModelProvider)).put("com.speedmenu.tablet.ui.viewmodel.ViewOrderViewModel", ((Provider) viewOrderViewModelProvider)).put("com.speedmenu.tablet.ui.viewmodel.WaiterViewModel", ((Provider) waiterViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.speedmenu.tablet.ui.screens.aiassistant.AiAssistantViewModel 
          return (T) new AiAssistantViewModel();

          case 1: // com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel 
          return (T) new AppConfigViewModel(singletonCImpl.provideAppConfigRepositoryProvider.get(), singletonCImpl.provideRestaurantSessionProvider.get());

          case 2: // com.speedmenu.tablet.ui.viewmodel.CartViewModel 
          return (T) new CartViewModel();

          case 3: // com.speedmenu.tablet.ui.games.flappy.FlappyViewModel 
          return (T) new FlappyViewModel(singletonCImpl.gamePreferencesProvider.get());

          case 4: // com.speedmenu.tablet.ui.screens.placeholder.PlaceholderViewModel 
          return (T) new PlaceholderViewModel();

          case 5: // com.speedmenu.tablet.ui.viewmodel.QrScannerViewModel 
          return (T) new QrScannerViewModel(singletonCImpl.bindOrderRepositoryProvider.get());

          case 6: // com.speedmenu.tablet.ui.screens.rateplace.RatePlaceViewModel 
          return (T) new RatePlaceViewModel(singletonCImpl.bindRatingRepositoryProvider.get());

          case 7: // com.speedmenu.tablet.ui.screens.splash.SplashViewModel 
          return (T) new SplashViewModel();

          case 8: // com.speedmenu.tablet.ui.viewmodel.ViewOrderViewModel 
          return (T) new ViewOrderViewModel(singletonCImpl.bindOrderRepositoryProvider.get());

          case 9: // com.speedmenu.tablet.ui.viewmodel.WaiterViewModel 
          return (T) new WaiterViewModel();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SpeedMenuApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SpeedMenuApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends SpeedMenuApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppConfigSource> provideAppConfigSourceProvider;

    private Provider<AppConfigRepository> provideAppConfigRepositoryProvider;

    private Provider<AppConfigDataStore> provideAppConfigDataStoreProvider;

    private Provider<RestaurantSession> provideRestaurantSessionProvider;

    private Provider<GamePreferences> gamePreferencesProvider;

    private Provider<OrderRepositoryImpl> orderRepositoryImplProvider;

    private Provider<OrderRepository> bindOrderRepositoryProvider;

    private Provider<RatingRepositoryImpl> ratingRepositoryImplProvider;

    private Provider<RatingRepository> bindRatingRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideAppConfigSourceProvider = DoubleCheck.provider(new SwitchingProvider<AppConfigSource>(singletonCImpl, 1));
      this.provideAppConfigRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AppConfigRepository>(singletonCImpl, 0));
      this.provideAppConfigDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<AppConfigDataStore>(singletonCImpl, 3));
      this.provideRestaurantSessionProvider = DoubleCheck.provider(new SwitchingProvider<RestaurantSession>(singletonCImpl, 2));
      this.gamePreferencesProvider = DoubleCheck.provider(new SwitchingProvider<GamePreferences>(singletonCImpl, 4));
      this.orderRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindOrderRepositoryProvider = DoubleCheck.provider((Provider) orderRepositoryImplProvider);
      this.ratingRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 6);
      this.bindRatingRepositoryProvider = DoubleCheck.provider((Provider) ratingRepositoryImplProvider);
    }

    @Override
    public void injectSpeedMenuApplication(SpeedMenuApplication speedMenuApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.speedmenu.tablet.domain.repository.AppConfigRepository 
          return (T) ConfigModule_ProvideAppConfigRepositoryFactory.provideAppConfigRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideAppConfigSourceProvider.get());

          case 1: // com.speedmenu.tablet.domain.repository.AppConfigSource 
          return (T) ConfigModule_ProvideAppConfigSourceFactory.provideAppConfigSource();

          case 2: // com.speedmenu.tablet.data.local.RestaurantSession 
          return (T) ConfigModule_ProvideRestaurantSessionFactory.provideRestaurantSession(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideAppConfigDataStoreProvider.get());

          case 3: // com.speedmenu.tablet.data.local.AppConfigDataStore 
          return (T) ConfigModule_ProvideAppConfigDataStoreFactory.provideAppConfigDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.speedmenu.tablet.data.preferences.GamePreferences 
          return (T) new GamePreferences(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.speedmenu.tablet.data.repository.OrderRepositoryImpl 
          return (T) new OrderRepositoryImpl();

          case 6: // com.speedmenu.tablet.data.repository.RatingRepositoryImpl 
          return (T) new RatingRepositoryImpl();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
