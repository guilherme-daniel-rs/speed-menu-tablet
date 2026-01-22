package com.speedmenu.tablet.data.datasource.remote;

import com.speedmenu.tablet.data.model.CategoryEntity;
import com.speedmenu.tablet.data.model.MenuItemEntity;

/**
 * Interface para acesso a dados remotos (API, servidor, etc.).
 * Define os contratos para operações de comunicação com o backend.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\"\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0007\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0010"}, d2 = {"Lcom/speedmenu/tablet/data/datasource/remote/RemoteDataSource;", "", "getCategories", "Lkotlin/Result;", "", "Lcom/speedmenu/tablet/data/model/CategoryEntity;", "getCategories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItemById", "Lcom/speedmenu/tablet/data/model/MenuItemEntity;", "itemId", "", "getMenuItemById-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "getMenuItems-IoAF18A", "app_debug"})
public abstract interface RemoteDataSource {
}