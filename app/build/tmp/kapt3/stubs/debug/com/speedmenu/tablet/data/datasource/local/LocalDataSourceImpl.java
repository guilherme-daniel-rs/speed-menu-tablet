package com.speedmenu.tablet.data.datasource.local;

import com.speedmenu.tablet.data.model.CategoryEntity;
import com.speedmenu.tablet.data.model.MenuItemEntity;
import javax.inject.Inject;

/**
 * Implementação mockada do LocalDataSource.
 * Por enquanto, armazena dados em memória.
 * TODO: Substituir por implementação real usando Room ou DataStore quando necessário.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\tH\u0096@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0096@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\r\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0096@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0012\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0096@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0096@\u00a2\u0006\u0002\u0010\u0014R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/speedmenu/tablet/data/datasource/local/LocalDataSourceImpl;", "Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;", "()V", "categoriesCache", "", "Lcom/speedmenu/tablet/data/model/CategoryEntity;", "menuItemsCache", "Lcom/speedmenu/tablet/data/model/MenuItemEntity;", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "", "getMenuItemById", "itemId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "saveCategories", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveMenuItems", "items", "app_debug"})
public final class LocalDataSourceImpl implements com.speedmenu.tablet.data.datasource.local.LocalDataSource {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity> menuItemsCache = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.speedmenu.tablet.data.model.CategoryEntity> categoriesCache = null;
    
    @javax.inject.Inject()
    public LocalDataSourceImpl() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveMenuItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMenuItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMenuItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.speedmenu.tablet.data.model.MenuItemEntity> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveCategories(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.data.model.CategoryEntity> categories, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.speedmenu.tablet.data.model.CategoryEntity>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}