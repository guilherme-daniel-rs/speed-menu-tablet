package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.domain.model.FulfillmentType

/**
 * Toggle compacto para alternar entre "No restaurante" e "Para viagem".
 * Usa cores 100% derivadas do tema da API.
 */
@Composable
fun FulfillmentToggle(
    fulfillmentType: FulfillmentType,
    onValueChange: (FulfillmentType) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    // Interação para detectar quando está pressionado
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animação de scale ao pressionar (feedback tátil elegante)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "toggle_scale"
    )
    
    // Animações de cor suaves
    // Invertido: "No local" (DINE_IN) fica colorido, "Para viagem" (TAKEAWAY) fica neutro
    val backgroundColor by animateColorAsState(
        targetValue = if (fulfillmentType == FulfillmentType.DINE_IN) {
            colorScheme.primary
        } else {
            colorScheme.surface
        },
        animationSpec = tween(200),
        label = "background_color"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (fulfillmentType == FulfillmentType.DINE_IN) {
            colorScheme.onPrimary
        } else {
            colorScheme.onSurfaceVariant
        },
        animationSpec = tween(200),
        label = "text_color"
    )
    
    val iconColor by animateColorAsState(
        targetValue = if (fulfillmentType == FulfillmentType.DINE_IN) {
            colorScheme.onPrimary
        } else {
            colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        },
        animationSpec = tween(200),
        label = "icon_color"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (fulfillmentType == FulfillmentType.DINE_IN) {
            colorScheme.primary
        } else {
            colorScheme.outline.copy(alpha = 0.3f)
        },
        animationSpec = tween(200),
        label = "border_color"
    )
    
    Box(
        modifier = modifier
            .scale(scale) // Aplica animação de scale
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Remove o ripple padrão para usar nossa animação customizada
                onClick = {
                    val newType = if (fulfillmentType == FulfillmentType.DINE_IN) {
                        FulfillmentType.TAKEAWAY
                    } else {
                        FulfillmentType.DINE_IN
                    }
                    onValueChange(newType)
                }
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (fulfillmentType == FulfillmentType.TAKEAWAY) {
                    Icons.Default.ShoppingBag
                } else {
                    Icons.Default.Restaurant
                },
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = if (fulfillmentType == FulfillmentType.TAKEAWAY) {
                    "Viagem"
                } else {
                    "No local"
                },
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = textColor,
                fontSize = 13.sp
            )
        }
    }
}
