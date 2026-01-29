package com.speedmenu.tablet.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Constraints
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.SidebarMenuItem
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle
import com.speedmenu.tablet.core.ui.components.SpeedMenuBadge
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState

/**
 * FONTE ÚNICA DE VERDADE: Lista de itens do menu da Home.
 * Esta lista é compartilhada entre todos os layouts (sidebar fixa, drawer, etc.)
 * para garantir consistência e evitar duplicação.
 */
@Composable
private fun rememberHomeMenuItems(
    onStartOrderClick: () -> Unit,
    onViewOrderClick: () -> Unit,
    onRatePlaceClick: () -> Unit,
    onGamesClick: () -> Unit,
    onAiAssistantClick: () -> Unit
): List<HomeMenuItem> {
    return remember(
        onStartOrderClick,
        onViewOrderClick,
        onRatePlaceClick,
        onGamesClick,
        onAiAssistantClick
    ) {
        listOf(
            HomeMenuItem(
                text = "Iniciar pedido",
                icon = Icons.Default.ShoppingCart,
                style = SidebarMenuItemStyle.PRIMARY,
                onClick = onStartOrderClick,
                animationDelay = 0,
                needsPadding = false
            ),
            HomeMenuItem(
                text = "Ver meu pedido",
                icon = Icons.Default.Visibility,
                style = SidebarMenuItemStyle.SECONDARY,
                onClick = onViewOrderClick,
                animationDelay = 200,
                needsPadding = true
            ),
            HomeMenuItem(
                text = "Pergunte à IA",
                icon = Icons.Default.AutoAwesome,
                style = SidebarMenuItemStyle.AI_BORDERED,
                onClick = onAiAssistantClick,
                animationDelay = 300,
                needsPadding = true
            ),
            HomeMenuItem(
                text = "Jogos",
                icon = Icons.Default.SportsEsports,
                style = SidebarMenuItemStyle.SECONDARY,
                onClick = onGamesClick,
                animationDelay = 400,
                needsPadding = true
            ),
            HomeMenuItem(
                text = "Avaliar o local",
                icon = Icons.Default.Star,
                style = SidebarMenuItemStyle.SECONDARY,
                onClick = onRatePlaceClick,
                animationDelay = 500,
                needsPadding = true
            )
        )
    }
}

/**
 * Tela Home do SpeedMenuTablet.
 * Layout responsivo: sidebar fixa para tablets (Expanded) e drawer para celulares (Compact/Medium).
 */
