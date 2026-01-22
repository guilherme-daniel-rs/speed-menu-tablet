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
     * Tela de Categorias do Cardápio
     */
    object Categories : Screen("categories")

    /**
     * Tela placeholder para desenvolvimento futuro
     */
    object Placeholder : Screen("placeholder")
}

