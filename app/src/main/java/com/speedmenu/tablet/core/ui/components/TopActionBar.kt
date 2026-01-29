package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Barra de ação fixa no topo da tela.
 * Contém botão "Voltar" à esquerda e bloco de status (Conectado, Mesa, Garçom) à direita.
 * 
 * Características:
 * - Altura fixa: 72.dp
 * - Padding horizontal: 24.dp
 * - Alinhamento vertical central
 * - Background: SpeedMenuColors.Surface
 * - Não pode ser sobreposta pelo conteúdo
 */
@Composable
fun TopActionBar(
    onBackClick: () -> Unit,
    isConnected: Boolean = true,
    tableNumber: String = "17",
    onCallWaiterClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .zIndex(10f) // Garante que está acima de outros elementos
            .background(colorScheme.surface)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botão "Voltar" à esquerda
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.clickable(onClick = onBackClick)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Voltar",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 16.sp
                )
            }

            // Bloco de status à direita (OrderTopStatusPill)
            OrderTopStatusPill(
                isConnected = isConnected,
                tableNumber = tableNumber,
                onCallWaiterClick = onCallWaiterClick,
                enabled = enabled
            )
        }
    }
}

