package com.speedmenu.tablet.data.model

import com.speedmenu.tablet.domain.model.MenuItem

/**
 * Entidade de dados representando um item do cardápio.
 * Esta é a representação dos dados na camada de dados.
 * Pode ser mapeada para/do modelo de domínio.
 */
data class MenuItemEntity(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true
)

/**
 * Mapeia a entidade de dados para o modelo de domínio.
 */
fun MenuItemEntity.toDomain(): MenuItem {
    return MenuItem(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl,
        isAvailable = isAvailable
    )
}

/**
 * Mapeia o modelo de domínio para a entidade de dados.
 */
fun MenuItem.toEntity(): MenuItemEntity {
    return MenuItemEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl,
        isAvailable = isAvailable
    )
}

