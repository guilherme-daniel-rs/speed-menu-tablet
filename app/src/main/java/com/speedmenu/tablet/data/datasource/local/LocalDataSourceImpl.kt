package com.speedmenu.tablet.data.datasource.local

import com.speedmenu.tablet.data.model.CategoryEntity
import com.speedmenu.tablet.data.model.MenuItemEntity
import javax.inject.Inject

/**
 * Implementação mockada do LocalDataSource.
 * Por enquanto, armazena dados em memória.
 * TODO: Substituir por implementação real usando Room ou DataStore quando necessário.
 */
class LocalDataSourceImpl @Inject constructor() : LocalDataSource {

    // Armazenamento em memória temporário
    private val menuItemsCache = mutableListOf<MenuItemEntity>()
    private val categoriesCache = mutableListOf<CategoryEntity>()

    override suspend fun saveMenuItems(items: List<MenuItemEntity>) {
        menuItemsCache.clear()
        menuItemsCache.addAll(items)
    }

    override suspend fun getMenuItems(): List<MenuItemEntity> {
        return menuItemsCache.toList()
    }

    override suspend fun getMenuItemById(itemId: String): MenuItemEntity? {
        return menuItemsCache.find { it.id == itemId }
    }

    override suspend fun saveCategories(categories: List<CategoryEntity>) {
        categoriesCache.clear()
        categoriesCache.addAll(categories)
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return categoriesCache.toList()
    }

    override suspend fun clearAll() {
        menuItemsCache.clear()
        categoriesCache.clear()
    }
}

