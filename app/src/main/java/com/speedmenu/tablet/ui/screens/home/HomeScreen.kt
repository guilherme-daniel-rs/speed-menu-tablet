package com.speedmenu.tablet.ui.screens.home

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.TableRestaurant
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.components.SidebarMenuItem
import com.speedmenu.tablet.core.ui.components.SidebarMenuItemStyle
import com.speedmenu.tablet.core.ui.components.SpeedMenuBadge
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Tela Home do SpeedMenuTablet.
 * Layout dividido em sidebar fixa à esquerda e conteúdo principal à direita.
 * Design pensado para tablets em modo quiosque.
 */
@Composable
fun HomeScreen() {
    // Estado para controlar animação de entrada
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Sidebar fixa à esquerda com animação de entrada
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
                    isVisible = isVisible
                )
                
                // Borda extremamente sutil à direita (quase imperceptível para continuidade máxima)
                // Apenas uma sugestão visual mínima, não um divisor - integração total com o ambiente
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(0.5.dp) // Ainda mais fino
                        .fillMaxHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f), // Quase imperceptível
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.15f), // Apenas sugestão mínima
                                    SpeedMenuColors.BorderSubtle.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }

        // Conteúdo principal à direita com animação de entrada
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

/**
 * Sidebar fixa com logo, CTA principal e itens de menu.
 * Elemento forte de identidade visual com gradiente vertical sutil e continuidade com o ambiente.
 */
@Composable
private fun Sidebar(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    Box(modifier = modifier) {
        // ========== CAMADA 1: Gradiente vertical base refinado (dark → um pouco mais claro) ==========
        // Cria sensação de profundidade orgânica e continuidade visual perfeita
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.BackgroundPrimary, // Topo: exatamente o mesmo tom do background principal (continuidade total)
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.105f, green = 0.125f, blue = 0.105f), // 25%: levemente mais claro
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.11f, green = 0.13f, blue = 0.11f), // 50%: um pouco mais claro
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.12f, green = 0.14f, blue = 0.12f), // 75%: mais claro
                            SpeedMenuColors.BackgroundPrimary.copy(red = 0.13f, green = 0.15f, blue = 0.13f)  // Base: mais claro (profundidade sutil)
                        )
                    )
                )
        )
        
        // ========== CAMADA 2: Overlay radial sutil para profundidade ==========
        // Cria leve elevação visual no centro, reduz sensação de painel plano
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x10000000), // Leve escurecimento no centro
                            Color(0x00000000)  // Transparente nas bordas
                        ),
                        radius = 400f
                    )
                )
        )
        
        // ========== CAMADA 3: Overlay horizontal ultra-sutil para transição ==========
        // Reduz sensação de painel sólido, cria continuidade perfeita com conteúdo principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x00000000), // Totalmente transparente à esquerda (continuidade absoluta)
                            Color(0x05000000), // Overlay quase imperceptível no centro
                            Color(0x0A000000)  // Leve overlay à direita (transição suave)
                        )
                    )
                )
        )
        
        // ========== CAMADA 4: Conteúdo da sidebar ==========
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp), // Apenas padding vertical - horizontal removido para permitir faixa completa
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ========== SEÇÃO 1: Identidade (Logo) ==========
            Box(
                modifier = Modifier.padding(horizontal = 28.dp) // Padding aplicado apenas no logo
            ) {
                RestaurantLogo()
            }
            
            // Espaçamento uniforme: régua única de 32dp entre todos os itens
            Spacer(modifier = Modifier.height(32.dp))

            // ========== SEÇÃO 2: Ação Principal (CTA) ==========
            // Container próprio que ocupa 100% da largura da sidebar - sem padding horizontal
            SidebarMenuItem(
                text = "Iniciar pedido",
                icon = Icons.Default.ShoppingCart,
                onClick = {
                    // TODO: Implementar navegação para tela de cardápio
                },
                style = SidebarMenuItemStyle.PRIMARY,
                modifier = Modifier.fillMaxWidth() // Ocupa 100% da largura da sidebar
            )
            
            // Espaçamento uniforme: mesma régua de 32dp
            Spacer(modifier = Modifier.height(32.dp))

            // ========== SEÇÃO 3: Navegação Secundária (Itens Editoriais) ==========
            // Container com padding horizontal para os itens secundários
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp) // Padding aplicado apenas nos itens secundários
            ) {
                // Item 1: Ver meu pedido (com animação de entrada sutil)
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(400, delayMillis = 300, easing = LinearEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { -it / 3 }, // Deslocamento sutil
                        animationSpec = tween(400, delayMillis = 300, easing = LinearEasing)
                    )
                ) {
                    SidebarMenuItem(
                        text = "Ver meu pedido",
                        icon = Icons.Default.Visibility,
                        onClick = {
                            // TODO: Implementar navegação para tela de pedido
                        },
                        style = SidebarMenuItemStyle.SECONDARY,
                        modifier = Modifier.fillMaxWidth() // Garantir alinhamento horizontal
                    )
                }

                // Espaçamento uniforme: mesma régua de 32dp
                Spacer(modifier = Modifier.height(32.dp))

                // Item 2: Avaliar o local (com animação de entrada sutil)
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(400, delayMillis = 400, easing = LinearEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { -it / 3 }, // Deslocamento sutil
                        animationSpec = tween(400, delayMillis = 400, easing = LinearEasing)
                    )
                ) {
                    SidebarMenuItem(
                        text = "Avaliar o local",
                        icon = Icons.Default.Star,
                        onClick = {
                            // TODO: Implementar navegação para tela de avaliação
                        },
                        style = SidebarMenuItemStyle.SECONDARY,
                        modifier = Modifier.fillMaxWidth() // Garantir alinhamento horizontal
                    )
                }
            }
        }
    }
}

