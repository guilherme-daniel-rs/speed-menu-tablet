package com.speedmenu.tablet.core.di

import com.speedmenu.tablet.data.datasource.local.LocalDataSource
import com.speedmenu.tablet.data.datasource.local.LocalDataSourceImpl
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSourceImpl
import com.speedmenu.tablet.data.repository.MenuRepositoryImpl
import com.speedmenu.tablet.data.repository.OrderRepositoryImpl
import com.speedmenu.tablet.domain.repository.MenuRepository
import com.speedmenu.tablet.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de injeção de dependências para a camada de dados.
 * Define as implementações concretas dos data sources e repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMenuRepository(
        menuRepositoryImpl: MenuRepositoryImpl
    ): MenuRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}

