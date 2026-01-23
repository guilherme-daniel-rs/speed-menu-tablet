package com.speedmenu.tablet.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.ProductCard
import com.speedmenu.tablet.core.ui.components.SidebarMenuItem
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.screens.home.Sidebar

/**
 * Tela de listagem de produtos/pratos de uma categoria.
 * Layout minimalista e premium com foco em imagens e decisão rápida.
 */
@Composable
fun ProductsScreen(
    categoryName: String,
    onNavigateBack: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToProductDetail: (String) -> Unit = {}
) {
    // Estado para controlar bottom sheet
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    // Estado mockado do carrinho
    var cartItemCount by remember { mutableStateOf(0) }
    
    // Dados mockados de produtos (usando imagens das categorias como placeholder)
    val products = remember(categoryName) {
        when (categoryName.lowercase()) {
            "entradas" -> listOf(
                Product("1", "Bruschetta Italiana", 24.90, R.drawable.entradas, "Pão artesanal com tomate, manjericão e azeite"),
                Product("2", "Carpaccio de Salmão", 32.50, R.drawable.entradas, "Salmão fresco com rúcula e parmesão"),
                Product("3", "Tartar de Atum", 28.90, R.drawable.entradas, "Atum fresco com abacate e molho especial"),
                Product("4", "Ceviche de Peixe", 29.90, R.drawable.entradas, "Peixe branco marinado com limão e cebola roxa"),
                Product("5", "Salada Caprese", 22.90, R.drawable.entradas, "Mozzarella, tomate e manjericão fresco"),
                Product("6", "Crostini de Queijo", 26.50, R.drawable.entradas, "Pão crocante com queijo brie e geleia"),
                Product("7", "Antepasto Italiano", 35.90, R.drawable.entradas, "Seleção de embutidos e queijos"),
                Product("8", "Tábua de Frios", 38.90, R.drawable.entradas, "Variedade de frios e queijos artesanais")
            )
            "pratos principais" -> listOf(
                Product("9", "Filé Mignon ao Molho", 68.90, R.drawable.pratos_principais, "Filé grelhado com molho especial"),
                Product("10", "Risotto de Camarão", 54.90, R.drawable.pratos_principais, "Arroz cremoso com camarões frescos"),
                Product("11", "Salmão Grelhado", 62.50, R.drawable.pratos_principais, "Salmão com legumes grelhados"),
                Product("12", "Penne ao Pesto", 42.90, R.drawable.pratos_principais, "Massa com molho pesto artesanal"),
                Product("13", "Frango à Parmegiana", 48.90, R.drawable.pratos_principais, "Frango empanado com molho de tomate"),
                Product("14", "Costela de Porco", 58.90, R.drawable.pratos_principais, "Costela assada com molho barbecue"),
                Product("15", "Lasanha Bolonhesa", 52.90, R.drawable.pratos_principais, "Lasanha tradicional italiana"),
                Product("16", "Peixe à Moda do Chef", 59.90, R.drawable.pratos_principais, "Peixe fresco com molho exclusivo")
            )
            "bebidas" -> listOf(
                Product("17", "Suco Natural Laranja", 12.90, R.drawable.bebidas, "Suco fresco de laranja"),
                Product("18", "Água com Gás", 6.90, R.drawable.bebidas, "Água mineral com gás"),
                Product("19", "Refrigerante", 8.90, R.drawable.bebidas, "Refrigerante gelado"),
                Product("20", "Cerveja Artesanal", 15.90, R.drawable.bebidas, "Cerveja artesanal local"),
                Product("21", "Vinho Tinto", 45.90, R.drawable.bebidas, "Vinho tinto selecionado"),
                Product("22", "Caipirinha", 18.90, R.drawable.bebidas, "Caipirinha tradicional"),
                Product("23", "Água Mineral", 5.90, R.drawable.bebidas, "Água mineral sem gás"),
                Product("24", "Suco Detox", 14.90, R.drawable.bebidas, "Suco verde detox")
            )
            "sobremesas" -> listOf(
                Product("25", "Brownie com Sorvete", 22.90, R.drawable.sobremesas, "Brownie quente com sorvete de creme"),
                Product("26", "Tiramisu", 24.90, R.drawable.sobremesas, "Tiramisu tradicional italiano"),
                Product("27", "Cheesecake de Frutas", 23.90, R.drawable.sobremesas, "Cheesecake com frutas vermelhas"),
                Product("28", "Pudim de Leite", 18.90, R.drawable.sobremesas, "Pudim caseiro com calda"),
                Product("29", "Mousse de Chocolate", 20.90, R.drawable.sobremesas, "Mousse cremosa de chocolate"),
                Product("30", "Petit Gateau", 26.90, R.drawable.sobremesas, "Bolinho quente com sorvete"),
                Product("31", "Torta de Limão", 21.90, R.drawable.sobremesas, "Torta refrescante de limão"),
                Product("32", "Sorvete Artesanal", 16.90, R.drawable.sobremesas, "Sorvete artesanal com cobertura")
            )
            else -> emptyList()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Sidebar fixa à esquerda (reutilizada)
        Box(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
        ) {
            Sidebar(
                modifier = Modifier.fillMaxSize(),
                isVisible = true,
                onStartOrderClick = {
                    // Já estamos na tela de produtos, não precisa navegar
                }
            )
            
            // Borda sutil à direita
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(0.5.dp)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f),
                                SpeedMenuColors.BorderSubtle.copy(alpha = 0.15f),
                                SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        // Área principal à direita
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.BackgroundPrimary,
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.08f, green = 0.10f, blue = 0.08f),
                            SpeedMenuColors.BackgroundSecondary
                        )
                    )
                )
                .padding(horizontal = 40.dp, vertical = 32.dp)
        ) {
            // ========== HEADER ==========
            ProductsHeader(
                categoryName = categoryName,
                productCount = products.size,
                cartItemCount = cartItemCount,
                onBackClick = onNavigateBack,
                onCartClick = onNavigateToCart,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // ========== GRID DE PRODUTOS ==========
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 colunas para tablet
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                items(products) { product ->
                    // Badge emocional apenas para alguns produtos específicos
                    val badgeText = when (product.id) {
                        "1" -> "Mais pedido" // Bruschetta Italiana
                        "9" -> "Chef recomenda" // Filé Mignon ao Molho
                        "25" -> "Mais pedido" // Brownie com Sorvete
                        else -> null
                    }
                    
                    ProductCard(
                        name = product.name,
                        price = product.price,
                        imageResId = product.imageResId,
                        onClick = {
                            // Navega para tela de detalhes do produto
                            onNavigateToProductDetail(product.id)
                        },
                        onSelectClick = {
                            // Adiciona ao carrinho (mockado)
                            cartItemCount++
                            // TODO: Implementar lógica real de carrinho
                        },
                        badgeText = badgeText
                    )
                }
            }
        }
    }
    
    // ========== BOTTOM SHEET DE DETALHES ==========
    selectedProduct?.let { product ->
        ProductDetailsBottomSheet(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = {
                cartItemCount++
                selectedProduct = null
                // TODO: Implementar lógica real de carrinho
            }
        )
    }
}

