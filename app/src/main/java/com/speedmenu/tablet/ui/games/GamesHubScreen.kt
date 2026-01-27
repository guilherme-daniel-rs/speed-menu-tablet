package com.speedmenu.tablet.ui.games

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.R
import com.speedmenu.tablet.core.ui.components.SpeedMenuBadge
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Hub de jogos - tela principal que lista os jogos disponíveis.
 * Layout premium com cards bonitos e animações suaves.
 */
@Composable
fun GamesHubScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToFlappy: () -> Unit = {}
) {
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterDialog by remember { mutableStateOf(false) }
    
    // Estados mockados (mesmo padrão da ProductsScreen)
    val isConnected = remember { true }
    val tableNumber = remember { "17" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        // AppTopBar padronizado no topo
        AppTopBar(
            showBackButton = true,
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {
                showWaiterDialog = true
            }
        )

        // Conteúdo principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Jogos",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = SpeedMenuColors.TextPrimary,
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtítulo
            Text(
                text = "Jogue enquanto espera seu pedido",
                style = MaterialTheme.typography.bodyLarge,
                color = SpeedMenuColors.TextSecondary,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Grid de cards de jogos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Card 1: Flappy
                GameCard(
                    title = "Flappy",
                    badge = "Novo",
                    imageResId = R.drawable.flappy,
                    onClick = onNavigateToFlappy,
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Em breve (placeholder)
                GameCard(
                    title = "Em breve",
                    badge = null,
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )

                // Card 3: Em breve (placeholder)
                GameCard(
                    title = "Em breve",
                    badge = null,
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Dialog de garçom chamado
        WaiterCalledDialog(
            visible = showWaiterDialog,
            onDismiss = { showWaiterDialog = false },
            onConfirm = {
                showWaiterDialog = false
            }
        )
    }
}

/**
 * Card de jogo premium com animação de toque.
 */
@Composable
private fun GameCard(
    title: String,
    badge: String? = null,
    imageResId: Int? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = tween(150),
        label = "card_scale"
    )

    Box(
        modifier = modifier
            .height(280.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (enabled) {
                        SpeedMenuColors.SurfaceElevated
                    } else {
                        SpeedMenuColors.SurfaceElevated.copy(alpha = 0.5f)
                    },
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            // Imagem de fundo (se fornecida)
            if (imageResId != null && enabled) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Overlay escuro para melhorar legibilidade do texto
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = SpeedMenuColors.BackgroundPrimary.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(24.dp)
                        )
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge no topo
                if (badge != null) {
                    SpeedMenuBadge(
                        text = badge,
                        modifier = Modifier.align(Alignment.Start)
                    )
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Título no centro
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (enabled) {
                        SpeedMenuColors.TextPrimary
                    } else {
                        SpeedMenuColors.TextTertiary
                    },
                    fontSize = 28.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Botão de ação (ou placeholder)
                if (enabled) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = SpeedMenuColors.Primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Jogar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = SpeedMenuColors.Surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Em breve",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = SpeedMenuColors.TextTertiary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

