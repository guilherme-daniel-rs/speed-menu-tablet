package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.core.utils.CurrencyFormatter

/**
 * Header com nome do prato, categoria e preço destacado.
 */
@Composable
fun PriceHeader(
    name: String,
    category: String,
    price: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Nome do prato (grande)
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = SpeedMenuColors.TextPrimary,
            fontSize = 32.sp,
            lineHeight = 38.sp
        )
        
        // Linha com categoria e preço
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Categoria (pequena)
            Text(
                text = category,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = SpeedMenuColors.TextTertiary,
                fontSize = 14.sp
            )
            
            // Preço destacado (alinhado à direita, sem exagero)
            Text(
                text = CurrencyFormatter.formatCurrencyBR(price),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = SpeedMenuColors.PrimaryLight,
                fontSize = 24.sp
            )
        }
    }
}

