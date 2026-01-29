package com.speedmenu.tablet.core.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.data.config.DefaultAppConfig
import com.speedmenu.tablet.ui.viewmodel.AppConfigViewModel

/**
 * Barra superior padronizada para todas as telas do app.
 * 
 * Estrutura com 3 zonas fixas:
 * - Left: Botão voltar ou placeholder invisível (para manter centralização)
 * - Center: Logo do restaurante (centralizada matematicamente)
 * - Right: Status (Conectado / Mesa / Garçom)
 * 
 * A logo é centralizada usando Box com Alignment.Center, garantindo que
 * fique exatamente no centro horizontal, independentemente do conteúdo lateral.
 * 
 * IMPORTANTE: onCallWaiterClick é OBRIGATÓRIO e nunca deve ser vazio.
 * Use o WaiterViewModel para centralizar a lógica de chamadas de garçom.
 * 
 * @param showBackButton Se deve exibir o botão voltar (false na Home)
 * @param onBackClick Callback quando o botão voltar é clicado
 * @param isConnected Status da conexão
 * @param tableNumber Número da mesa
 * @param onCallWaiterClick Callback quando o botão garçom é clicado (OBRIGATÓRIO)
 * @param screenName Nome da tela atual (para logs de debug)
 * @param onMenuClick Callback quando o botão menu (hamburger) é clicado (para abrir drawer no celular)
 * @param enabled Se o botão de garçom está habilitado
 * @param modifier Modifier para customização adicional
 */
@Composable
fun AppTopBar(
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    isConnected: Boolean = true,
    tableNumber: String = "17",
    onCallWaiterClick: () -> Unit, // OBRIGATÓRIO - não pode ser vazio
    screenName: String = "Unknown",
    onMenuClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    appConfigViewModel: AppConfigViewModel = hiltViewModel(),
    onRestaurantLongClick: (() -> Unit)? = null, // Callback para abrir debug menu
    modifier: Modifier = Modifier
) {
    // Observa AppConfig para obter o nome do restaurante
    val appConfig by appConfigViewModel.appConfig.collectAsState()
    val restaurantName = appConfig?.branding?.restaurantName ?: DefaultAppConfig.get().branding.restaurantName
    
    // Handler interno que adiciona logging antes de chamar o callback
    val waiterClickHandler: () -> Unit = {
        Log.d("TopBar", "🛎️ Garçom click - screen=$screenName")
        onCallWaiterClick()
    }
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .zIndex(10f) // Garante que está acima de outros elementos
            .background(colorScheme.surface)
            .padding(horizontal = 24.dp)
    ) {
        // ========== LOGO CENTRALIZADA (CENTRO ABSOLUTO) ==========
        // A logo é posicionada no centro absoluto da tela, independentemente do conteúdo lateral
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            RestaurantLogo(
                restaurantName = restaurantName,
                onLongClick = onRestaurantLongClick
            )
        }

        // ========== ZONA ESQUERDA: Botão voltar, menu ou placeholder invisível ==========
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            when {
                onMenuClick != null -> {
                    // Botão menu (hamburger) para abrir drawer no celular
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onMenuClick)
                    )
                }
                showBackButton -> {
                    // Botão voltar visível
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
                }
                else -> {
                    // Placeholder invisível com largura similar ao botão voltar
                    // Isso garante que a logo continue centralizada mesmo sem o botão
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.size(20.dp)) // Espaço do ícone
                        Text(
                            text = "Voltar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.onSurfaceVariant.copy(alpha = 0f), // Invisível
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // ========== ZONA DIREITA: Status pill ==========
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            OrderTopStatusPill(
                isConnected = isConnected,
                tableNumber = tableNumber,
                onCallWaiterClick = waiterClickHandler,
                enabled = enabled
            )
        }
    }
}

/**
 * Logo do restaurante para o Top Bar.
 * Versão compacta e monocromática para uso no menu superior.
 * Agora usa o nome do restaurante do AppConfig.
 * Clique longo abre o menu de debug para alternar restaurantes.
 */
@Composable
private fun RestaurantLogo(
    restaurantName: String,
    onLongClick: (() -> Unit)? = null
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Text(
        text = restaurantName,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold,
        color = colorScheme.onSurface,
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp,
        lineHeight = 24.sp,
        modifier = Modifier
            .then(
                if (onLongClick != null) {
                    Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { onLongClick() }
                        )
                    }
                } else {
                    Modifier
                }
            )
    )
}

