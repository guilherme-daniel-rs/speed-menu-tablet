package com.speedmenu.tablet.data.repository;

import com.speedmenu.tablet.data.datasource.local.LocalDataSource;
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource;
import com.speedmenu.tablet.data.model.CategoryEntity;
import com.speedmenu.tablet.data.model.MenuItemEntity;
import com.speedmenu.tablet.domain.model.Category;
import com.speedmenu.tablet.domain.model.MenuItem;
import com.speedmenu.tablet.domain.repository.MenuRepository;
import javax.inject.Inject;

/**
 * Implementação do repositório de menu.
 * Coordena o acesso a dados locais e remotos, implementando a lógica de cache.
 * TODO: Implementar estratégia de cache (buscar local primeiro, depois remoto, etc.)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ&\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\"\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\fJ*\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b2\u0006\u0010\u0016\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0018"}, d2 = {"Lcom/speedmenu/tablet/data/repository/MenuRepositoryImpl;", "Lcom/speedmenu/tablet/domain/repository/MenuRepository;", "remoteDataSource", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;", "localDataSource", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;", "(Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;)V", "getCategories", "Lkotlin/Result;", "", "Lcom/speedmenu/tablet/domain/model/Category;", "getCategories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItemById", "Lcom/speedmenu/tablet/domain/model/MenuItem;", "itemId", "", "getMenuItemById-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "getMenuItems-IoAF18A", "getMenuItemsByCategory", "categoryId", "getMenuItemsByCategory-gIAlu-s", "app_debug"})
public final class MenuRepositoryImpl implements com.speedmenu.tablet.domain.repository.MenuRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.speedmenu.tablet.data.datasource.remote.RemoteDataSource remoteDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.speedmenu.tablet.data.datasource.local.LocalDataSource localDataSource = null;
    
    @javax.inject.Inject()
    public MenuRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.datasource.remote.RemoteDataSource remoteDataSource, @org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.data.datasource.local.LocalDataSource localDataSource) {
        super();
    }
}