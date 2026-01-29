package com.speedmenu.tablet.core.ui.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Componente reutilizável de status no topo direito para o fluxo de pedidos.
 * Exibe: Status de conexão, Número da mesa e botão para chamar garçom.
 * 
 * Características:
 * - 100% reutilizável e independente de layout específico
 * - Posicionamento consistente (topo direito com padding)
 * - Visual premium com fundo translúcido e cantos arredondados
 * - Acessível com áreas clicáveis confortáveis e contentDescription
 * - Micro-interações suaves no botão de garçom
 * 
 * @param isConnected Status da conexão (true = conectado, false = desconectado)
 * @param tableNumber Número da mesa (String ou Int, será formatado como "Mesa {number}")
 * @param onCallWaiterClick Callback quando o botão "Garçom" é clicado
 * @param enabled Se o botão de garçom está habilitado (padrão: true)
 * @param modifier Modifier para customização adicional
 */
@Composable
fun OrderTopStatusPill(
    isConnected: Boolean = true,
    tableNumber: String = "17", // Aceita String para flexibilidade
    onCallWaiterClick: () -> Unit = {},
    enabled: Boolean = true,
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
                // Ícone de WiFi com cor baseada no status
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isConnected) Icons.Default.Wifi else Icons.Default.WifiOff,
                        contentDescription = if (isConnected) "Conectado" else "Desconectado",
                        tint = if (isConnected) {
                            SpeedMenuColors.Success.copy(alpha = 0.85f)
                        } else {
                            SpeedMenuColors.Error.copy(alpha = 0.85f)
                        },
                        modifier = Modifier.size(18.dp)
                    )
                }
                // Indicador de status conectado/desconectado
                Text(
                    text = if (isConnected) "Conectado" else "Desconectado",
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
                    text = "Mesa $tableNumber",
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
                targetValue = when {
                    !enabled -> SpeedMenuColors.TextTertiary.copy(alpha = 0.4f)
                    isWaiterPressed -> SpeedMenuColors.PrimaryLight
                    else -> SpeedMenuColors.PrimaryLight.copy(alpha = 0.8f)
                },
                animationSpec = tween(150),
                label = "waiter_icon_color"
            )
            
            val waiterTextColor by animateColorAsState(
                targetValue = when {
                    !enabled -> SpeedMenuColors.TextTertiary.copy(alpha = 0.4f)
                    isWaiterPressed -> SpeedMenuColors.TextPrimary
                    else -> SpeedMenuColors.TextSecondary
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
                    enabled = enabled,
                    onClick = {
                        if (enabled) {
                            Log.d("OrderTopStatusPill", "🛎️ Botão Garçom clicado (enabled=$enabled)")
                            onCallWaiterClick()
                        } else {
                            Log.w("OrderTopStatusPill", "⚠️ Botão Garçom clicado mas está desabilitado")
                        }
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

