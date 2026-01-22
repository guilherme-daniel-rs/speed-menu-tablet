package com.speedmenu.tablet.data.model

import com.speedmenu.tablet.domain.model.Category

/**
 * Entidade de dados representando uma categoria do cardápio.
 */
data class CategoryEntity(
    val id: String,
    val name: String,
    val description: String? = null,
    val displayOrder: Int = 0
)

/**
 * Mapeia a entidade de dados para o modelo de domínio.
 */
fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        displayOrder = displayOrder
    )
}

/**
 * Mapeia o modelo de domínio para a entidade de dados.
 */
fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        displayOrder = displayOrder
    )
}

