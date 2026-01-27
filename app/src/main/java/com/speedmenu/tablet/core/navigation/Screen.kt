package com.speedmenu.tablet.core.navigation

/**
 * Sealed class que define todas as rotas de navegação da aplicação.
 * Cada objeto representa uma tela do aplicativo.
 */
sealed class Screen(val route: String) {
    /**
     * Tela de splash/boas-vindas
     */
    object Splash : Screen("splash")

    /**
     * Tela Home principal do aplicativo
     */
    object Home : Screen("home")

    /**
     * Tela de Produtos/Pratos de uma categoria
     */
    object Products : Screen("products/{categoryName}") {
        fun createRoute(categoryName: String) = "products/$categoryName"
    }

    /**
     * Tela de detalhes do prato (VerPratoScreen)
     */
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }

    /**
     * Tela placeholder para desenvolvimento futuro
     */
    object Placeholder : Screen("placeholder")

    /**
     * Tela de pedido/carrinho
     */
    object Cart : Screen("cart")
    
    /**
     * Tela de scanner de QR Code para finalizar pedido ou ver pedido
     */
    object QrScanner : Screen("qr_scanner/{mode}") {
        fun createRoute(mode: String) = "qr_scanner/$mode"
    }
    
    /**
     * Tela de visualização de pedido por comanda (read-only)
     */
    object ViewOrder : Screen("view_order/{comandaCode}") {
        fun createRoute(comandaCode: String) = "view_order/$comandaCode"
    }
    
    /**
     * Tela de avaliação do local
     */
    object RatePlace : Screen("rate_place")
}

