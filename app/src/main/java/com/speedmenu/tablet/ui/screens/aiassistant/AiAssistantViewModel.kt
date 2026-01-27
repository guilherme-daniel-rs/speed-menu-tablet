package com.speedmenu.tablet.ui.screens.aiassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Tipo de mensagem no chat.
 */
enum class MessageType {
    USER,
    AI
}

/**
 * Mensagem do chat.
 */
data class ChatMessage(
    val id: String,
    val text: String,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Estado da UI da tela de assistente de IA.
 */
data class AiAssistantUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showSuggestions: Boolean = true
)

/**
 * ViewModel da tela de assistente de IA.
 * Gerencia o estado e lógica do chat.
 */
@HiltViewModel
class AiAssistantViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AiAssistantUiState())
    val uiState: StateFlow<AiAssistantUiState> = _uiState.asStateFlow()

    init {
        // Mensagem inicial da IA
        addInitialMessage()
    }

    /**
     * Adiciona mensagem inicial de boas-vindas.
     */
    private fun addInitialMessage() {
        val welcomeMessage = ChatMessage(
            id = "welcome_${System.currentTimeMillis()}",
            text = "Olá! Posso te ajudar a escolher o prato ideal 😄",
            type = MessageType.AI
        )
        _uiState.value = _uiState.value.copy(
            messages = listOf(welcomeMessage),
            showSuggestions = true
        )
    }

    /**
     * Envia uma mensagem do usuário e processa a resposta da IA.
     */
    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = ChatMessage(
            id = "user_${System.currentTimeMillis()}",
            text = text.trim(),
            type = MessageType.USER
        )

        // Adiciona mensagem do usuário
        val currentMessages = _uiState.value.messages
        _uiState.value = _uiState.value.copy(
            messages = currentMessages + userMessage,
            showSuggestions = false,
            isLoading = true,
            error = null
        )

        // Simula processamento da IA
        viewModelScope.launch {
            delay(1000 + (Math.random() * 1500).toLong()) // Delay realista entre 1-2.5s

            try {
                val aiResponse = generateAiResponse(text)
                val aiMessage = ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    text = aiResponse,
                    type = MessageType.AI
                )

                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + aiMessage,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ops, tive um probleminha 😕 Pode tentar novamente?"
                )
            }
        }
    }

    /**
     * Gera resposta da IA baseada na pergunta do usuário.
     * Simula comportamento de um garçom experiente + sommelier.
     */
    private fun generateAiResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()

        return when {
            // Recomendações gerais
            lowerMessage.contains("recomenda") || lowerMessage.contains("recomendação") -> {
                "Se você gosta de algo mais suave, o Filé Mignon ao Molho é uma excelente escolha hoje 👌"
            }
            
            // Combinações com vinho
            lowerMessage.contains("vinho") || lowerMessage.contains("bebida") -> {
                "Quer uma sugestão que combina muito bem com vinho tinto? O Risotto de Cogumelos é perfeito para isso 🍷"
            }
            
            // Restrições alimentares
            lowerMessage.contains("lactose") || lowerMessage.contains("sem leite") -> {
                "Temos várias opções sem lactose! O Salmão Grelhado e o Risotto de Cogumelos são excelentes escolhas 🌿"
            }
            
            // Pratos mais pedidos
            lowerMessage.contains("mais pedido") || lowerMessage.contains("popular") || lowerMessage.contains("favorito") -> {
                "O Filé Mignon ao Molho é um dos favoritos da casa! Sempre muito bem avaliado 🍷"
            }
            
            // Pratos leves
            lowerMessage.contains("leve") || lowerMessage.contains("saudável") || lowerMessage.contains("light") -> {
                "Para algo mais leve, recomendo o Salmão Grelhado com legumes ou nossa Salada Caesar Premium. Ambos são deliciosos e nutritivos 🥗"
            }
            
            // Surpresas
            lowerMessage.contains("surpreenda") || lowerMessage.contains("surpresa") -> {
                "Que tal experimentar nosso Risotto de Cogumelos? É um prato especial da casa que sempre impressiona os clientes 🍽️"
            }
            
            // Perguntas sobre ingredientes
            lowerMessage.contains("ingrediente") || lowerMessage.contains("tem o que") -> {
                "Posso ajudar! Qual prato você tem interesse? Posso detalhar os ingredientes e sugerir alternativas se necessário 😊"
            }
            
            // Preço
            lowerMessage.contains("preço") || lowerMessage.contains("quanto") || lowerMessage.contains("valor") -> {
                "Nossos pratos variam entre R$ 45 e R$ 85. Posso sugerir opções dentro de uma faixa específica se quiser!"
            }
            
            // Saudação
            lowerMessage.contains("olá") || lowerMessage.contains("oi") || lowerMessage.contains("bom dia") || lowerMessage.contains("boa tarde") || lowerMessage.contains("boa noite") -> {
                "Olá! Estou aqui para ajudar você a escolher o prato perfeito. O que você está procurando hoje? 😄"
            }
            
            // Default - resposta genérica mas personalizada
            else -> {
                val responses = listOf(
                    "Entendi! Deixa eu pensar... Que tal experimentar nosso Filé Mignon ao Molho? É uma escolha sempre acertada 👌",
                    "Boa pergunta! Recomendo o Risotto de Cogumelos - é um prato especial da casa que combina muito bem 🍽️",
                    "Para essa ocasião, sugiro o Salmão Grelhado. É leve, saboroso e sempre bem avaliado pelos nossos clientes 🐟",
                    "Que tal algo diferente? O Risotto de Cogumelos é uma excelente opção se você quer experimentar algo especial da casa 🍷"
                )
                responses.random()
            }
        }
    }

    /**
     * Limpa o erro.
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

