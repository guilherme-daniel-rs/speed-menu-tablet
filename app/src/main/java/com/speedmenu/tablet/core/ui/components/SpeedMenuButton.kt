package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Botão primário do Design System.
 * CTA forte e convidativo, usado para ações principais.
 * Inclui animação sutil de pulso na sombra.
 */
@Composable
fun SpeedMenuPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    // Interação para hover/pressed
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animação de pulso na sombra (mais pronunciada para ponto focal)
    val infiniteTransition = rememberInfiniteTransition(label = "button_pulse")
    val shadowIntensity = infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shadow_intensity"
    ).value

    // Animação suave de scale ao pressionar (leve compressão tátil)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )
    
    // Animação suave de brilho ao pressionar (mais pronunciada)
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.22f else 0.16f,
        animationSpec = tween(150),
        label = "overlay_alpha"
    )

    // Cantos mais refinados e orgânicos para ponto focal
    val buttonShape = RoundedCornerShape(20.dp)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp) // Altura aumentada para maior presença
            .scale(scale) // Aplicar scale suave
            // ========== SOMBRAS MÚLTIPLAS PARA PROFUNDIDADE TÁTIL ==========
            // Sombra externa principal (mais pronunciada para ponto focal)
            .shadow(
                elevation = 24.dp,
                shape = buttonShape,
                spotColor = SpeedMenuColors.Primary.copy(alpha = shadowIntensity * 0.8f),
                ambientColor = SpeedMenuColors.Primary.copy(alpha = shadowIntensity * 0.4f)
            )
            // Sombra intermediária (profundidade)
            .shadow(
                elevation = 16.dp,
                shape = buttonShape,
                spotColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.6f),
                ambientColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.3f)
            )
            // Sombra próxima (contorno sutil)
            .shadow(
                elevation = 8.dp,
                shape = buttonShape,
                spotColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.4f),
                ambientColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.2f)
            )
            .clip(buttonShape)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                // Gradiente orgânico refinado com mais pontos para profundidade
                brush = if (enabled) {
                    Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.PrimaryLight.copy(red = 0.98f, green = 0.65f, blue = 0.05f), // Topo: brilho intenso
                            SpeedMenuColors.PrimaryLight, // 25%: laranja claro
                            SpeedMenuColors.Primary,      // 50%: cor principal
                            SpeedMenuColors.Primary.copy(red = 0.82f, green = 0.45f, blue = 0.03f), // 75%: transição
                            SpeedMenuColors.PrimaryDark   // Base: mais escuro (profundidade máxima)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.Disabled,
                            SpeedMenuColors.Disabled.copy(alpha = 0.8f)
                        )
                    )
                },
                shape = buttonShape
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        // ========== EFEITOS DE LUZ E PROFUNDIDADE ==========
        // Overlay de brilho no topo (mais pronunciado para ponto focal)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = overlayAlpha), // Brilho animado mais intenso
                            Color.White.copy(alpha = overlayAlpha * 0.5f), // Transição suave
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        )
        
        // Overlay de profundidade na base (sombra interna sutil)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.15f) // Sombra interna sutil
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
        )
        
        // Conteúdo do botão
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 2.dp) // Leve ajuste para compensar o brilho interno
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = SpeedMenuColors.TextOnPrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 14.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SpeedMenuColors.TextOnPrimary,
                fontSize = 18.sp
            )
        }
    }
}

/**
 * Botão secundário do Design System.
 * Visual discreto, usado para ações secundárias.
 */
@Composable
fun SpeedMenuSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                color = SpeedMenuColors.Surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = if (enabled) SpeedMenuColors.TextSecondary else SpeedMenuColors.Disabled,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 12.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = if (enabled) SpeedMenuColors.TextSecondary else SpeedMenuColors.Disabled,
                fontSize = 15.sp
            )
        }
    }
}

