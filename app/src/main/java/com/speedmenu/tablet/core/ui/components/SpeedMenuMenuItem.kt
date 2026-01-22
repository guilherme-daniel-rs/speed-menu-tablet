package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Item de menu lateral com estilo editorial premium.
 * Tratado como opção de navegação, não botão - sensação de menu refinado.
 */
@Composable
fun SpeedMenuMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animações suaves de cor e background (estilo editorial sutil)
    val targetItemColor = when {
        isActive -> SpeedMenuColors.PrimaryLight // Laranja claro quando ativo
        isPressed -> SpeedMenuColors.TextPrimary // Branco ao pressionar
        else -> SpeedMenuColors.TextTertiary // Cinza médio (estilo editorial discreto)
    }
    
    val targetIconColor = when {
        isActive -> SpeedMenuColors.PrimaryLight
        isPressed -> SpeedMenuColors.TextPrimary
        else -> SpeedMenuColors.TextTertiary.copy(alpha = 0.7f) // Ícone mais discreto
    }
    
    // Background extremamente sutil (quase imperceptível) - estilo editorial
    val targetBackgroundColor = when {
        isActive -> SpeedMenuColors.PrimaryContainer.copy(alpha = 0.15f) // Muito sutil quando ativo
        isPressed -> SpeedMenuColors.Hover.copy(alpha = 0.3f) // Leve ao pressionar
        else -> Color.Transparent // Sem background por padrão
    }
    
    // Transições suaves de cor (250ms para elegância editorial)
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
    
    // ========== MICRO-INTERAÇÕES SUTIS ==========
    // Scale sutil ao tocar (feedback tátil elegante)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(150),
        label = "item_scale"
    )
    
    // Opacidade sutil ao tocar (feedback visual discreto)
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(150),
        label = "item_alpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp) // Altura aumentada para respiração editorial
            .scale(scale) // Aplicar scale sutil
            .alpha(alpha) // Aplicar opacidade sutil
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp) // Cantos mais sutis
            )
            .padding(horizontal = 16.dp, vertical = 16.dp), // Padding generoso para estilo editorial
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Ícone discreto e harmonioso (menor que o CTA principal: 28dp → 20dp)
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(20.dp) // Menor que o botão principal (28dp), discreto e harmonioso
                .padding(end = 16.dp) // Espaçamento consistente entre ícone e texto
        )
        
        // Texto com tipografia editorial refinada
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = when {
                isActive -> FontWeight.SemiBold
                isPressed -> FontWeight.Medium
                else -> FontWeight.Normal
            },
            color = itemColor,
            fontSize = 15.sp, // Tamanho ligeiramente menor para elegância
            letterSpacing = 0.2.sp, // Letter spacing refinado
            lineHeight = 20.sp // Line height confortável
        )
    }
}

