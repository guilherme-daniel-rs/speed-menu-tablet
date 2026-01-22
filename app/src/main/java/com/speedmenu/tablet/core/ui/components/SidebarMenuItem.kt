package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Constantes de alinhamento horizontal para o menu lateral.
 * Garantem que todos os itens usem a mesma régua horizontal.
 */
private object SidebarMenuAlignment {
    /** PaddingStart do conteúdo (ícone + texto) - mesma régua para todos os itens */
    val ContentStartPadding = 44.dp // 28.dp (Column) + 16.dp (Row interno)
    
    /** Espaçamento entre ícone e texto - consistente em todos os itens */
    val IconTextSpacing = 18.dp
}

/**
 * Estilo do item de menu lateral.
 */
enum class SidebarMenuItemStyle {
    /** Botão primário (CTA principal) - destaque visual forte */
    PRIMARY,
    /** Item secundário (navegação) - estilo editorial discreto */
    SECONDARY
}

/**
 * Componente base unificado para itens do menu lateral.
 * Garante alinhamento, espaçamento e comportamento consistentes.
 * 
 * @param text Texto do item
 * @param icon Ícone do item (obrigatório para SECONDARY, opcional para PRIMARY)
 * @param onClick Ação ao clicar
 * @param style Estilo do item (PRIMARY ou SECONDARY)
 * @param modifier Modifier customizado
 * @param enabled Se o item está habilitado (apenas para PRIMARY)
 * @param isActive Se o item está ativo (apenas para SECONDARY)
 */
@Composable
fun SidebarMenuItem(
    text: String,
    icon: ImageVector?,
    onClick: () -> Unit,
    style: SidebarMenuItemStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isActive: Boolean = false
) {
    when (style) {
        SidebarMenuItemStyle.PRIMARY -> {
            PrimaryMenuItem(
                text = text,
                icon = icon,
                onClick = onClick,
                modifier = modifier,
                enabled = enabled
            )
        }
        SidebarMenuItemStyle.SECONDARY -> {
            SecondaryMenuItem(
                text = text,
                icon = icon ?: throw IllegalArgumentException("Icon is required for SECONDARY style"),
                onClick = onClick,
                modifier = modifier,
                isActive = isActive
            )
        }
    }
}

/**
 * Item primário do menu lateral (CTA principal).
 * Visual forte com sombras, gradientes e animações.
 */
@Composable
private fun PrimaryMenuItem(
    text: String,
    icon: ImageVector?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animação de pulso na sombra
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

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )
    
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.22f else 0.16f,
        animationSpec = tween(150),
        label = "overlay_alpha"
    )

    // Faixa contínua: fundo ocupa 100% da largura, sem cantos arredondados nas bordas laterais
    val fullWidthShape = RoundedCornerShape(0.dp) // Sem cantos arredondados para faixa contínua
    
    Box(
        modifier = modifier
            .fillMaxWidth() // Ocupa 100% da largura disponível
            .height(96.dp)
            .scale(scale)
            // Sombras ajustadas para faixa contínua
            .shadow(
                elevation = 24.dp,
                shape = fullWidthShape,
                spotColor = SpeedMenuColors.Primary.copy(alpha = shadowIntensity * 0.8f),
                ambientColor = SpeedMenuColors.Primary.copy(alpha = shadowIntensity * 0.4f)
            )
            .shadow(
                elevation = 16.dp,
                shape = fullWidthShape,
                spotColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.6f),
                ambientColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.3f)
            )
            .shadow(
                elevation = 8.dp,
                shape = fullWidthShape,
                spotColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.4f),
                ambientColor = SpeedMenuColors.PrimaryDark.copy(alpha = shadowIntensity * 0.2f)
            )
            .clip(fullWidthShape)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                brush = if (enabled) {
                    Brush.verticalGradient(
                        colors = listOf(
                            SpeedMenuColors.PrimaryLight.copy(red = 0.98f, green = 0.65f, blue = 0.05f),
                            SpeedMenuColors.PrimaryLight,
                            SpeedMenuColors.Primary,
                            SpeedMenuColors.Primary.copy(red = 0.82f, green = 0.45f, blue = 0.03f),
                            SpeedMenuColors.PrimaryDark
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
                shape = fullWidthShape
            ),
        contentAlignment = Alignment.CenterStart // Alinhamento à esquerda para seguir a régua do menu
    ) {
        // Overlay de brilho no topo (sem cantos arredondados para faixa contínua)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = overlayAlpha),
                            Color.White.copy(alpha = overlayAlpha * 0.5f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(0.dp) // Sem cantos arredondados
                )
        )
        
        // Overlay de profundidade na base (sem cantos arredondados para faixa contínua)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.15f)
                        )
                    ),
                    shape = RoundedCornerShape(0.dp) // Sem cantos arredondados
                )
        )
        
        // Conteúdo alinhado à esquerda seguindo a mesma régua horizontal dos itens secundários
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SidebarMenuAlignment.ContentStartPadding, end = 28.dp) // Usa constante única de alinhamento
                .padding(vertical = 2.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = SpeedMenuColors.TextOnPrimary,
                    modifier = Modifier
                        .size(24.dp) // Tamanho normalizado - igual aos ícones dos itens secundários para consistência visual
                )
            }
            
            // Espaçamento entre ícone e texto usando constante única
            Spacer(modifier = Modifier.width(SidebarMenuAlignment.IconTextSpacing))
            
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SpeedMenuColors.TextOnPrimary,
                fontSize = 19.sp // Aumentado discretamente de 18.sp para melhor legibilidade em tablet
            )
        }
    }
}

/**
 * Item secundário do menu lateral (navegação).
 * Estilo editorial discreto e premium.
 */
@Composable
private fun SecondaryMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val targetItemColor = when {
        isActive -> SpeedMenuColors.PrimaryLight
        isPressed -> SpeedMenuColors.TextPrimary
        else -> SpeedMenuColors.TextSecondary // Cor mais clara para melhor legibilidade
    }
    
    val targetIconColor = when {
        isActive -> SpeedMenuColors.PrimaryLight
        isPressed -> SpeedMenuColors.TextPrimary
        else -> SpeedMenuColors.TextSecondary.copy(alpha = 0.9f) // TextSecondary com leve opacidade - elegante e legível
    }
    
    val targetBackgroundColor = when {
        isActive -> SpeedMenuColors.PrimaryContainer.copy(alpha = 0.15f)
        isPressed -> SpeedMenuColors.Hover.copy(alpha = 0.3f)
        else -> Color.Transparent
    }
    
    val itemColor by animateColorAsState(
        targetValue = targetItemColor,
        animationSpec = tween(250),
        label = "item_color"
    )
    
    val iconColor by animateColorAsState(
        targetValue = targetIconColor,
        animationSpec = tween(250),
        label = "icon_color"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(250),
        label = "background_color"
    )
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(150),
        label = "item_scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(150),
        label = "item_alpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(scale)
            .alpha(alpha)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Ícone com alinhamento vertical explícito
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
        
        // Espaçamento entre ícone e texto usando constante única
        Spacer(modifier = Modifier.width(SidebarMenuAlignment.IconTextSpacing))
        
        // Texto com paddingStart adicional para melhor alinhamento e legibilidade
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = when {
                isActive -> FontWeight.SemiBold
                isPressed -> FontWeight.Medium
                else -> FontWeight.Normal
            },
            color = itemColor,
            fontSize = 16.sp,
            letterSpacing = 0.2.sp,
            lineHeight = 22.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 2.dp) // PaddingStart adicional para melhor alinhamento premium
        )
    }
}

