package com.speedmenu.tablet.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Header da tela de produtos.
 * Contém título, subtítulo e botão de carrinho (CTA oficial e único de acesso ao carrinho).
 * Botão "Voltar para categorias" removido - navegação apenas via sidebar.
 */
@Composable
fun ProductsHeader(
    categoryName: String,
    productCount: Int,
    cartItemCount: Int = 0,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Linha horizontal: Título à esquerda, botão Carrinho à direita (mesma altura visual)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Coluna esquerda: Título e subtítulo
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 36.sp,
                lineHeight = 42.sp
            )
            
            Text(
                text = "$productCount opções disponíveis",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = SpeedMenuColors.TextTertiary,
                fontSize = 15.sp
            )
        }
        
        // Botão Carrinho à direita (alinhado com o título)
        // Visível apenas quando há itens no carrinho (cartItemCount > 0)
        if (cartItemCount > 0) {
            Box(
                modifier = Modifier
                    .background(
                        color = SpeedMenuColors.SurfaceElevated.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(onClick = onCartClick)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrinho",
                        tint = SpeedMenuColors.TextSecondary,
                        modifier = Modifier.padding(0.dp)
                    )
                    Text(
                        text = "Carrinho ($cartItemCount)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
