package com.speedmenu.tablet.core.di;

import com.speedmenu.tablet.data.datasource.local.LocalDataSource;
import com.speedmenu.tablet.data.datasource.local.LocalDataSourceImpl;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSourceImpl;
import com.speedmenu.tablet.data.repository.MenuRepositoryImpl;
import com.speedmenu.tablet.data.repository.OrderRepositoryImpl;
import com.speedmenu.tablet.data.repository.RatingRepositoryImpl;
import com.speedmenu.tablet.domain.repository.MenuRepository;
import com.speedmenu.tablet.domain.repository.OrderRepository;
import com.speedmenu.tablet.domain.repository.RatingRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

/**
 * Módulo de injeção de dependências para a camada de dados.
 * Define as implementações concretas dos data sources e repositories.
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\'\u00a8\u0006\u0017"}, d2 = {"Lcom/speedmenu/tablet/core/di/DataModule;", "", "()V", "bindLocalDataSource", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;", "localDataSourceImpl", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSourceImpl;", "bindMenuRepository", "Lcom/speedmenu/tablet/domain/repository/MenuRepository;", "menuRepositoryImpl", "Lcom/speedmenu/tablet/data/repository/MenuRepositoryImpl;", "bindOrderRepository", "Lcom/speedmenu/tablet/domain/repository/OrderRepository;", "orderRepositoryImpl", "Lcom/speedmenu/tablet/data/repository/OrderRepositoryImpl;", "bindRatingRepository", "Lcom/speedmenu/tablet/domain/repository/RatingRepository;", "ratingRepositoryImpl", "Lcom/speedmenu/tablet/data/repository/RatingRepositoryImpl;", "bindRemoteDataSource", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;", "remoteDataSourceImpl", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSourceImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class DataModule {
    
    public DataModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.speedmenu.tablet.data.datasource.local.LocalDataSource bindLocalDataSource(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.datasource.local.LocalDataSourceImpl localDataSourceImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.speedmenu.tablet.data.datasource.remote.RemoteDataSource bindRemoteDataSource(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.datasource.remote.RemoteDataSourceImpl remoteDataSourceImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.speedmenu.tablet.domain.repository.MenuRepository bindMenuRepository(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.repository.MenuRepositoryImpl menuRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.speedmenu.tablet.domain.repository.OrderRepository bindOrderRepository(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.repository.OrderRepositoryImpl orderRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.speedmenu.tablet.domain.repository.RatingRepository bindRatingRepository(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.repository.RatingRepositoryImpl ratingRepositoryImpl);
}