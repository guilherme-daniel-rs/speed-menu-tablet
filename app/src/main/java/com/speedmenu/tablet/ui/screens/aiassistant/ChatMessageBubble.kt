package com.speedmenu.tablet.ui.screens.aiassistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Componente de balão de mensagem do chat.
 */
@Composable
fun ChatMessageBubble(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    when (message.type) {
        MessageType.USER -> {
            UserMessageBubble(
                text = message.text,
                modifier = modifier
            )
        }
        MessageType.AI -> {
            AiMessageBubble(
                text = message.text,
                modifier = modifier
            )
        }
    }
}

/**
 * Balão de mensagem do usuário (alinhado à direita).
 */
@Composable
private fun UserMessageBubble(
    text: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .clip(RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp))
                .background(
                    color = colorScheme.surfaceVariant.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)
                )
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = colorScheme.onSurface,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}

/**
 * Balão de mensagem da IA (alinhado à esquerda).
 */
@Composable
private fun AiMessageBubble(
    text: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        // Ícone da IA
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorScheme.primaryContainer,
                            colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "IA",
                tint = colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Balão de mensagem
        Box(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .clip(RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorScheme.primary.copy(alpha = 0.85f),
                            colorScheme.primary.copy(alpha = 0.9f)
                        )
                    ),
                    shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
                )
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = colorScheme.onPrimary,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}

/**
 * Indicador de digitação da IA (3 pontinhos pulsando).
 */
@Composable
fun TypingIndicator(
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone da IA
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorScheme.primaryContainer,
                            colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "IA",
                tint = colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Balão com pontinhos
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorScheme.primary.copy(alpha = 0.85f),
                            colorScheme.primary.copy(alpha = 0.9f)
                        )
                    ),
                    shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
                )
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            TypingDots()
        }
    }
}

/**
 * Animação de 3 pontinhos pulsando.
 */
@Composable
private fun TypingDots() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            TypingDot(delayMillis = index * 200)
        }
    }
}

@Composable
private fun TypingDot(delayMillis: Int) {
    val colorScheme = MaterialTheme.colorScheme
    
    val alpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(
                durationMillis = 600,
                delayMillis = delayMillis,
                easing = androidx.compose.animation.core.LinearEasing
            ),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "typing_dot"
    )
    
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(
                color = colorScheme.onPrimary.copy(alpha = 0.3f + alpha * 0.7f),
                shape = androidx.compose.foundation.shape.CircleShape
            )
    )
}

