package com.speedmenu.tablet.core.di

import android.content.Context
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
    
    @Provides
    @Singleton
    fun provideAppConfigDataStore(
        @ApplicationContext context: Context
    ): AppConfigDataStore {
        return AppConfigDataStore(context)
    }
    
    @Provides
    @Singleton
    fun provideAppConfigSource(): AppConfigSource {
        return MockAppConfigSource()
    }
    
    @Provides
    @Singleton
    fun provideRestaurantSession(
        @ApplicationContext context: Context,
        appConfigDataStore: AppConfigDataStore
    ): RestaurantSession {
        return RestaurantSession(context, appConfigDataStore)
    }
    
    @Provides
    @Singleton
    fun provideAppConfigRepository(
        @ApplicationContext context: Context,
        appConfigSource: AppConfigSource
    ): AppConfigRepository {
        return AppConfigRepositoryImpl(context, appConfigSource)
    }
}

