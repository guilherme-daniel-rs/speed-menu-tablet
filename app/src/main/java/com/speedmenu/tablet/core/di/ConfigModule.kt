package com.speedmenu.tablet.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.speedmenu.tablet.data.local.AppConfigDataStore
import com.speedmenu.tablet.data.local.RestaurantSession
import com.speedmenu.tablet.data.mock.MockAppConfigSource
import com.speedmenu.tablet.data.repository.AppConfigRepositoryImpl
import com.speedmenu.tablet.domain.repository.AppConfigRepository
import com.speedmenu.tablet.domain.repository.AppConfigSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para dependências de configuração do app.
 */
@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {
    
    // Singleton do DataStore - criado uma única vez usando ApplicationContext
    private val Context.appConfigDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_config")
    private val Context.restaurantSessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "restaurant_session")
    
    @Provides
    @Singleton
    @AppConfigDS
    fun provideAppConfigDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.appConfigDataStore
    }
    
    @Provides
    @Singleton
    @RestaurantSessionDS
    fun provideRestaurantSessionDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.restaurantSessionDataStore
    }
    
    @Provides
    @Singleton
    fun provideAppConfigDataStoreWrapper(
        @AppConfigDS dataStore: DataStore<Preferences>
    ): AppConfigDataStore {
        return AppConfigDataStore(dataStore)
    }
    
    @Provides
    @Singleton
    fun provideAppConfigSource(): AppConfigSource {
        return MockAppConfigSource()
    }
    
    @Provides
    @Singleton
    fun provideRestaurantSession(
        @RestaurantSessionDS restaurantSessionDataStore: DataStore<Preferences>,
        appConfigDataStore: AppConfigDataStore
    ): RestaurantSession {
        return RestaurantSession(restaurantSessionDataStore, appConfigDataStore)
    }
    
    @Provides
    @Singleton
    fun provideAppConfigRepository(
        appConfigDataStore: AppConfigDataStore,
        appConfigSource: AppConfigSource
    ): AppConfigRepository {
        return AppConfigRepositoryImpl(appConfigDataStore, appConfigSource)
    }
}

