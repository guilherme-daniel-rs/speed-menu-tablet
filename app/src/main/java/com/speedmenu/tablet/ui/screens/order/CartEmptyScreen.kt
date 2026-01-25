package com.speedmenu.tablet.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.components.PrimaryCTA
import com.speedmenu.tablet.core.ui.components.TopActionBar
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Tela de pedido vazio.
 * Exibida quando o carrinho não possui itens.
 */
@Composable
fun CartEmptyScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToMenu: () -> Unit = {},
    cartItemCount: Int = 0
) {
    // Estados mockados
    val isConnected = true
    val tableNumber = "17"
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // Top Action Bar
        TopActionBar(
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {},
            onCartClick = {},
            cartItemCount = cartItemCount
        )
        
        // Conteúdo centralizado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ícone do carrinho vazio - extremamente discreto
                Box(
                    modifier = Modifier
                        .background(
                            color = SpeedMenuColors.Surface.copy(alpha = 0.08f), // Mais sutil
                            shape = RoundedCornerShape(20.dp) // Mais suave
                        )
                        .padding(28.dp), // Ligeiramente menor
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrinho vazio",
                        tint = SpeedMenuColors.TextTertiary.copy(alpha = 0.25f), // Ainda mais discreto
                        modifier = Modifier.size(72.dp) // Ligeiramente menor
                    )
                }
                
                // Mensagem principal - elegante e discreta
                Text(
                    text = "Seu pedido está vazio",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium, // Mais leve que SemiBold
                    color = SpeedMenuColors.TextPrimary.copy(alpha = 0.9f), // Ligeiramente mais suave
                    fontSize = 26.sp, // Ligeiramente menor
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp // Espaçamento refinado
                )
                
                // Mensagem secundária - sugestiva, não imperativa
                Text(
                    text = "Adicione itens para começar",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light, // Mais leve
                    color = SpeedMenuColors.TextSecondary.copy(alpha = 0.7f), // Mais discreto
                    fontSize = 15.sp, // Ligeiramente menor
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.3.sp
                )
                
                // CTA opcional: Ver cardápio
                PrimaryCTA(
                    text = "Ver cardápio",
                    price = 0.0, // Preço oculto quando 0.0
                    onClick = onNavigateToMenu,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

