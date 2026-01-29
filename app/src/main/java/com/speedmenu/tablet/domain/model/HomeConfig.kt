package com.speedmenu.tablet.domain.model

/**
 * Configuração da tela Home.
 * Contém o carrossel de imagens e outras configurações futuras.
 */
data class HomeConfig(
    /**
     * Lista de itens do carrossel.
     * Se vazia, o carrossel não deve ser exibido.
     */
    val carousel: List<CarouselItem> = emptyList()
)

