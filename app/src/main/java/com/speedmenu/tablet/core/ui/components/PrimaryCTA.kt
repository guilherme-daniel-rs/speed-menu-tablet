package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter

/**
 * CTA principal fixo no rodapé com preço.
 */
@Composable
fun PrimaryCTA(
    text: String,
    price: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Aumento de brilho ao pressionar (gradiente mais claro)
    // Se desabilitado, usa cores mais escuras e reduzidas
    val topColor by animateColorAsState(
        targetValue = if (!enabled) {
            SpeedMenuColors.Primary.copy(alpha = 0.5f)
        } else if (isPressed) {
            SpeedMenuColors.PrimaryLight.copy(alpha = 1f)
        } else {
            SpeedMenuColors.Primary
        },
        animationSpec = tween(150),
        label = "cta_top_color"
    )
    
    val bottomColor by animateColorAsState(
        targetValue = if (!enabled) {
            SpeedMenuColors.PrimaryDark.copy(alpha = 0.5f)
        } else if (isPressed) {
            SpeedMenuColors.Primary
        } else {
            SpeedMenuColors.PrimaryDark
        },
        animationSpec = tween(150),
        label = "cta_bottom_color"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(
                elevation = if (isPressed) 10.dp else 8.dp,
                shape = RoundedCornerShape(999.dp),
                spotColor = SpeedMenuColors.Primary.copy(alpha = if (isPressed) 0.4f else 0.3f),
                ambientColor = Color.Black.copy(alpha = if (isPressed) 0.18f else 0.15f)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        topColor,
                        bottomColor
                    )
                ),
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (price > 0.0) {
                "$text • ${CurrencyFormatter.formatCurrencyBR(price)}"
            } else {
                text
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = if (enabled) SpeedMenuColors.TextOnPrimary else SpeedMenuColors.TextOnPrimary.copy(alpha = 0.6f),
            fontSize = 18.sp
        )
    }
}

