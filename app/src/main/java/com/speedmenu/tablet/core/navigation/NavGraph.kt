package com.speedmenu.tablet.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.speedmenu.tablet.ui.screens.categories.CategoriesScreen
import com.speedmenu.tablet.ui.screens.home.HomeScreen
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderScreen
import com.speedmenu.tablet.ui.screens.splash.SplashScreen

/**
 * Configuração do grafo de navegação da aplicação.
 * Define todas as rotas e suas respectivas telas.
 *
 * @param navController Controlador de navegação do Compose
 * @param startDestination Rota inicial da aplicação (padrão: Home)
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToPlaceholder = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCategories = {
                    navController.navigate(Screen.Categories.route)
                }
            )
        }

        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCategory = { categoryId ->
                    // TODO: Implementar navegação para itens da categoria
                }
            )
        }

        composable(route = Screen.Placeholder.route) {
            PlaceholderScreen()
        }
    }
}

