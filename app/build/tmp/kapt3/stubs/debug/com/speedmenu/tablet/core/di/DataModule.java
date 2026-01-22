package com.speedmenu.tablet.core.di;

import com.speedmenu.tablet.data.datasource.local.LocalDataSource;
import com.speedmenu.tablet.data.datasource.local.LocalDataSourceImpl;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSourceImpl;
import com.speedmenu.tablet.data.repository.MenuRepositoryImpl;
import com.speedmenu.tablet.domain.repository.MenuRepository;
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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'\u00a8\u0006\u000f"}, d2 = {"Lcom/speedmenu/tablet/core/di/DataModule;", "", "()V", "bindLocalDataSource", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;", "localDataSourceImpl", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSourceImpl;", "bindMenuRepository", "Lcom/speedmenu/tablet/domain/repository/MenuRepository;", "menuRepositoryImpl", "Lcom/speedmenu/tablet/data/repository/MenuRepositoryImpl;", "bindRemoteDataSource", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;", "remoteDataSourceImpl", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSourceImpl;", "app_debug"})
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
}