/**
 * Logo do restaurante como assinatura de marca.
 * Identidade visual refinada, sem aparência de botão ou ação.
 */
@Composable
private fun RestaurantLogo() {
    Column(
        modifier = Modifier
            .padding(vertical = 24.dp), // Espaço negativo generoso ao redor
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto da marca com tipografia refinada
        Text(
            text = "SPEED\nMENU",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold, // Menos peso que Bold
            color = SpeedMenuColors.TextPrimary, // Branco direto, sem background
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp, // Espaçamento de letras refinado
            lineHeight = 32.sp // Altura de linha confortável
        )
        
        // Linha decorativa sutil abaixo do texto (assinatura visual)
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(2.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            SpeedMenuColors.Primary.copy(alpha = 0.4f), // Muito sutil
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(1.dp)
                )
        )
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

        // Topo direito: Chamar garçom e número da mesa
        TopRightInfo(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
        )
    }
}

/**
 * Banner principal com imagem de fundo e texto de destaque.
 * Fundo sofisticado com múltiplas camadas visuais para profundidade.
 */
@Composable
private fun HomeBanner() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // ========== CAMADA 1: Background base ==========
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

        // ========== CAMADA 2: Padrão sutil de profundidade ==========
        // Gradiente horizontal para adicionar dimensão
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x15000000), // Levemente escuro à esquerda
                            Color(0x00000000), // Transparente no centro
                            Color(0x25000000)  // Levemente escuro à direita
                        )
                    )
                )
        )

        // ========== CAMADA 3: Vignette radial ==========
        // Escurecer bordas para foco central
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x00000000), // Transparente no centro
                            Color(0x40000000), // Escuro nas bordas
                            Color(0x80000000)  // Muito escuro nas bordas externas
                        ),
                        radius = 1200f
                    )
                )
        )

        // ========== CAMADA 4: Overlay de contraste ==========
        // Overlay escuro para melhorar contraste do texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.OverlayLight.copy(alpha = 0.6f), // Overlay leve (topo)
                            SpeedMenuColors.Overlay.copy(alpha = 0.85f), // Overlay mais opaco (meio)
                            SpeedMenuColors.Overlay  // Overlay muito opaco (base)
                        )
                    )
                )
        )

        // ========== CAMADA 5: Elementos informativos (chips) ==========
        // Chips no canto inferior esquerdo para contexto sem poluir
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 40.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Chip: Destaque do dia
            SpeedMenuBadge(
                text = "Destaque do dia",
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
            )
            
            // Chip: Tempo médio de preparo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = SpeedMenuColors.PrimaryLight.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                SpeedMenuBadge(
                    text = "~15 min",
                    modifier = Modifier,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }

        // ========== CAMADA 6: Conteúdo principal (texto) ==========
        // Texto de destaque agrupado em Column, alinhado à direita
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 80.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Texto secundário: menor e mais leve
            Text(
                text = "Cremosamente",
                style = MaterialTheme.typography.bodyMedium,
                color = SpeedMenuColors.PrimaryLight, // Laranja claro (estilo gastronômico)
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 1.2.sp
            )

            // Espaçamento aumentado para melhor hierarquia visual
            Spacer(modifier = Modifier.height(16.dp))

            // Texto principal: muito grande e bold para máximo impacto
            Text(
                text = "IRRESISTÍVEL!",
                style = MaterialTheme.typography.displayLarge,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 100.sp,
                letterSpacing = (-1).sp
            )
        }
    }
}

/**
 * Widget de status do sistema no topo direito.
 * Comunica estado de conexão, mesa e ações rápidas.
 * Aparência de widget de status profissional.
 */
@Composable
private fun TopRightInfo(
    modifier: Modifier = Modifier
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
                    onClick = {
                        // TODO: Implementar ação de chamar garçom
                    }
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

