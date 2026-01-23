package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Stepper compacto de quantidade em uma única linha (−  1  +).
 */
@Composable
fun QuantityStepper(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botão menos (neutro, cinza/low contrast)
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = SpeedMenuColors.Surface.copy(alpha = 0.3f), // Neutro
                    shape = CircleShape
                )
                .clickable(enabled = quantity > 1) {
                    onQuantityChange(quantity - 1)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Diminuir",
                tint = if (quantity > 1) {
                    SpeedMenuColors.TextSecondary.copy(alpha = 0.7f) // Neutro, low contrast
                } else {
                    SpeedMenuColors.TextTertiary.copy(alpha = 0.4f) // Desabilitado
                },
                modifier = Modifier.size(20.dp)
            )
        }
        
        // Quantidade
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = SpeedMenuColors.TextPrimary,
            fontSize = 20.sp
        )
        
        // Botão mais (neutro, cinza/low contrast)
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = SpeedMenuColors.Surface.copy(alpha = 0.3f), // Neutro, mesmo do menos
                    shape = CircleShape
                )
                .clickable {
                    onQuantityChange(quantity + 1)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar",
                tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f), // Neutro, low contrast
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

