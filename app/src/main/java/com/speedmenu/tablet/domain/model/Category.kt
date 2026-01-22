package com.speedmenu.tablet.domain.model

/**
 * Modelo de domínio representando uma categoria do cardápio.
 */
data class Category(
    val id: String,
    val name: String,
    val description: String? = null,
    val displayOrder: Int = 0
)

