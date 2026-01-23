package com.speedmenu.tablet.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.zIndex
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.AppTopBarContainer
import com.speedmenu.tablet.core.ui.components.CategoryCard
import com.speedmenu.tablet.core.ui.components.SidebarMenuItem
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle
import com.speedmenu.tablet.core.ui.components.TopRightStatusPill
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.screens.home.Sidebar

/**
 * Dados mockados de categoria.
 */
data class CategoryData(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val imageResId: Int
)

/**
 * Tela de Categorias do Cardápio.
 * Grid de categorias com filtros no topo.
 */
@Composable
fun CategoriesScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToCategory: (String) -> Unit = {}
) {
    // Estado para filtro selecionado
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterCalledDialog by remember { mutableStateOf(false) }
    
    // Dados mockados de categorias
    val categories = remember {
        listOf(
            CategoryData(
                id = "entradas",
                title = "Entradas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.Restaurant,
                imageResId = R.drawable.entradas
            ),
            CategoryData(
                id = "pratos",
                title = "Pratos Principais",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.LocalDining,
                imageResId = R.drawable.pratos_principais
            ),
            CategoryData(
                id = "bebidas",
                title = "Bebidas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.LocalBar,
                imageResId = R.drawable.bebidas
            ),
            CategoryData(
                id = "sobremesas",
                title = "Sobremesas",
                subtitle = "Coleção Autoral",
                description = "Explorar agora",
                icon = Icons.Default.Cake,
                imageResId = R.drawable.sobremesas
            )
        )
    }
    
    // Filtros mockados
    val filters = remember {
        listOf(
            "Favoritos da casa",
            "Novidades",
            "Menu degustação",
            "Opções veganas",
            "Sem glúten"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Sidebar fixa à esquerda (reutilizada da Home)
        Box(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
        ) {
            Sidebar(
                modifier = Modifier.fillMaxSize(),
                isVisible = true,
                onStartOrderClick = {
                    // Já estamos na tela de categorias, não precisa navegar
                }
            )
            
            // Borda sutil à direita
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(0.5.dp)
                    .fillMaxHeight()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
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
        ) {
            // ========== TOPBAR: Categorias + Status ==========
            AppTopBarContainer(
                modifier = Modifier.padding(horizontal = 40.dp),
                content = {
                    // Box com weight(1f) e clipToBounds() já aplicados pelo AppTopBarContainer
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val scrollState = rememberScrollState()
                        
                        // Estado para controlar visibilidade dos chevrons
                        var canScrollLeft by remember { mutableStateOf(false) }
                        var canScrollRight by remember { mutableStateOf(true) }
                        
                        // Observa mudanças no scroll para atualizar os chevrons
                        LaunchedEffect(scrollState) {
                            snapshotFlow { scrollState.value to scrollState.maxValue }
                                .distinctUntilChanged()
                                .collect { (value, maxValue) ->
                                    canScrollLeft = value > 0
                                    canScrollRight = value < maxValue
                                }
                        }
                        
                        // Row com categorias scrolláveis (centralizado verticalmente)
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .horizontalScroll(scrollState)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            filters.forEachIndexed { index, filter ->
                                val isSelected = selectedFilter == filter
                                
                                // Divisória vertical sutil (exceto no primeiro item)
                                if (index > 0) {
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .height(16.dp)
                                            .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.3f))
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                                
                                // Item de categoria minimalista
                                androidx.compose.foundation.layout.Box(
                                    modifier = Modifier
                                        .clickable {
                                            selectedFilter = if (selectedFilter == filter) null else filter
                                        }
                                        .padding(horizontal = 16.dp, vertical = 0.dp)
                                        .drawBehind {
                                            // Underline discreto para categoria ativa
                                            if (isSelected) {
                                                val strokeWidth = 2.dp.toPx()
                                                drawLine(
                                                    color = SpeedMenuColors.PrimaryLight,
                                                    start = Offset(0f, size.height),
                                                    end = Offset(size.width, size.height),
                                                    strokeWidth = strokeWidth
                                                )
                                            }
                                        }
                                ) {
                                    Text(
                                        text = filter,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (isSelected) {
                                            SpeedMenuColors.PrimaryLight
                                        } else {
                                            SpeedMenuColors.TextSecondary
                                        },
                                        fontSize = 15.sp,
                                        letterSpacing = 0.2.sp
                                    )
                                }
                            }
                        }
                        
                        // Edge fade direito (opcional, quando há mais conteúdo à direita)
                        if (canScrollRight) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .width(60.dp)
                                    .fillMaxHeight()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                SpeedMenuColors.BackgroundPrimary.copy(alpha = 0.95f),
                                                SpeedMenuColors.BackgroundPrimary
                                            )
                                        )
                                    )
                                    .zIndex(1f)
                            )
                        }
                        
                        // Edge fade esquerdo (opcional, quando já houve scroll)
                        if (canScrollLeft) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .width(40.dp)
                                    .fillMaxHeight()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                SpeedMenuColors.BackgroundPrimary,
                                                SpeedMenuColors.BackgroundPrimary.copy(alpha = 0.95f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                                    .zIndex(1f)
                            )
                        }
                        
                        // Chevron ESQUERDA (só se canScrollLeft)
                        if (canScrollLeft) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextTertiary,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 6.dp)
                                    .size(20.dp)
                                    .alpha(0.55f)
                                    .zIndex(3f)
                            )
                        }
                        
                        // Chevron DIREITA (só se canScrollRight)
                        if (canScrollRight) {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextTertiary,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 6.dp)
                                    .size(20.dp)
                                    .alpha(0.55f)
                                    .zIndex(3f)
                            )
                        }
                    }
                },
                statusPill = {
                        TopRightStatusPill(
                            onWaiterClick = {
                                showWaiterCalledDialog = true
                            }
                        )
                }
            )
            
            // Espaçamento entre TopBar e grid (máximo 12-16dp)
            Spacer(modifier = Modifier.height(12.dp))
            
            // ========== CONTEÚDO PRINCIPAL (Grid de categorias) ==========
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                // ========== GRID DE CATEGORIAS ==========
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 colunas para tablet
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(
                            title = category.title,
                            subtitle = category.subtitle,
                            description = category.description,
                            icon = category.icon,
                            imageResId = category.imageResId,
                            onClick = {
                                // Navega para tela de produtos da categoria
                                onNavigateToCategory(category.title)
                            },
                            onViewItemsClick = {
                                // Navega para tela de produtos da categoria
                                onNavigateToCategory(category.title)
                            }
                        )
                    }
                }
            }
            
            // Dialog de garçom chamado
            WaiterCalledDialog(
                visible = showWaiterCalledDialog,
                onDismiss = { showWaiterCalledDialog = false },
                onConfirm = { showWaiterCalledDialog = false }
            )
        }
    }
}

