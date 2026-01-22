package com.speedmenu.tablet.data.repository

import com.speedmenu.tablet.data.datasource.local.LocalDataSource
import com.speedmenu.tablet.data.datasource.remote.RemoteDataSource
import com.speedmenu.tablet.data.model.CategoryEntity
import com.speedmenu.tablet.data.model.MenuItemEntity
import com.speedmenu.tablet.data.model.toDomain
import com.speedmenu.tablet.domain.model.Category
import com.speedmenu.tablet.domain.model.MenuItem
import com.speedmenu.tablet.domain.repository.MenuRepository
import javax.inject.Inject

/**
 * Implementação do repositório de menu.
 * Coordena o acesso a dados locais e remotos, implementando a lógica de cache.
 * TODO: Implementar estratégia de cache (buscar local primeiro, depois remoto, etc.)
 */
class MenuRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MenuRepository {

    override suspend fun getMenuItems(): Result<List<MenuItem>> {
        return try {
            // TODO: Implementar estratégia de cache
            // Por enquanto, busca apenas do remoto e salva localmente
            val result = remoteDataSource.getMenuItems()
            result.onSuccess { items ->
                localDataSource.saveMenuItems(items)
            }
            result.map { items -> items.map { it.toDomain() } }
        } catch (e: Exception) {
            // Em caso de erro, tenta buscar do cache local
            val localItems = localDataSource.getMenuItems()
            if (localItems.isNotEmpty()) {
                Result.success(localItems.map { it.toDomain() })
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun getMenuItemById(itemId: String): Result<MenuItem?> {
        return try {
            // Tenta buscar localmente primeiro
            val localItem = localDataSource.getMenuItemById(itemId)
            if (localItem != null) {
                Result.success(localItem.toDomain())
            } else {
                // Se não encontrar localmente, busca remotamente
                val result = remoteDataSource.getMenuItemById(itemId)
                result.map { it.toDomain() }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMenuItemsByCategory(categoryId: String): Result<List<MenuItem>> {
        return try {
            val allItems = getMenuItems()
            allItems.map { items ->
                items.filter { it.category == categoryId }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            // TODO: Implementar estratégia de cache
            val result = remoteDataSource.getCategories()
            result.onSuccess { categories ->
                localDataSource.saveCategories(categories)
            }
            result.map { categories -> categories.map { it.toDomain() } }
        } catch (e: Exception) {
            val localCategories = localDataSource.getCategories()
            if (localCategories.isNotEmpty()) {
                Result.success(localCategories.map { it.toDomain() })
            } else {
                Result.failure(e)
            }
        }
    }
}

