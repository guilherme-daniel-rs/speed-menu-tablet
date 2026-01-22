package com.speedmenu.tablet.data.datasource.remote

import com.speedmenu.tablet.data.model.CategoryEntity
import com.speedmenu.tablet.data.model.MenuItemEntity
import javax.inject.Inject

/**
 * Implementação mockada do RemoteDataSource.
 * Simula chamadas à API retornando dados fake.
 * TODO: Substituir por implementação real usando Retrofit/OkHttp quando a API estiver disponível.
 */
class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    override suspend fun getMenuItems(): Result<List<MenuItemEntity>> {
        // TODO: Substituir por chamada real à API
        return Result.success(getMockMenuItems())
    }

    override suspend fun getMenuItemById(itemId: String): Result<MenuItemEntity> {
        // TODO: Substituir por chamada real à API
        val item = getMockMenuItems().find { it.id == itemId }
        return if (item != null) {
            Result.success(item)
        } else {
            Result.failure(Exception("Item not found"))
        }
    }

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        // TODO: Substituir por chamada real à API
        return Result.success(getMockCategories())
    }

    /**
     * Gera dados mockados de itens do menu para desenvolvimento.
     * TODO: Remover quando a API real estiver disponível.
     */
    private fun getMockMenuItems(): List<MenuItemEntity> {
        return listOf(
            MenuItemEntity(
                id = "1",
                name = "Item Mock 1",
                description = "Descrição do item mock 1",
                price = 25.90,
                category = "1",
                isAvailable = true
            ),
            MenuItemEntity(
                id = "2",
                name = "Item Mock 2",
                description = "Descrição do item mock 2",
                price = 35.50,
                category = "1",
                isAvailable = true
            )
        )
    }

    /**
     * Gera dados mockados de categorias para desenvolvimento.
     * TODO: Remover quando a API real estiver disponível.
     */
    private fun getMockCategories(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(
                id = "1",
                name = "Categoria Mock",
                description = "Descrição da categoria mock",
                displayOrder = 1
            )
        )
    }
}

