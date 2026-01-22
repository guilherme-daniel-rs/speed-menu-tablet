package com.speedmenu.tablet.data.datasource.remote;

import com.speedmenu.tablet.data.model.CategoryEntity;
import com.speedmenu.tablet.data.model.MenuItemEntity;
import javax.inject.Inject;

/**
 * Implementação mockada do RemoteDataSource.
 * Simula chamadas à API retornando dados fake.
 * TODO: Substituir por implementação real usando Retrofit/OkHttp quando a API estiver disponível.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ\"\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00050\u0004H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\bJ\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u0002\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0013"}, d2 = {"Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSourceImpl;", "Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;", "()V", "getCategories", "Lkotlin/Result;", "", "Lcom/speedmenu/tablet/data/model/CategoryEntity;", "getCategories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItemById", "Lcom/speedmenu/tablet/data/model/MenuItemEntity;", "itemId", "", "getMenuItemById-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "getMenuItems-IoAF18A", "getMockCategories", "getMockMenuItems", "app_debug"})
public final class RemoteDataSourceImpl implements com.speedmenu.tablet.data.datasource.remote.RemoteDataSource {
    
    @javax.inject.Inject()
    public RemoteDataSourceImpl() {
        super();
    }
    
    /**
     * Gera dados mockados de itens do menu para desenvolvimento.
     * TODO: Remover quando a API real estiver disponível.
     */
    private final java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity> getMockMenuItems() {
        return null;
    }
    
    /**
     * Gera dados mockados de categorias para desenvolvimento.
     * TODO: Remover quando a API real estiver disponível.
     */
    private final java.util.List<com.speedmenu.tablet.data.model.CategoryEntity> getMockCategories() {
        return null;
    }
}