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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Item de ingrediente com controles de quantidade.
 * 
 * @param name Nome do ingrediente
 * @param quantity Quantidade atual
 * @param isBase Se true, é ingrediente base (requer confirmação ao remover de 1 para 0)
 * @param maxQuantity Quantidade máxima permitida
 * @param onQuantityChange Callback quando a quantidade muda
 * @param onRemoveBaseIngredient Callback quando tenta remover ingrediente base (1 -> 0). Se null, remove direto.
 * @param modifier Modifier
 */
@Composable
fun IngredientQuantityItem(
    name: String,
    quantity: Int,
    isBase: Boolean = false,
    maxQuantity: Int = 5,
    onQuantityChange: (Int) -> Unit,
    onRemoveBaseIngredient: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nome do ingrediente (esquerda, 1 linha, ellipsis)
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = SpeedMenuColors.TextSecondary,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Controles de quantidade (direita): [ - ] [quantidade] [ + ]
        // Largura fixa para alinhamento consistente
        Row(
            modifier = Modifier.width(120.dp), // Largura fixa para não "dançar"
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento fixo entre elementos
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botão menos
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = SpeedMenuColors.Surface.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .clickable(enabled = quantity > 0) {
                        // Se for ingrediente base e estiver em 1, dispara confirmação
                        if (isBase && quantity == 1 && onRemoveBaseIngredient != null) {
                            onRemoveBaseIngredient()
                        } else {
                            // Caso contrário, remove direto (pode ir até 0)
                            onQuantityChange(quantity - 1)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Diminuir",
                    tint = if (quantity > 0) {
                        SpeedMenuColors.TextSecondary.copy(alpha = 0.7f)
                    } else {
                        SpeedMenuColors.TextTertiary.copy(alpha = 0.4f)
                    },
                    modifier = Modifier.size(16.dp)
                )
            }
            
            // Quantidade
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 15.sp,
                modifier = Modifier.width(24.dp),
                textAlign = TextAlign.Center
            )
            
            // Botão mais
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = SpeedMenuColors.Surface.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .clickable(enabled = quantity < maxQuantity) {
                        onQuantityChange((quantity + 1).coerceAtMost(maxQuantity))
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aumentar",
                    tint = if (quantity < maxQuantity) {
                        SpeedMenuColors.TextSecondary.copy(alpha = 0.7f)
                    } else {
                        SpeedMenuColors.TextTertiary.copy(alpha = 0.4f)
                    },
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

