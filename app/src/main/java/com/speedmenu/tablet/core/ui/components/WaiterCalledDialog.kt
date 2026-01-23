package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Dialog premium para confirmar chamada de garçom.
 * Design minimalista, sem scroll, com animações suaves.
 */
@Composable
fun WaiterCalledDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
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

    // Animação de pulse suave no ícone do sino
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val iconPulseAlpha = infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "icon_pulse_alpha"
    ).value

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
            // Scrim padronizado (overlay escuro - conteúdo quase imperceptível)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f)) // Alpha entre 0.82f e 0.88f
            )
            
            // Card do dialog (largura limitada, centralizado, 100% nítido)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth(0.7f) // Para tablets
                    .heightIn(max = 420.dp)
                    .padding(horizontal = 24.dp)
                    .scale(scale)
                    .alpha(alpha)
            ) {
                // Background do card com borda (sólido, alpha ≥ 0.92f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF11161C).copy(alpha = 0.95f), // Card sólido
                            shape = RoundedCornerShape(28.dp)
                        )
                        .drawBehind {
                            // Borda branca sutil (1dp, 12% alpha)
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.12f),
                                style = Stroke(width = 1.dp.toPx()),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(28.dp.toPx(), 28.dp.toPx())
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
                    // ========== TOPO: Ícone do sino ==========
                    Box(
                        modifier = Modifier.size(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Glow suave (círculo maior com opacidade baixa)
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Primary.copy(alpha = 0.3f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        )
                        
                        // Círculo laranja principal
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    color = SpeedMenuColors.Primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextPrimary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(32.dp)
                                    .alpha(iconPulseAlpha)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp)) // Espaçamento aumentado entre ícone e título

                    // ========== MEIO: Título, subtítulo, chip de status (bloco coeso centralizado) ==========
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Título grande (peso visual aumentado)
                        Text(
                            text = "Garçom chamado",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp)) // Espaçamento aumentado

                        // Subtítulo curto (tamanho reduzido, opacidade reduzida)
                        Text(
                            text = "Já avisamos a equipe. Em instantes alguém vem até sua mesa.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = SpeedMenuColors.TextSecondary.copy(alpha = 0.75f),
                            fontSize = 14.sp, // Reduzido de 15.sp para 14.sp
                            lineHeight = 20.sp, // Reduzido de 22.sp para 20.sp
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Chip de status (menor e mais discreto)
                        Row(
                            modifier = Modifier
                                .background(
                                    color = SpeedMenuColors.Success.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SpeedMenuColors.Success,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Solicitação enviada",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = SpeedMenuColors.Success,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Spacer para empurrar botão para o rodapé
                    Spacer(modifier = Modifier.weight(1f))

                    // Espaçamento entre chip e CTA (aumentado)
                    Spacer(modifier = Modifier.height(28.dp))

                    // ========== RODAPÉ: Botão principal único ==========
                    // Botão primário (CTA no rodapé com acabamento premium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(999.dp),
                                spotColor = Color.Black.copy(alpha = 0.25f), // Blur amplo, opacidade baixa
                                ambientColor = Color.Black.copy(alpha = 0.12f)
                            )
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        SpeedMenuColors.Primary, // Top: laranja principal
                                        Color(0xFFC76A05) // Bottom: laranja 8% mais escuro (0xFFD97706 * 0.92)
                                    )
                                ),
                                shape = RoundedCornerShape(999.dp)
                            )
                            .clickable(onClick = onConfirm),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ok, entendi",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White, // Branco puro para contraste máximo
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

