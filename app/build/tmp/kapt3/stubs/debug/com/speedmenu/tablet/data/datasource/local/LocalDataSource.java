package com.speedmenu.tablet.data.datasource.local;

import com.speedmenu.tablet.data.model.CategoryEntity;
import com.speedmenu.tablet.data.model.MenuItemEntity;

/**
 * Interface para acesso a dados locais (cache, banco de dados local, etc.).
 * Define os contratos para operações de leitura/escrita local.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u000e\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0013"}, d2 = {"Lcom/speedmenu/tablet/data/datasource/local/LocalDataSource;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "", "Lcom/speedmenu/tablet/data/model/CategoryEntity;", "getMenuItemById", "Lcom/speedmenu/tablet/data/model/MenuItemEntity;", "itemId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "saveCategories", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveMenuItems", "items", "app_debug"})
public abstract interface LocalDataSource {
    
    /**
     * Salva itens do menu localmente.
     * @param items Lista de itens a serem salvos
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveMenuItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Busca itens do menu salvos localmente.
     * @return Lista de itens salvos ou lista vazia se não houver dados
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMenuItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.speedmenu.tablet.data.model.MenuItemEntity>> $completion);
    
    /**
     * Busca um item específico por ID.
     * @param itemId ID do item
     * @return Item encontrado ou null
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMenuItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.speedmenu.tablet.data.model.MenuItemEntity> $completion);
    
    /**
     * Salva categorias localmente.
     * @param categories Lista de categorias a serem salvas
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveCategories(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.data.model.CategoryEntity> categories, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Busca categorias salvas localmente.
     * @return Lista de categorias salvas ou lista vazia se não houver dados
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.speedmenu.tablet.data.model.CategoryEntity>> $completion);
    
    /**
     * Limpa todos os dados locais.
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}