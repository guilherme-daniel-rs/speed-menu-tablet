package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Chip de filtro para a tela de Categorias.
 * Estilo arredondado, fundo escuro e texto claro.
 */
@Composable
fun FilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .background(
                color = if (isSelected) {
                    SpeedMenuColors.Primary.copy(alpha = 0.3f)
                } else {
                    SpeedMenuColors.SurfaceElevated.copy(alpha = 0.7f)
                },
                shape = RoundedCornerShape(20.dp) // Cantos bem arredondados
            )
            .clickable(onClick = onClick)
            .padding(PaddingValues(horizontal = 20.dp, vertical = 12.dp))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) {
                SpeedMenuColors.PrimaryLight
            } else {
                SpeedMenuColors.TextSecondary
            },
            fontSize = 14.sp
        )
    }
}

