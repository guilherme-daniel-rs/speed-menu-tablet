package com.speedmenu.tablet.ui.games.flappy

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.games.flappy.FlappyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.isActive

// Flag de debug (remover em produção)
private const val DEBUG = true

/**
 * Tela principal do jogo Flappy.
 * Gerencia o game loop, HUD, game over e persistência de score.
 */
@Composable
fun GameFlappyScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: FlappyViewModel = hiltViewModel()
) {
    // Estado para controlar visibilidade do dialog de garçom
    var showWaiterDialog by remember { mutableStateOf(false) }
    
    // Estados mockados (mesmo padrão da ProductsScreen)
    val isConnected = remember { true }
    val tableNumber = remember { "17" }

    // Dimensões da tela
    var screenSize by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current

    // Engine do jogo (criado uma vez quando as dimensões estão disponíveis)
    val engine = remember(screenSize.width, screenSize.height) {
        if (screenSize.width > 0 && screenSize.height > 0) {
            if (DEBUG) Log.d("GameFlappyScreen", "Creating engine - width=${screenSize.width}, height=${screenSize.height}")
            FlappyEngine(
                screenWidth = screenSize.width,
                screenHeight = screenSize.height
            )
        } else {
            if (DEBUG) Log.d("GameFlappyScreen", "Engine not created - screenSize is zero")
            null
        }
    }

    // Estado observável do jogo (força recomposição quando muda)
    var gameStateTick by remember { mutableStateOf(0L) }
    
    // Lê o estado do engine sempre que gameStateTick muda (força recomposição)
    val gameState = remember(engine, gameStateTick) {
        val state = engine?.getState() ?: FlappyGameState()
        if (DEBUG && gameStateTick % 60L == 0L) { // Log a cada segundo (~60 frames)
            Log.d("GameFlappyScreen", "State read - tick=$gameStateTick, status=${state.status}, y=${state.playerY}, score=${state.score}")
        }
        state
    }
    
    // Coroutine scope para operações assíncronas
    val coroutineScope = rememberCoroutineScope()
    
    // Estado do best score
    val bestScore by viewModel.flappyBestScore.collectAsState(initial = 0)
    
    var currentBestScore by remember { mutableStateOf(0) }
    
    // Atualiza best score quando o flow mudar
    LaunchedEffect(bestScore) {
        currentBestScore = bestScore
    }

    // Game loop usando delay para ~60 FPS
    LaunchedEffect(engine) {
        if (engine != null) {
            if (DEBUG) Log.d("GameFlappyScreen", "Game loop started")
            var lastFrameTime = System.nanoTime()
            var frameCount = 0
            var lastStatus = GameStatus.IDLE
            
            while (isActive) {
                val currentTime = System.nanoTime()
                val deltaTime = (currentTime - lastFrameTime) / 1_000_000_000f // Converter para segundos
                lastFrameTime = currentTime
                
                val currentState = engine.getState()
                
                // Detecta mudança de status e força atualização
                if (currentState.status != lastStatus) {
                    if (DEBUG) Log.d("GameFlappyScreen", "Status changed: $lastStatus -> ${currentState.status}")
                    lastStatus = currentState.status
                    gameStateTick++ // Força recomposição quando status muda
                }
                
                if (currentState.status == GameStatus.RUNNING) {
                    engine.update(deltaTime.coerceIn(0f, 0.1f)) // Limita deltaTime para evitar saltos grandes
                    
                    // Incrementa tick a cada frame para forçar recomposição do Canvas
                    gameStateTick++
                    
                    // Log a cada 30 frames (throttle)
                    if (DEBUG && frameCount % 30 == 0) {
                        Log.d("GameFlappyScreen", "Frame $frameCount - dt=${deltaTime}s, status=${currentState.status}, y=${currentState.playerY}, score=${currentState.score}")
                    }
                }
                
                frameCount++
                delay(16) // ~60 FPS (1000ms / 60 frames ≈ 16ms por frame)
            }
        }
    }

    // Salva best score quando o jogo termina
    LaunchedEffect(gameState.status, gameState.score) {
        if (gameState.status == GameStatus.GAME_OVER && gameState.score > currentBestScore) {
            if (DEBUG) Log.d("GameFlappyScreen", "Saving best score: ${gameState.score}")
            coroutineScope.launch {
                viewModel.gamePreferences.saveFlappyBestScore(gameState.score)
            }
        }
    }

    // Estado do dialog de game over
    var showGameOverDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(gameState.status) {
        if (gameState.status == GameStatus.GAME_OVER) {
            delay(500) // Pequeno delay antes de mostrar o dialog
            showGameOverDialog = true
        }
    }

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

        // Área do jogo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    screenSize = with(density) {
                        Size(size.width.toFloat(), size.height.toFloat())
                    }
                }
        ) {
            // Renderer do jogo
            // Usa gameStateTick como key para forçar recomposição do Canvas quando o estado muda
            if (engine != null) {
                key(gameStateTick) {
                    FlappyRenderer(
                        state = gameState,
                        screenWidth = screenSize.width,
                        screenHeight = screenSize.height,
                        onTap = {
                            if (DEBUG) Log.d("GameFlappyScreen", "Tap received - current status=${gameState.status}")
                            engine.onTap()
                            // Força atualização imediata do estado
                            gameStateTick++
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // HUD - Score no topo
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "${gameState.score}",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 64.sp
                )
            }

            // Instrução inicial
            if (gameState.status == GameStatus.IDLE) {
                val alpha by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(1000),
                    label = "instruction_alpha"
                )
                
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(alpha)
                ) {
                    Text(
                        text = "Toque para começar",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 24.sp
                    )
                }
            }

            // Dialog de Game Over
            if (showGameOverDialog) {
                GameOverDialog(
                    score = gameState.score,
                    bestScore = maxOf(gameState.score, currentBestScore),
                    onPlayAgain = {
                        showGameOverDialog = false
                        engine?.reset()
                        // Força atualização do estado após reset
                        gameStateTick++
                    },
                    onBack = {
                        showGameOverDialog = false
                        onNavigateBack()
                    }
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
 * Dialog de Game Over premium.
 */
@Composable
private fun GameOverDialog(
    score: Int,
    bestScore: Int,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(onClick = {}), // Previne fechar ao clicar fora
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .background(
                    color = SpeedMenuColors.SurfaceElevated,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Título
                Text(
                    text = "Fim de jogo",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpeedMenuColors.TextPrimary,
                    fontSize = 32.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Score atual
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Pontuação",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = SpeedMenuColors.PrimaryLight,
                        fontSize = 48.sp
                    )
                }

                // Best score
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Recorde",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$bestScore",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botão "Voltar"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .background(
                                color = SpeedMenuColors.Surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(onClick = onBack)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Voltar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextSecondary,
                            fontSize = 16.sp
                        )
                    }

                    // Botão "Jogar novamente"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .background(
                                color = SpeedMenuColors.Primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(onClick = onPlayAgain)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Jogar novamente",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

