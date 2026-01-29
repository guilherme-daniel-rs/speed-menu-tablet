package com.speedmenu.tablet.ui.games.flappy

import android.util.Log
import kotlin.math.max
import kotlin.math.min

/**
 * Status do jogo Flappy.
 */
enum class GameStatus {
    IDLE,      // Aguardando primeiro tap
    RUNNING,   // Jogo em andamento
    GAME_OVER  // Fim de jogo
}

// Flag de debug (remover em produção)
private const val DEBUG = true

/**
 * Engine do jogo Flappy - gerencia estado e lógica do jogo.
 * Otimizado para performance, evitando alocações por frame.
 */
data class FlappyGameState(
    var playerY: Float = 0f,
    var playerVelocity: Float = 0f,
    var obstacles: MutableList<Obstacle> = mutableListOf(),
    var score: Int = 0,
    var status: GameStatus = GameStatus.IDLE,
    var lastObstacleX: Float = 0f,
    var gameStartTime: Long = 0L // Para grace period no início
) {
    companion object {
        const val GRAVITY = 0.5f // Aumentado para deixar mais difícil (cai mais rápido)
        const val JUMP_STRENGTH = -12f
        const val OBSTACLE_SPEED = 5.5f // Aumentado de 4f para 5.5f (obstáculos mais rápidos)
        const val OBSTACLE_SPACING = 400f
        const val OBSTACLE_GAP = 280f // Reduzido de 350f para 280f (mais difícil de passar)
        const val PLAYER_SIZE = 45f // Aumentado de 30f para 45f (50% maior)
        const val OBSTACLE_WIDTH = 60f
        const val MIN_GAP_Y = 100f // Reduzido de 150f para permitir buracos mais altos
        const val MAX_GAP_Y = 600f // Aumentado de 450f para 600f (maior variação vertical)
        const val GRACE_PERIOD_MS = 300L // Período de graça após iniciar (evita game over imediato)
    }
    
    val gameStarted: Boolean get() = status == GameStatus.RUNNING
    val gameOver: Boolean get() = status == GameStatus.GAME_OVER
}

data class Obstacle(
    var x: Float,
    var topHeight: Float,
    var gapY: Float,
    var gapHeight: Float = FlappyGameState.OBSTACLE_GAP,
    var passed: Boolean = false
)

/**
 * Classe que gerencia a lógica do jogo Flappy.
 */
