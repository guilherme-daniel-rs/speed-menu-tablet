package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Dialog premium para confirmar item adicionado ao carrinho.
 * Design idêntico ao WaiterCalledDialog com dois botões.
 */
@Composable
fun ItemAddedDialog(
    visible: Boolean,
    productName: String? = null,
    onDismiss: () -> Unit,
    onFinishOrder: () -> Unit,
    onContinueShopping: () -> Unit
) {
    if (!visible) return

    // Animações de entrada
    var showContent by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0.98f,
        animationSpec = tween(200, easing = LinearOutSlowInEasing),
        label = "dialog_scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(180, easing = LinearOutSlowInEasing),
        label = "dialog_alpha"
    )

    LaunchedEffect(visible) {
        if (visible) {
            showContent = true
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Scrim padronizado (overlay escuro)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
            )
            
            // Card do dialog (largura limitada, centralizado)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth(0.7f)
                    .heightIn(max = 420.dp)
                    .padding(horizontal = 24.dp)
                    .scale(scale)
                    .alpha(alpha)
            ) {
                // Background do card com borda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF11161C).copy(alpha = 0.95f),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .drawBehind {
                            // Borda branca sutil
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.12f),
                                style = Stroke(width = 1.dp.toPx()),
                                cornerRadius = CornerRadius(28.dp.toPx(), 28.dp.toPx())
                            )
                        }
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(28.dp),
                            spotColor = Color.Black.copy(alpha = 0.35f),
                            ambientColor = Color.Black.copy(alpha = 0.18f)
                        )
                )
                
                // Conteúdo sobreposto
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ========== TOPO: Ícone de sucesso ==========
                    Box(
                        modifier = Modifier.size(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Glow suave
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Success.copy(alpha = 0.3f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        )
                        
                        // Círculo verde principal
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    color = SpeedMenuColors.Success,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextPrimary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ========== MEIO: Título e subtítulo ==========
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Título
                        Text(
                            text = "Item adicionado!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.Center
                        )

                        // Subtítulo opcional com nome do prato
                        if (!productName.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = productName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = SpeedMenuColors.TextSecondary.copy(alpha = 0.75f),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )
                        }
                    }

                    // Spacer para empurrar botões para o rodapé
                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(28.dp))

                    // ========== RODAPÉ: Dois botões ==========
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botão primário: "Finalizar pedido"
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .shadow(
                                    elevation = 6.dp,
                                    shape = RoundedCornerShape(999.dp),
                                    spotColor = Color.Black.copy(alpha = 0.25f),
                                    ambientColor = Color.Black.copy(alpha = 0.12f)
                                )
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Primary,
                                            Color(0xFFC76A05)
                                        )
                                    ),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = onFinishOrder),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Finalizar pedido",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }

                        // Botão secundário: "Continuar comprando"
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    color = SpeedMenuColors.Surface.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .clickable(onClick = onContinueShopping),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Continuar comprando",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = SpeedMenuColors.TextPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

