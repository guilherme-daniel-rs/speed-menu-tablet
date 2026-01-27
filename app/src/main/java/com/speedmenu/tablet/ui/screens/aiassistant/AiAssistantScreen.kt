package com.speedmenu.tablet.ui.screens.aiassistant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.core.ui.components.OrderTopStatusPill
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Tela de assistente de IA com chat completo.
 * Design premium estilo ChatGPT personalizado para restaurante.
 */
@Composable
fun AiAssistantScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AiAssistantViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var inputText by remember { mutableStateOf("") }

    // Estados mockados
    val isConnected = true
    val tableNumber = "17"

    // Scroll automático para última mensagem
    val listState = rememberLazyListState()
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    // Mostra snackbar de erro
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar customizada
            AiAssistantTopBar(
                onBackClick = onNavigateBack,
                isConnected = isConnected,
                tableNumber = tableNumber,
                onCallWaiterClick = {}
            )

            // Área de mensagens
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 24.dp,
                        vertical = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Mensagens do chat
                    items(uiState.messages) { message ->
                        ChatMessageBubble(message = message)
                    }

                    // Indicador de digitação quando loading
                    if (uiState.isLoading) {
                        item {
                            TypingIndicator()
                        }
                    }

                    // Sugestões (apenas se não houver mensagens do usuário)
                    if (uiState.showSuggestions && uiState.messages.none { it.type == MessageType.USER }) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Sugestões rápidas:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = SpeedMenuColors.TextSecondary,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                ChatSuggestions(
                                    onSuggestionClick = { suggestion ->
                                        inputText = suggestion
                                        viewModel.sendMessage(suggestion)
                                        inputText = ""
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Input fixo no rodapé
            ChatInput(
                text = inputText,
                onTextChange = { inputText = it },
                onSend = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    }
                },
                enabled = !uiState.isLoading
            )
        }

        // Snackbar para erros
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
                .padding(horizontal = 24.dp)
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                containerColor = SpeedMenuColors.SurfaceElevated,
                contentColor = SpeedMenuColors.TextPrimary
            )
        }
    }
}

/**
 * Top Bar customizada para a tela de IA.
 * Layout limpo em linha única com título integrado.
 */
@Composable
private fun AiAssistantTopBar(
    onBackClick: () -> Unit,
    isConnected: Boolean,
    tableNumber: String,
    onCallWaiterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp) // Altura padrão consistente com outras TopBars
            .background(
                color = SpeedMenuColors.Surface.copy(alpha = 0.95f)
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lado esquerdo: Botão Voltar + Título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Botão Voltar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable(onClick = onBackClick)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SpeedMenuColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Voltar",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = SpeedMenuColors.TextSecondary,
                        fontSize = 16.sp
                    )
                }

                // Título com ícone
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = SpeedMenuColors.PrimaryLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Pergunte à IA",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 20.sp,
                        lineHeight = 24.sp
                    )
                }
            }

            // Lado direito: Status pill
            OrderTopStatusPill(
                isConnected = isConnected,
                tableNumber = tableNumber,
                onCallWaiterClick = onCallWaiterClick,
                enabled = true
            )
        }
    }
}