class FlappyEngine(
    private val screenWidth: Float,
    private val screenHeight: Float
) {
    private val state = FlappyGameState()
    
    init {
        reset()
    }

    fun getState(): FlappyGameState = state

    /**
     * Reseta o jogo para o estado inicial.
     */
    fun reset() {
        if (DEBUG) Log.d("FlappyEngine", "reset() called")
        state.playerY = screenHeight / 2f
        state.playerVelocity = 0f
        state.obstacles.clear()
        state.score = 0
        state.status = GameStatus.IDLE
        state.lastObstacleX = screenWidth
        state.gameStartTime = 0L
        
        // Cria obstáculos iniciais
        for (i in 0..2) {
            addObstacle(screenWidth + i * FlappyGameState.OBSTACLE_SPACING)
        }
        if (DEBUG) Log.d("FlappyEngine", "reset() completed - status=${state.status}")
    }

    /**
     * Handler de tap - inicia o jogo ou aplica impulso.
     * CORREÇÃO: Garante que o primeiro toque SEMPRE aplica impulso imediatamente.
     */
    fun onTap() {
        val statusBefore = state.status
        val velocityBefore = state.playerVelocity
        
        if (DEBUG) Log.d("FlappyEngine", "onTap() called - status=$statusBefore, velBefore=$velocityBefore, y=${state.playerY}")
        
        when (state.status) {
            GameStatus.IDLE -> {
                // Primeiro tap: inicia o jogo E aplica impulso imediatamente
                state.status = GameStatus.RUNNING
                state.playerVelocity = FlappyGameState.JUMP_STRENGTH
                state.gameStartTime = System.currentTimeMillis()
                if (DEBUG) Log.d("FlappyEngine", "Game started + jump applied - status=${state.status}, velocity=${state.playerVelocity}, y=${state.playerY}")
            }
            GameStatus.RUNNING -> {
                // Taps seguintes: aplica impulso SEMPRE
                state.playerVelocity = FlappyGameState.JUMP_STRENGTH
                if (DEBUG) Log.d("FlappyEngine", "Jump applied - velocity=${state.playerVelocity}, y=${state.playerY}")
            }
            GameStatus.GAME_OVER -> {
                // Ignora taps quando game over
                if (DEBUG) Log.d("FlappyEngine", "Tap ignored - game over")
            }
        }
        
        if (DEBUG && statusBefore == GameStatus.RUNNING) {
            Log.d("FlappyEngine", "onTap() completed - velAfter=${state.playerVelocity}, y=${state.playerY}")
        }
    }

    /**
     * Atualiza o estado do jogo (chamado a cada frame).
     */
    fun update(deltaTime: Float) {
        // Só atualiza se o jogo estiver rodando
        if (state.status != GameStatus.RUNNING) return

        // Aplica gravidade
        state.playerVelocity += FlappyGameState.GRAVITY * deltaTime * 60f
        state.playerY += state.playerVelocity * deltaTime * 60f

        // Limita player dentro da tela (com grace period no início)
        val playerRadius = FlappyGameState.PLAYER_SIZE / 2f
        val currentTime = System.currentTimeMillis()
        val timeSinceStart = currentTime - state.gameStartTime
        val inGracePeriod = state.gameStartTime > 0 && timeSinceStart < FlappyGameState.GRACE_PERIOD_MS
        
        if (state.playerY - playerRadius < 0) {
            state.playerY = playerRadius
            if (!inGracePeriod) {
                state.status = GameStatus.GAME_OVER
                if (DEBUG) Log.d("FlappyEngine", "Game over - hit top")
            }
        }
        if (state.playerY + playerRadius > screenHeight) {
            state.playerY = screenHeight - playerRadius
            // Grace period: não dá game over imediatamente após iniciar (evita queda instantânea)
            if (!inGracePeriod) {
                state.status = GameStatus.GAME_OVER
                if (DEBUG) Log.d("FlappyEngine", "Game over - hit bottom (gracePeriod=$inGracePeriod, timeSinceStart=$timeSinceStart)")
            } else {
                if (DEBUG) Log.d("FlappyEngine", "Hit bottom but in grace period - y=${state.playerY}, timeSinceStart=$timeSinceStart")
            }
        }

        // Move obstáculos
        val obstaclesToRemove = mutableListOf<Obstacle>()
        for (obstacle in state.obstacles) {
            obstacle.x -= FlappyGameState.OBSTACLE_SPEED * deltaTime * 60f

            // Verifica se passou pelo obstáculo (incrementa score)
            if (!obstacle.passed && obstacle.x + FlappyGameState.OBSTACLE_WIDTH < screenWidth / 2f - playerRadius) {
                obstacle.passed = true
                state.score++
            }

            // Remove obstáculos que saíram da tela
            if (obstacle.x + FlappyGameState.OBSTACLE_WIDTH < 0) {
                obstaclesToRemove.add(obstacle)
            }
        }
        state.obstacles.removeAll(obstaclesToRemove)

        // Adiciona novos obstáculos quando o último está próximo da tela
        // Verifica se o último obstáculo está dentro da tela ou próximo dela
        val shouldAddObstacle = if (state.obstacles.isEmpty()) {
            true // Sempre adiciona se não houver obstáculos
        } else {
            // Encontra o obstáculo mais à direita
            val rightmostObstacle = state.obstacles.maxByOrNull { it.x } ?: return
            // Adiciona novo quando o obstáculo mais à direita está próximo da tela
            rightmostObstacle.x < screenWidth + FlappyGameState.OBSTACLE_SPACING
        }
        
        if (shouldAddObstacle) {
            // Calcula a posição X do novo obstáculo baseado no último
            val newX = if (state.obstacles.isEmpty()) {
                screenWidth + FlappyGameState.OBSTACLE_SPACING
            } else {
                val rightmostObstacle = state.obstacles.maxByOrNull { it.x } ?: return
                rightmostObstacle.x + FlappyGameState.OBSTACLE_SPACING
            }
            addObstacle(newX)
        }

        // Verifica colisões
        checkCollisions()
    }

    /**
     * Adiciona um novo obstáculo na posição x.
     */
    private fun addObstacle(x: Float) {
        val gapY = (FlappyGameState.MIN_GAP_Y + (FlappyGameState.MAX_GAP_Y - FlappyGameState.MIN_GAP_Y) * Math.random()).toFloat()
        val topHeight = gapY
        state.obstacles.add(
            Obstacle(
                x = x,
                topHeight = topHeight,
                gapY = gapY,
                gapHeight = FlappyGameState.OBSTACLE_GAP
            )
        )
        state.lastObstacleX = x
    }

    /**
     * Verifica colisões entre player e obstáculos.
     */
    private fun checkCollisions() {
        val playerRadius = FlappyGameState.PLAYER_SIZE / 2f
        val playerCenterX = screenWidth / 2f - playerRadius
        val playerCenterY = state.playerY

        for (obstacle in state.obstacles) {
            // Colisão com parte superior do obstáculo
            if (playerCenterX + playerRadius > obstacle.x &&
                playerCenterX - playerRadius < obstacle.x + FlappyGameState.OBSTACLE_WIDTH &&
                playerCenterY - playerRadius < obstacle.topHeight
            ) {
                state.status = GameStatus.GAME_OVER
                if (DEBUG) Log.d("FlappyEngine", "Game over - collision with top obstacle")
                return
            }

            // Colisão com parte inferior do obstáculo
            val bottomObstacleY = obstacle.gapY + obstacle.gapHeight
            if (playerCenterX + playerRadius > obstacle.x &&
                playerCenterX - playerRadius < obstacle.x + FlappyGameState.OBSTACLE_WIDTH &&
                playerCenterY + playerRadius > bottomObstacleY
            ) {
                state.status = GameStatus.GAME_OVER
                if (DEBUG) Log.d("FlappyEngine", "Game over - collision with bottom obstacle")
                return
            }
        }
    }
}

