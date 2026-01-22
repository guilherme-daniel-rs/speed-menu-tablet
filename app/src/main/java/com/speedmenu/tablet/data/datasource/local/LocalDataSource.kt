package com.speedmenu.tablet.data.datasource.local

import com.speedmenu.tablet.data.model.CategoryEntity
import com.speedmenu.tablet.data.model.MenuItemEntity

/**
 * Interface para acesso a dados locais (cache, banco de dados local, etc.).
 * Define os contratos para operações de leitura/escrita local.
 */
interface LocalDataSource {
    /**
     * Salva itens do menu localmente.
     * @param items Lista de itens a serem salvos
     */
    suspend fun saveMenuItems(items: List<MenuItemEntity>)

    /**
     * Busca itens do menu salvos localmente.
     * @return Lista de itens salvos ou lista vazia se não houver dados
     */
    suspend fun getMenuItems(): List<MenuItemEntity>

    /**
     * Busca um item específico por ID.
     * @param itemId ID do item
     * @return Item encontrado ou null
     */
    suspend fun getMenuItemById(itemId: String): MenuItemEntity?

    /**
     * Salva categorias localmente.
     * @param categories Lista de categorias a serem salvas
     */
    suspend fun saveCategories(categories: List<CategoryEntity>)

    /**
     * Busca categorias salvas localmente.
     * @return Lista de categorias salvas ou lista vazia se não houver dados
     */
    suspend fun getCategories(): List<CategoryEntity>

    /**
     * Limpa todos os dados locais.
     */
    suspend fun clearAll()
}

