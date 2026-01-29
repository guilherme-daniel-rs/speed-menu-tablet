package com.speedmenu.tablet.ui.games.flappy

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Renderer do jogo Flappy - desenha todos os elementos no Canvas.
 */
@Composable
fun FlappyRenderer(
    state: FlappyGameState,
    screenWidth: Float,
    screenHeight: Float,
    onTap: () -> Unit, // Mantido para compatibilidade, mas não usado aqui
    modifier: Modifier = Modifier
) {
    // Usa o estado como dependência para forçar recomposição do Canvas
    val playerY = state.playerY
    val obstacles = state.obstacles
    
    // Canvas simples - handler de toque será gerenciado pelo GameFlappyScreen
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        // Fundo
        drawRect(
            color = SpeedMenuColors.BackgroundPrimary,
            size = Size(size.width, size.height)
        )

        // Desenha obstáculos
        for (obstacle in obstacles) {
            drawObstacle(obstacle, size.width, size.height)
        }

        // Desenha player
        drawPlayer(playerY, size.width)
    }
}

/**
 * Desenha um obstáculo (canos superior e inferior).
 */
private fun DrawScope.drawObstacle(
    obstacle: Obstacle,
    screenWidth: Float,
    screenHeight: Float
) {
    val obstacleWidth = FlappyGameState.OBSTACLE_WIDTH
    val x = obstacle.x

    // Cano superior
    drawRect(
        color = SpeedMenuColors.Primary,
        topLeft = Offset(x, 0f),
        size = Size(obstacleWidth, obstacle.topHeight)
    )

    // Cano inferior
    val bottomObstacleY = obstacle.gapY + obstacle.gapHeight
    val bottomObstacleHeight = screenHeight - bottomObstacleY
    drawRect(
        color = SpeedMenuColors.Primary,
        topLeft = Offset(x, bottomObstacleY),
        size = Size(obstacleWidth, bottomObstacleHeight)
    )

    // Detalhes decorativos nos canos (opcional, para ficar mais bonito)
    val detailColor = SpeedMenuColors.Primary.copy(alpha = 0.7f)
    
    // Detalhe superior
    drawRect(
        color = detailColor,
        topLeft = Offset(x + obstacleWidth * 0.2f, obstacle.topHeight - 20f),
        size = Size(obstacleWidth * 0.6f, 20f)
    )
    
    // Detalhe inferior
    drawRect(
        color = detailColor,
        topLeft = Offset(x + obstacleWidth * 0.2f, bottomObstacleY),
        size = Size(obstacleWidth * 0.6f, 20f)
    )
}

/**
 * Desenha o player (círculo simples).
 */
private fun DrawScope.drawPlayer(
    playerY: Float,
    screenWidth: Float
) {
    val playerRadius = FlappyGameState.PLAYER_SIZE / 2f
    val playerCenterX = screenWidth / 2f - playerRadius
    val playerCenter = Offset(playerCenterX, playerY)

    // Círculo principal
    drawCircle(
        color = SpeedMenuColors.PrimaryLight,
        radius = playerRadius,
        center = playerCenter
    )

    // Olho (detalhe decorativo)
    drawCircle(
        color = Color.White,
        radius = playerRadius * 0.3f,
        center = Offset(
            playerCenter.x + playerRadius * 0.3f,
            playerCenter.y - playerRadius * 0.2f
        )
    )

    // Pupila
    drawCircle(
        color = Color.Black,
        radius = playerRadius * 0.15f,
        center = Offset(
            playerCenter.x + playerRadius * 0.35f,
            playerCenter.y - playerRadius * 0.25f
        )
    )
}

