package com.speedmenu.tablet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import android.util.Log
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.speedmenu.tablet.domain.model.CartItem
import com.speedmenu.tablet.ui.viewmodel.CartViewModel
import com.speedmenu.tablet.ui.screens.home.HomeScreen
import com.speedmenu.tablet.ui.screens.home.MenuMockupScenario
import com.speedmenu.tablet.ui.screens.home.firstCategoryId
import com.speedmenu.tablet.ui.screens.home.getMenuMockup
import com.speedmenu.tablet.ui.screens.order.CartEmptyScreen
import com.speedmenu.tablet.ui.screens.order.CartSummaryScreen
import com.speedmenu.tablet.ui.screens.order.ViewOrderScreen
import com.speedmenu.tablet.ui.screens.placeholder.PlaceholderScreen
import com.speedmenu.tablet.ui.screens.productdetail.VerPratoScreen
import com.speedmenu.tablet.ui.screens.products.ProductsScreen
import com.speedmenu.tablet.ui.screens.qrscanner.QrScannerMode
import com.speedmenu.tablet.ui.screens.qrscanner.QrScannerScreen
import com.speedmenu.tablet.ui.screens.rateplace.RatePlaceScreen
import com.speedmenu.tablet.ui.screens.splash.SplashScreen
import com.speedmenu.tablet.ui.games.GamesHubScreen
import com.speedmenu.tablet.ui.games.flappy.GameFlappyScreen

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
    // ViewModel compartilhado entre todas as telas (escopo da Activity)
    // Usa hiltViewModel() para garantir instância única gerenciada pelo Hilt
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartState by cartViewModel.cartState.collectAsState()
    
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
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToViewOrder = {
                    // Navega para a tela de scanner de QR Code para escanear a comanda (modo VIEW_ORDER)
                    navController.navigate(Screen.QrScanner.createRoute("view_order"))
                },
                onNavigateToRatePlace = {
                    navController.navigate(Screen.RatePlace.route)
                },
                onNavigateToGames = {
                    navController.navigate(Screen.GamesHub.route)
                },
                cartItemCount = cartState.totalItems
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
            
            ProductsScreen(
                categoryName = categoryName,
                initialSelectedCategoryId = initialCategoryId, // Prioriza savedStateHandle para preservar estado ao voltar do prato
                navController = navController,
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                cartItemCount = cartState.totalItems,
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
            
            VerPratoScreen(
                productId = productId,
                productName = "Filé Mignon ao Molho",
                productCategory = "Pratos Principais",
                productPrice = 68.90,
                productImageResId = com.speedmenu.tablet.R.drawable.pratos_principais,
                productDescription = "Filé grelhado com molho especial e acompanhamentos",
                ingredients = listOf("Filé mignon", "Molho especial", "Batatas", "Legumes", "Ervas"),
                cartViewModel = cartViewModel,
                navController = navController,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onAddToCart = { _ ->
                    // A lógica de salvar no savedStateHandle agora é feita diretamente no VerPratoScreen
                    // antes de fazer popBackStack, garantindo que o evento seja escrito no backStack correto
                }
            )
        }

        composable(route = Screen.Placeholder.route) {
            PlaceholderScreen()
        }

        composable(route = Screen.Cart.route) {
            if (cartState.items.isEmpty()) {
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
                    cartItemCount = cartState.totalItems
                )
            } else {
                // Tela de resumo do pedido
                CartSummaryScreen(
                    items = cartState.items,
                    cartViewModel = cartViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onFinishOrder = {
                        // Navega para a tela de scanner de QR Code no modo CHECKOUT
                        navController.navigate(Screen.QrScanner.createRoute("checkout"))
                    }
                )
            }
        }

        composable(
            route = Screen.QrScanner.route,
            arguments = listOf(
                navArgument("mode") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Obtém o modo do parâmetro da rota
            val modeParam = backStackEntry.arguments?.getString("mode") ?: "view_order"
            val scannerMode = when (modeParam.lowercase()) {
                "checkout" -> QrScannerMode.CHECKOUT
                "view_order" -> QrScannerMode.VIEW_ORDER
                else -> {
                    Log.w("NavGraph", "⚠️ Modo desconhecido: $modeParam, usando VIEW_ORDER como padrão")
                    QrScannerMode.VIEW_ORDER
                }
            }
            
            Log.d("NavGraph", "🔍 QrScanner aberto - modeParam: $modeParam, scannerMode: $scannerMode")
            
            QrScannerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                mode = scannerMode,
                cartViewModel = cartViewModel, // Passa o mesmo CartViewModel compartilhado do NavGraph
                onNavigateToViewOrder = { comandaCode ->
                    Log.d("NavGraph", "👁️ onNavigateToViewOrder chamado - comandaCode: $comandaCode")
                    // Navega para view_order removendo o scanner do backstack
                    navController.navigate(Screen.ViewOrder.createRoute(comandaCode)) {
                        // Remove o scanner do backstack para não voltar nele sem querer
                        // Usa pattern matching para remover qualquer rota qr_scanner
                        popUpTo("qr_scanner") { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    Log.d("NavGraph", "🏠 onNavigateToHome chamado")
                    // Navega para Home limpando o backstack do fluxo de pedido
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.ViewOrder.route,
            arguments = listOf(
                navArgument("comandaCode") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val comandaCode = backStackEntry.arguments?.getString("comandaCode") ?: ""
            
            ViewOrderScreen(
                comandaCode = comandaCode,
                onNavigateBack = {
                    // Volta para Home (não volta para o scanner)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        composable(route = Screen.RatePlace.route) {
            RatePlaceScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        composable(route = Screen.GamesHub.route) {
            GamesHubScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToFlappy = {
                    navController.navigate(Screen.GameFlappy.route)
                }
            )
        }

        composable(route = Screen.GameFlappy.route) {
            GameFlappyScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