@Composable
fun HomeScreen(
    onNavigateToCategories: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToViewOrder: () -> Unit = {},
    onNavigateToRatePlace: () -> Unit = {},
    onNavigateToGames: () -> Unit = {},
    onNavigateToAiAssistant: () -> Unit = {},
    cartItemCount: Int = 0
) {
    // Detectar tamanho de tela usando LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val isExpanded = screenWidthDp >= 840 // Tablet (Expanded)
    val isCompact = screenWidthDp < 600 // Celular (Compact)
    
    // Estado para controlar animação de entrada
    var isVisible by remember { mutableStateOf(false) }
    
    // WaiterViewModel centralizado para gerenciar chamadas de garçom
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    // Estado do drawer (apenas para celular)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    
    // Lista única de itens do menu (fonte única de verdade)
    val menuItems = rememberHomeMenuItems(
        onStartOrderClick = onNavigateToCategories,
        onViewOrderClick = onNavigateToViewOrder,
        onRatePlaceClick = onNavigateToRatePlace,
        onGamesClick = onNavigateToGames,
        onAiAssistantClick = onNavigateToAiAssistant
    )
    
    // Log de debug para validar que todos os itens estão sendo incluídos
    LaunchedEffect(menuItems.size) {
        Log.d("HomeScreen", "📋 Menu montado com ${menuItems.size} itens (screenWidthDp: $screenWidthDp, isExpanded: $isExpanded)")
        menuItems.forEachIndexed { index, item ->
            Log.d("HomeScreen", "  ${index + 1}. ${item.text}")
        }
    }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Layout responsivo: drawer para celular, sidebar fixa para tablet
    if (isExpanded) {
        // ========== LAYOUT TABLET: Sidebar fixa + conteúdo principal ==========
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SpeedMenuColors.BackgroundPrimary)
        ) {
            // Top bar
            AppTopBar(
                showBackButton = false,
                isConnected = true,
                tableNumber = "17",
                onCallWaiterClick = {
                    waiterViewModel.requestWaiter("HomeScreen")
                },
                screenName = "HomeScreen"
            )
            
            // Conteúdo principal com sidebar fixa
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SpeedMenuColors.BackgroundPrimary)
            ) {
                // Sidebar fixa à esquerda
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(600, easing = LinearEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(600, easing = LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .width(280.dp)
                            .fillMaxHeight()
                    ) {
                        Sidebar(
                            modifier = Modifier.fillMaxSize(),
                            isVisible = isVisible,
                            menuItems = menuItems
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
                }

                // Conteúdo principal à direita
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(800, delayMillis = 200, easing = LinearEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(800, delayMillis = 200, easing = LinearEasing)
                    )
                ) {
                    HomeContent(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    } else {
        // ========== LAYOUT CELULAR: ModalNavigationDrawer ==========
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    menuItems = menuItems,
                    isVisible = isVisible,
                    onCloseDrawer = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    // Top bar com botão hamburger para abrir drawer
            AppTopBar(
                showBackButton = false,
                isConnected = true,
                tableNumber = "17",
                onCallWaiterClick = {
                    waiterViewModel.requestWaiter("HomeScreen")
                },
                screenName = "HomeScreen",
                onMenuClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            ) { innerPadding ->
                // Conteúdo principal (hero image)
                HomeContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
    
    // Dialog de garçom chamado (gerenciado pelo WaiterViewModel)
    WaiterCalledDialog(
        visible = waiterUiState.showDialog,
        onDismiss = { waiterViewModel.dismissDialog() },
        onConfirm = { waiterViewModel.confirmWaiterCall() }
    )
}

/**
 * Data class que representa um item do menu lateral.
 * Fonte única de verdade para garantir que todos os itens sejam sempre incluídos.
 */
internal data class HomeMenuItem(
    val text: String,
    val icon: ImageVector,
    val style: SidebarMenuItemStyle,
    val onClick: () -> Unit,
    val animationDelay: Int = 0,
    val needsPadding: Boolean = false // Se precisa de padding horizontal (itens secundários)
)

/**
 * Tamanhos responsivos para a sidebar baseados na altura disponível.
 */
private data class ResponsiveSizes(
    val logoHeight: androidx.compose.ui.unit.Dp,
    val headerHeight: androidx.compose.ui.unit.Dp,
    val itemHeight: androidx.compose.ui.unit.Dp,
    val itemSpacing: androidx.compose.ui.unit.Dp,
    val sectionSpacing: androidx.compose.ui.unit.Dp,
    val iconSize: androidx.compose.ui.unit.Dp,
    val fontSize: androidx.compose.ui.unit.TextUnit,
    val bottomPadding: androidx.compose.ui.unit.Dp
)

/**
 * Calcula tamanhos responsivos baseados na altura disponível.
 */
private fun calculateResponsiveSizes(maxHeight: androidx.compose.ui.unit.Dp): ResponsiveSizes {
    return when {
        maxHeight < 520.dp -> {
            // Altura pequena (celular landscape / telas baixas)
            ResponsiveSizes(
                logoHeight = 44.dp,
                headerHeight = 80.dp,
                itemHeight = 44.dp,
                itemSpacing = 8.dp,
                sectionSpacing = 12.dp,
                iconSize = 18.dp,
                fontSize = 13.sp,
                bottomPadding = 24.dp
            )
        }
        maxHeight in 520.dp..650.dp -> {
            // Altura média
            ResponsiveSizes(
                logoHeight = 56.dp,
                headerHeight = 100.dp,
                itemHeight = 52.dp,
                itemSpacing = 10.dp,
                sectionSpacing = 14.dp,
                iconSize = 20.dp,
                fontSize = 14.sp,
                bottomPadding = 28.dp
            )
        }
        else -> {
            // Altura grande (tablet)
            ResponsiveSizes(
                logoHeight = 72.dp,
                headerHeight = 140.dp,
                itemHeight = 60.dp,
                itemSpacing = 12.dp,
                sectionSpacing = 16.dp,
                iconSize = 24.dp,
                fontSize = 16.sp,
                bottomPadding = 32.dp
            )
        }
    }
}

/**
 * Sidebar fixa com logo, CTA principal e itens de menu.
 * Elemento forte de identidade visual com gradiente vertical sutil e continuidade com o ambiente.
 * Recebe a lista de itens como parâmetro (fonte única de verdade).
 * Layout responsivo SEM scroll - sempre mostra os 5 itens adaptando-se à altura disponível.
 */
@Composable
internal fun Sidebar(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    menuItems: List<HomeMenuItem>
) {
    BoxWithConstraints(modifier = modifier.fillMaxHeight()) {
        // Calcular tamanhos responsivos baseados na altura disponível
        val sizes = calculateResponsiveSizes(maxHeight = maxHeight)
        
        Box(modifier = Modifier.fillMaxSize()) {
            // ========== CAMADA 1: Gradiente vertical base refinado (dark → um pouco mais claro) ==========
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SpeedMenuColors.BackgroundPrimary,
                                SpeedMenuColors.BackgroundPrimary.copy(red = 0.105f, green = 0.125f, blue = 0.105f),
                                SpeedMenuColors.BackgroundPrimary.copy(red = 0.11f, green = 0.13f, blue = 0.11f),
                                SpeedMenuColors.BackgroundPrimary.copy(red = 0.12f, green = 0.14f, blue = 0.12f),
                                SpeedMenuColors.BackgroundPrimary.copy(red = 0.13f, green = 0.15f, blue = 0.13f)
                            )
                        )
                    )
            )
            
            // ========== CAMADA 2: Overlay radial sutil para profundidade ==========
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x10000000),
                                Color(0x00000000)
                            ),
                            radius = 400f
                        )
                    )
            )
            
            // ========== CAMADA 3: Overlay horizontal ultra-sutil para transição ==========
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x00000000),
                                Color(0x05000000),
                                Color(0x0A000000)
                            )
                        )
                    )
            )
            
            // ========== CAMADA 4: Conteúdo da sidebar (SEM SCROLL) ==========
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = sizes.bottomPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // ========== SEÇÃO 1: Header de marca premium (tamanho responsivo) ==========
                SidebarHeader(
                    logoHeight = sizes.logoHeight,
                    headerHeight = sizes.headerHeight
                )
                
                // Espaçamento após header
                Spacer(modifier = Modifier.height(sizes.sectionSpacing))
                
                // ========== SEÇÃO 2: Itens do Menu (Fonte Única de Verdade) ==========
                // Lista dos 5 itens SEM scroll - sempre visíveis
                menuItems.forEachIndexed { index, menuItem ->
                    // Espaçamento entre itens (exceto antes do primeiro)
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(sizes.itemSpacing))
                    }
                    
                    // Renderiza o item com animação e altura responsiva
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(
                            animationSpec = tween(400, delayMillis = menuItem.animationDelay, easing = LinearEasing)
                        ) + slideInHorizontally(
                            initialOffsetX = { -it / 3 },
                            animationSpec = tween(400, delayMillis = menuItem.animationDelay, easing = LinearEasing)
                        )
                    ) {
                        // Container com padding condicional baseado no item
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (menuItem.needsPadding) {
                                        Modifier.padding(horizontal = 28.dp)
                                    } else {
                                        Modifier
                                    }
                                )
                        ) {
                            // Item com altura responsiva
                            ResponsiveSidebarMenuItem(
                                text = menuItem.text,
                                icon = menuItem.icon,
                                onClick = menuItem.onClick,
                                style = menuItem.style,
                                itemHeight = sizes.itemHeight,
                                iconSize = sizes.iconSize,
                                fontSize = sizes.fontSize,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                
                // Spacer com weight para empurrar conteúdo quando sobrar espaço
                // Isso garante que o padding inferior sempre seja respeitado
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * Conteúdo do drawer para layout mobile (celular).
 * Reutiliza a mesma lista de itens do menu (fonte única de verdade).
 * Layout responsivo SEM scroll - sempre mostra os 5 itens adaptando-se à altura disponível.
 */
@Composable
private fun DrawerContent(
    menuItems: List<HomeMenuItem>,
    isVisible: Boolean = true,
    onCloseDrawer: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Calcular tamanhos responsivos baseados na altura disponível
        val sizes = calculateResponsiveSizes(maxHeight = maxHeight)
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = sizes.bottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header do drawer (mesmo estilo da sidebar, tamanho responsivo)
            SidebarHeader(
                logoHeight = sizes.logoHeight,
                headerHeight = sizes.headerHeight
            )
            
            // Espaçamento após header
            Spacer(modifier = Modifier.height(sizes.sectionSpacing))
            
            // Itens do menu (mesma lista compartilhada) - SEM SCROLL
            menuItems.forEachIndexed { index, menuItem ->
                // Espaçamento entre itens (exceto antes do primeiro)
                if (index > 0) {
                    Spacer(modifier = Modifier.height(sizes.itemSpacing))
                }
                
                // Renderiza o item com animação e altura responsiva
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(400, delayMillis = menuItem.animationDelay, easing = LinearEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { -it / 3 },
                        animationSpec = tween(400, delayMillis = menuItem.animationDelay, easing = LinearEasing)
                    )
                ) {
                    // Container com padding condicional baseado no item
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (menuItem.needsPadding) {
                                    Modifier.padding(horizontal = 28.dp)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        // Item com altura responsiva
                        ResponsiveSidebarMenuItem(
                            text = menuItem.text,
                            icon = menuItem.icon,
                            onClick = {
                                menuItem.onClick()
                                onCloseDrawer() // Fecha o drawer ao clicar em um item
                            },
                            style = menuItem.style,
                            itemHeight = sizes.itemHeight,
                            iconSize = sizes.iconSize,
                            fontSize = sizes.fontSize,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // Spacer com weight para empurrar conteúdo quando sobrar espaço
            // Isso garante que o padding inferior sempre seja respeitado
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * Item do menu lateral com tamanhos responsivos.
 * Wrapper que aplica altura e tamanhos responsivos ao SidebarMenuItem.
 * Usa clipToBounds para garantir que o conteúdo não ultrapasse a altura desejada.
 */
@Composable
private fun ResponsiveSidebarMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    style: SidebarMenuItemStyle,
    itemHeight: androidx.compose.ui.unit.Dp,
    iconSize: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    modifier: Modifier = Modifier
) {
    // Para PRIMARY, usar altura maior proporcionalmente
    // Para SECONDARY/AI_BORDERED, usar altura direta
    val finalHeight = when (style) {
        SidebarMenuItemStyle.PRIMARY -> {
            // PRIMARY normalmente é 96dp, mas ajustamos proporcionalmente
            // Se itemHeight é 44dp (compacto), PRIMARY fica ~66dp
            // Se itemHeight é 60dp (tablet), PRIMARY fica ~90dp
            (itemHeight.value * 1.5f).dp.coerceAtLeast(60.dp)
        }
        else -> itemHeight
    }
    
    Box(
        modifier = modifier
            .height(finalHeight)
            .clip(RoundedCornerShape(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        SidebarMenuItem(
            text = text,
            icon = icon,
            onClick = onClick,
            style = style,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Header premium da sidebar com logo de marca.
 * Container dedicado que dá presença e hierarquia visual forte à identidade.
 * Tamanhos responsivos baseados na altura disponível.
 */
@Composable
private fun SidebarHeader(
    logoHeight: androidx.compose.ui.unit.Dp = 72.dp,
    headerHeight: androidx.compose.ui.unit.Dp = 140.dp
) {
    // ColorMatrix para aumentar leve contraste e brilho (destacar no fundo escuro)
    val logoEnhancementMatrix = remember {
        ColorMatrix(floatArrayOf(
            // Contraste: 1.15 (aumenta contraste sutilmente)
            1.15f, 0f, 0f, 0f, 0.08f, // R: contraste + leve brilho
            0f, 1.15f, 0f, 0f, 0.08f, // G: contraste + leve brilho
            0f, 0f, 1.15f, 0f, 0.08f, // B: contraste + leve brilho
            0f, 0f, 0f, 1f, 0f       // Alpha: sem alteração
        ))
    }
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Container do header com altura responsiva
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) {
            // Overlay escuro sutil para destaque (6% de opacidade)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.06f) // Overlay escuro discreto
                    )
            )
            
            // Logo centralizada (vertical e horizontalmente)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp), // Respiro lateral
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo do restaurante",
                    modifier = Modifier
                        .fillMaxWidth(0.75f) // Logo maior e mais dominante (75% da largura)
                        .heightIn(max = logoHeight) // Altura responsiva
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit, // Mantém proporção sem distorção
                    colorFilter = ColorFilter.colorMatrix(logoEnhancementMatrix) // Leve aumento de contraste/brilho
                )
            }
        }
        
        // Divisor discreto abaixo do header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 28.dp) // Respiro lateral no divisor
                .background(
                    color = Color.White.copy(alpha = 0.10f) // Linha branca com 10% de opacidade
                )
        )
        
        // Espaçamento generoso entre header e primeiro item (respiro claro)
        Spacer(modifier = Modifier.height(32.dp))
    }
}


/**
 * Conteúdo principal da Home com banner e informações.
 */
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Banner principal com imagem de fundo
        HomeBanner()
    }
}

/**
 * Banner principal com carrossel de imagens e texto de destaque.
 * Fundo sofisticado com múltiplas camadas visuais para profundidade.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeBanner() {
    // Lista de imagens do carrossel
    val coverImages = remember {
        listOf(
            R.drawable.capa,      // capa.jpeg
            R.drawable.capa_2,    // capa-2.png
            R.drawable.capa_3     // capa-3.jpg
        )
    }
    
    // Estado do pager
    val pagerState = rememberPagerState(pageCount = { coverImages.size }, initialPage = 0)
    
    // Auto-play do carrossel (muda de página a cada 5 segundos)
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % coverImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // ========== CAMADA 0: Fallback background (por baixo, caso imagens não carreguem) ==========
        // Gradiente sofisticado (tons escuros com leve tom gastronômico)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.12f, green = 0.15f, blue = 0.10f), // Tom escuro com leve verde
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.08f, green = 0.10f, blue = 0.08f), // Mais escuro
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.05f, green = 0.07f, blue = 0.05f), // Muito escuro
                            SpeedMenuColors.BackgroundSecondary  // Quase preto
                        )
                    )
                )
        )
        
        // ========== CAMADA 1: Carrossel de imagens ==========
        // ColorMatrix para aumentar brilho e contraste (imagens mais vivas e apetitosas)
        // Ajuste sutil: brilho e contraste levemente aumentados
        val brightnessContrastMatrix = remember {
            ColorMatrix(floatArrayOf(
                // Contraste: 1.12 (aumenta contraste sutilmente)
                1.12f, 0f, 0f, 0f, 0.12f, // R: contraste + brilho aumentado
                0f, 1.12f, 0f, 0f, 0.12f, // G: contraste + brilho aumentado
                0f, 0f, 1.12f, 0f, 0.12f, // B: contraste + brilho aumentado
                0f, 0f, 0f, 1f, 0f       // Alpha: sem alteração
            ))
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = coverImages[page]),
                contentDescription = "Imagem de capa ${page + 1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(brightnessContrastMatrix)
            )
        }

        // ========== CAMADA 2: Padrão sutil de profundidade ==========
        // Gradiente horizontal para adicionar dimensão (reduzido ainda mais para mais clareza)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x06000000), // Reduzido de 0x0A para 0x06 (ainda menos escuro à esquerda)
                            Color(0x00000000), // Transparente no centro
                            Color(0x0F000000)  // Reduzido de 0x15 para 0x0F (ainda menos escuro à direita)
                        )
                    )
                )
        )

        // ========== CAMADA 3: Vignette radial ==========
        // Escurecer bordas para foco central (reduzido ainda mais para mais clareza)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x00000000), // Transparente no centro
                            Color(0x18000000), // Reduzido de 0x25 para 0x18 (ainda menos escuro nas bordas)
                            Color(0x40000000)  // Reduzido de 0x50 para 0x40 (ainda menos escuro nas bordas externas)
                        ),
                        radius = 1200f
                    )
                )
        )

        // ========== CAMADA 4: Overlay de contraste ==========
        // Overlay escuro para melhorar contraste do texto (reduzido ainda mais para mais clareza, mantendo legibilidade)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.OverlayLight.copy(alpha = 0.28f), // Reduzido de 0.35f para 0.28f (topo)
                            SpeedMenuColors.Overlay.copy(alpha = 0.48f), // Reduzido de 0.55f para 0.48f (meio)
                            SpeedMenuColors.Overlay.copy(alpha = 0.62f)  // Reduzido de 0.70f para 0.62f (base)
                        )
                    )
                )
        )
        
        // ========== CAMADA 4.5: Indicadores do carrossel ==========
        // Indicadores discretos no canto inferior direito
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 40.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(coverImages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (isSelected) {
                                SpeedMenuColors.PrimaryLight
                            } else {
                                SpeedMenuColors.PrimaryLight.copy(alpha = 0.4f)
                            }
                        )
                )
            }
        }
    }
}

/**
 * Widget de status do sistema no topo direito.
 * Comunica estado de conexão, mesa e ações rápidas.
 * Aparência de widget de status profissional.
 * Reutilizável em outras telas.
 */
@Composable
internal fun TopRightInfo(
    modifier: Modifier = Modifier,
    onWaiterClick: () -> Unit = {}
) {
    // Container agrupado com fundo semi-transparente
    Box(
        modifier = modifier
            .background(
                color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.75f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Status de conexão (ícone + indicador)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Ícone de WiFi com cor de sucesso sutil
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Wifi,
                        contentDescription = "Conectado",
                        tint = SpeedMenuColors.Success.copy(alpha = 0.85f),
                        modifier = Modifier.size(18.dp)
                    )
                }
                // Indicador de status conectado
                Text(
                    text = "Conectado",
                    style = MaterialTheme.typography.bodySmall,
                    color = SpeedMenuColors.TextTertiary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Divisor sutil
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.4f))
            )

            // Informação da mesa
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Ícone de mesa discreto
                Icon(
                    imageVector = Icons.Default.TableRestaurant,
                    contentDescription = "Mesa",
                    tint = SpeedMenuColors.TextTertiary.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Mesa 17",
                    style = MaterialTheme.typography.bodySmall,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Divisor sutil
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.4f))
            )

            // Ação rápida: Chamar garçom (com micro-interação)
            val waiterInteractionSource = remember { MutableInteractionSource() }
            val isWaiterPressed by waiterInteractionSource.collectIsPressedAsState()
            
            val waiterIconColor by animateColorAsState(
                targetValue = if (isWaiterPressed) {
                    SpeedMenuColors.PrimaryLight
                } else {
                    SpeedMenuColors.PrimaryLight.copy(alpha = 0.8f)
                },
                animationSpec = tween(150),
                label = "waiter_icon_color"
            )
            
            val waiterTextColor by animateColorAsState(
                targetValue = if (isWaiterPressed) {
                    SpeedMenuColors.TextPrimary
                } else {
                    SpeedMenuColors.TextSecondary
                },
                animationSpec = tween(150),
                label = "waiter_text_color"
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.clickable(
                    interactionSource = waiterInteractionSource,
                    indication = null,
                    onClick = onWaiterClick
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Chamar garçom",
                    tint = waiterIconColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Garçom",
                    style = MaterialTheme.typography.bodySmall,
                    color = waiterTextColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

