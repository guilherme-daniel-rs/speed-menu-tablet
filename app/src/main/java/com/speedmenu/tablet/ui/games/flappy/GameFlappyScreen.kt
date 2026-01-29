package com.speedmenu.tablet.ui.games.flappy

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.zIndex
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
import com.speedmenu.tablet.ui.games.flappy.FlappyViewModel
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel
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
    // WaiterViewModel centralizado para gerenciar chamadas de garçom
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    // Estados mockados (mesmo padrão da ProductsScreen)
    val isConnected = remember { true }
    val tableNumber = remember { "17" }
    
    val colorScheme = MaterialTheme.colorScheme

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
                    val clampedDt = deltaTime.coerceIn(0f, 0.033f) // Limita dt a 33ms (30 FPS mínimo)
                    engine.update(clampedDt)
                    
                    // Incrementa tick a cada frame para forçar recomposição do Canvas
                    gameStateTick++
                    
                    // Log a cada 15 frames (throttle) com informações de física
                    if (DEBUG && frameCount % 15 == 0) {
                        Log.d("GameFlappyScreen", "Frame $frameCount - dt=${clampedDt}s, status=${currentState.status}, y=${currentState.playerY}, velY=${currentState.playerVelocity}, score=${currentState.score}")
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
            .background(colorScheme.background)
    ) {
        // AppTopBar padronizado no topo
        AppTopBar(
            showBackButton = true,
            onBackClick = onNavigateBack,
            isConnected = isConnected,
            tableNumber = tableNumber,
            onCallWaiterClick = {
                waiterViewModel.requestWaiter("GameFlappyScreen")
            },
            screenName = "GameFlappyScreen"
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
            // Renderer do jogo (Canvas por baixo)
            if (engine != null) {
                FlappyRenderer(
                    state = gameState,
                    screenWidth = screenSize.width,
                    screenHeight = screenSize.height,
                    onTap = {}, // Não usado, handler está no Box acima
                    modifier = Modifier.fillMaxSize()
                )
            }

            // HUD - Score no topo (não bloqueia toques - está abaixo do Box de toque)
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "${gameState.score}",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    fontSize = 64.sp
                )
            }
            
            // CORREÇÃO CRÍTICA: Box transparente com clickable POR CIMA de tudo (exceto dialogs)
            // Isso garante que capture TODOS os toques, mesmo quando o jogo está rodando
            // IMPORTANTE: Só captura toques quando o jogo NÃO está em GAME_OVER (para não interferir no dialog)
            val interactionSource = remember { MutableInteractionSource() }
            if (gameState.status != GameStatus.GAME_OVER) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f) // Garante que está acima de outros elementos
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null, // Sem ripple para não interferir
                            onClick = {
                                if (DEBUG) Log.d("GameFlappyScreen", "Tap received - current status=${gameState.status}, y=${gameState.playerY}, velY=${gameState.playerVelocity}")
                                engine?.onTap()
                                // Força atualização imediata do estado
                                gameStateTick++
                            }
                        )
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
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 24.sp
                    )
                }
            }

            // Dialog de Game Over (com zIndex alto para ficar acima do Box transparente)
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
                    },
                    modifier = Modifier.zIndex(10f) // Garante que está acima do Box transparente
                )
            }
        }

        // Dialog de garçom chamado
        // Dialog de garçom chamado (gerenciado pelo WaiterViewModel)
        WaiterCalledDialog(
            visible = waiterUiState.showDialog,
            onDismiss = { waiterViewModel.dismissDialog() },
            onConfirm = { waiterViewModel.confirmWaiterCall() }
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
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(onClick = {}), // Previne fechar ao clicar fora
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .background(
                    color = colorScheme.surfaceVariant,
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
                    color = colorScheme.onSurface,
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
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary,
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
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$bestScore",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.onSurface,
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
                                color = colorScheme.surface,
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
                            color = colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }

                    // Botão "Jogar novamente"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .background(
                                color = colorScheme.primary,
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
                            color = colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

