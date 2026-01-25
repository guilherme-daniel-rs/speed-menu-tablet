package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import kotlinx.coroutines.delay

/**
 * Componente reutilizável de status no topo direito.
 * Exibe: Conectado, Mesa, botão Garçom e botão Pedido.
 * Posicionamento pixel-perfect consistente em todas as telas.
 * 
 * @param onWaiterClick Callback quando o botão "Garçom" é clicado
 * @param onCartClick Callback quando o botão "Pedido" é clicado
 * @param cartItemCount Quantidade de itens no carrinho (0 = carrinho vazio, sem badge)
 * @param modifier Modifier para customização adicional
 */
@Composable
fun TopRightStatusPill(
    onWaiterClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    cartItemCount: Int = 0,
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
            horizontalArrangement = Arrangement.spacedBy(14.dp)
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

            // Divisor sutil
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.4f))
            )

            // Ação rápida: Ver pedido (com micro-interação)
            val cartInteractionSource = remember { MutableInteractionSource() }
            val isCartPressed by cartInteractionSource.collectIsPressedAsState()
            
            // Animação de scale quando cartItemCount aumenta
            var previousCount by remember { mutableStateOf(cartItemCount) }
            var shouldAnimate by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(
                targetValue = if (shouldAnimate) 1.05f else 1.0f,
                animationSpec = tween(250),
                label = "cart_scale"
            )
            
            // Detecta aumento no cartItemCount e anima
            LaunchedEffect(cartItemCount) {
                if (cartItemCount > previousCount && cartItemCount > 0) {
                    shouldAnimate = true
                    kotlinx.coroutines.delay(250)
                    shouldAnimate = false
                }
                previousCount = cartItemCount
            }
            
            val cartIconColor by animateColorAsState(
                targetValue = if (isCartPressed) {
                    SpeedMenuColors.PrimaryLight
                } else {
                    SpeedMenuColors.PrimaryLight.copy(alpha = 0.8f)
                },
                animationSpec = tween(150),
                label = "cart_icon_color"
            )
            
            val cartTextColor by animateColorAsState(
                targetValue = if (isCartPressed) {
                    SpeedMenuColors.TextPrimary
                } else {
                    SpeedMenuColors.TextSecondary
                },
                animationSpec = tween(150),
                label = "cart_text_color"
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .scale(scale)
                    .clickable(
                        interactionSource = cartInteractionSource,
                        indication = null,
                        onClick = onCartClick
                    )
            ) {
                // Container para ícone com badge (garante espaço fixo)
                Box(
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Ver pedido",
                        tint = cartIconColor,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    // Badge discreto quando há itens no carrinho
                    if (cartItemCount > 0) {
                        // Badge circular pequeno no canto superior direito do ícone
                        // Mostra apenas indicador visual (ponto) para manter discreto
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 4.dp, y = (-2).dp)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    color = SpeedMenuColors.PrimaryLight
                                )
                        )
                    }
                }
                Text(
                    text = "Pedido",
                    style = MaterialTheme.typography.bodySmall,
                    color = cartTextColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

