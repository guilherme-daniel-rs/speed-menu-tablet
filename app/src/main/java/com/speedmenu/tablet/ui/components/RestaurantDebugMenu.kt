package com.speedmenu.tablet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.data.mock.MockAppConfigSource
import com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel

/**
 * Menu de debug para alternar entre restaurantes mockados.
 * Acessível via clique longo no nome do restaurante na TopBar.
 */
@Composable
fun RestaurantDebugMenu(
    visible: Boolean,
    onDismiss: () -> Unit,
    appConfigViewModel: AppConfigViewModel = hiltViewModel()
) {
    if (!visible) return
    
    val mockSource = MockAppConfigSource()
    val availableRestaurants = mockSource.getAvailableRestaurantIds()
    val currentRestaurantId by appConfigViewModel.restaurantId.collectAsState()
    
    Dialog(onDismissRequest = onDismiss) {
        val colorScheme = MaterialTheme.colorScheme
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Selecionar Restaurante",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Restaurante atual: $currentRestaurantId",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Lista de restaurantes
                availableRestaurants.forEach { restaurantId ->
                    RestaurantDebugMenuItem(
                        restaurantId = restaurantId,
                        isSelected = restaurantId == currentRestaurantId,
                        onClick = {
                            appConfigViewModel.setRestaurantId(restaurantId)
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    if (restaurantId != availableRestaurants.last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantDebugMenuItem(
    restaurantId: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val restaurantName = when (restaurantId) {
        "steakhouse" -> "Leo Steakhouse"
        "italian" -> "Bella Pasta"
        "burger" -> "Urban Burger"
        "japanese" -> "Sushi Zen"
        "bar" -> "Moon Bar"
        else -> restaurantId
    }
    
    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = if (isSelected) {
                    colorScheme.primary.copy(alpha = 0.2f)
                } else {
                    colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = restaurantName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = colorScheme.onSurface
                )
                Text(
                    text = restaurantId,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

