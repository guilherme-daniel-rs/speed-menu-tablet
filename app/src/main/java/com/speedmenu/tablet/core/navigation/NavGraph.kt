package com.speedmenu.tablet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.speedmenu.tablet.ui.screens.order.CartItem
import com.speedmenu.tablet.ui.screens.home.HomeScreen
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario
import com.speedmenu.tablet.ui.screens.home.firstCategoryId
import com.speedmenu.tablet.ui.screens.home.getMenuMockup
import com.speedmenu.tablet.ui.screens.order.CartEmptyScreen
import com.speedmenu.tablet.ui.screens.order.CartSummaryScreen
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderScreen
import com.speedmenu.tablet.ui.screens.productdetail.VerPratoScreen
import com.speedmenu.tablet.ui.screens.products.ProductsScreen
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

        composable(route = Screen.Home.route) { backStackEntry ->
            // Mock de dados do carrinho (em produção viria de um ViewModel/Repository)
            val cartItemCount = 0 // TODO: Obter quantidade real do carrinho
            
            HomeScreen(
                onNavigateToCategories = {
                    // Navega diretamente para a primeira categoria do primeiro tópico
                    // Usa o mesmo mockup scenario padrão usado nas outras telas
                    val defaultScenario = MenuMockupScenario.LONG_SCROLL
                    val topics = getMenuMockup(defaultScenario)
                    val firstCategory = firstCategoryId(topics)
                    
                    if (firstCategory != null) {
                        navController.navigate(Screen.Products.createRoute(firstCategory)) {
                            // Limpa a pilha até a Home (inclusive) para garantir navegação limpa
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    // Se não houver categorias, não navega (caso raro)
                    // Em produção, isso seria tratado com um estado de erro/empty state
                },
                onNavigateToCart = {
                    // Salva dados do carrinho no savedStateHandle antes de navegar
                    backStackEntry.savedStateHandle["cartItemCount"] = cartItemCount
                    navController.navigate(Screen.Cart.route)
                },
                cartItemCount = cartItemCount
            )
        }

        composable(
            route = Screen.Products.route,
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            
            // Restaura categoria selecionada do savedStateHandle (se existir)
            // Isso preserva a categoria quando o usuário volta do prato
            val savedCategoryId = backStackEntry.savedStateHandle.get<String>("selectedCategoryId")
            val initialCategoryId = savedCategoryId ?: categoryName.lowercase()
            
            // Mock de dados do carrinho (em produção viria de um ViewModel/Repository)
            val productsCartItemCount = 0 // TODO: Obter quantidade real do carrinho
            
            ProductsScreen(
                categoryName = categoryName,
                initialSelectedCategoryId = initialCategoryId, // Prioriza savedStateHandle para preservar estado ao voltar do prato
                onNavigateBack = {
                    // Não usado - TopActionBar usa onNavigateToHome
                },
                onNavigateToCart = {
                    // Salva dados do carrinho no savedStateHandle antes de navegar
                    backStackEntry.savedStateHandle["cartItemCount"] = productsCartItemCount
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToProductDetail = { productId ->
                    // Salva a categoria selecionada antes de navegar para o prato
                    backStackEntry.savedStateHandle["selectedCategoryId"] = categoryName.lowercase()
                    // Navega para detalhes do prato SEM limpar stack
                    // Back natural retornará para a categoria atual
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToCategory = { categoryId ->
                    // Salva a categoria selecionada antes de navegar
                    backStackEntry.savedStateHandle["selectedCategoryId"] = categoryId
                    // Navegação direta para outra categoria pelo menu lateral
                    // Substitui a tela Products atual na pilha para evitar acúmulo
                    // Mantém o resto da pilha (ex: Home) intacto
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    if (currentRoute?.startsWith("products/") == true) {
                        // Se já estamos em uma tela Products, substitui ela
                        navController.navigate(Screen.Products.createRoute(categoryId)) {
                            popUpTo(currentRoute) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        // Se não estamos em Products (ex: vindo de Home), apenas navega
                        navController.navigate(Screen.Products.createRoute(categoryId)) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            // Mock de dados do produto (em produção viria de um ViewModel/Repository)
            // Mock de dados do carrinho (em produção viria de um ViewModel/Repository)
            // Obtém do savedStateHandle ou usa valor padrão
            val detailCartItemCount = backStackEntry.savedStateHandle.get<Int>("cartItemCount") ?: 0
            
            VerPratoScreen(
                productId = productId,
                productName = "Filé Mignon ao Molho",
                productCategory = "Pratos Principais",
                productPrice = 68.90,
                productImageResId = com.speedmenu.tablet.R.drawable.pratos_principais,
                productDescription = "Filé grelhado com molho especial e acompanhamentos",
                ingredients = listOf("Filé mignon", "Molho especial", "Batatas", "Legumes", "Ervas"),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onNavigateToCart = {
                    // Salva dados do carrinho no savedStateHandle antes de navegar
                    // Atualiza com o valor atual do carrinho (que pode ter sido modificado na tela)
                    val currentCartCount = backStackEntry.savedStateHandle.get<Int>("cartItemCount") ?: detailCartItemCount
                    backStackEntry.savedStateHandle["cartItemCount"] = currentCartCount
                    navController.navigate(Screen.Cart.route)
                },
                onAddToCart = {
                    // TODO: Implementar lógica de adicionar ao carrinho
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Placeholder.route) {
            PlaceholderScreen()
        }

        composable(route = Screen.Cart.route) { backStackEntry ->
            // Obtém dados do carrinho do savedStateHandle
            val cartItemCount = backStackEntry.savedStateHandle.get<Int>("cartItemCount") ?: 0
            
            if (cartItemCount == 0) {
                // Tela de pedido vazio
                CartEmptyScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToMenu = {
                        // Navega para a primeira categoria
                        val defaultScenario = MenuMockupScenario.LONG_SCROLL
                        val topics = getMenuMockup(defaultScenario)
                        val firstCategory = firstCategoryId(topics)
                        
                        if (firstCategory != null) {
                            navController.navigate(Screen.Products.createRoute(firstCategory)) {
                                popUpTo(Screen.Cart.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Cart.route) { inclusive = true }
                            }
                        }
                    },
                    cartItemCount = cartItemCount
                )
            } else {
                // Tela de resumo do pedido
                // Mock de itens do carrinho (em produção viria de um ViewModel/Repository)
                val mockCartItems = listOf(
                    CartItem(
                        id = "1",
                        name = "Filé Mignon ao Molho",
                        quantity = 2,
                        unitPrice = 68.90,
                        totalPrice = 137.80
                    ),
                    CartItem(
                        id = "2",
                        name = "Risotto de Camarão",
                        quantity = 1,
                        unitPrice = 54.90,
                        totalPrice = 54.90
                    )
                )
                
                CartSummaryScreen(
                    items = mockCartItems,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onFinishOrder = {
                        // TODO: Implementar lógica de finalizar pedido
                        // Por enquanto, apenas volta
                        navController.popBackStack()
                    },
                    cartItemCount = cartItemCount
                )
            }
        }
    }
}

