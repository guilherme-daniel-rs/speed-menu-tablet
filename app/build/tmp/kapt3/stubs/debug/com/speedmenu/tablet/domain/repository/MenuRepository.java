package com.speedmenu.tablet.domain.repository;

import com.speedmenu.tablet.domain.model.Category;
import com.speedmenu.tablet.domain.model.MenuItem;

/**
 * Interface do repositório de cardápio.
 * Define os contratos para acesso aos dados do menu.
 * A implementação concreta fica na camada de dados.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J&\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\"\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0007J*\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u00032\u0006\u0010\u0011\u001a\u00020\u000bH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\r\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0013"}, d2 = {"Lcom/speedmenu/tablet/domain/repository/MenuRepository;", "", "getCategories", "Lkotlin/Result;", "", "Lcom/speedmenu/tablet/domain/model/Category;", "getCategories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItemById", "Lcom/speedmenu/tablet/domain/model/MenuItem;", "itemId", "", "getMenuItemById-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMenuItems", "getMenuItems-IoAF18A", "getMenuItemsByCategory", "categoryId", "getMenuItemsByCategory-gIAlu-s", "app_debug"})
public abstract interface MenuRepository {
}