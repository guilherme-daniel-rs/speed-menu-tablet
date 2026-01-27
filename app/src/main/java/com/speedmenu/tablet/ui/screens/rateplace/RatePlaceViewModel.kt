package com.speedmenu.tablet.ui.screens.rateplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.speedmenu.tablet.domain.repository.RatingRepository
import javax.inject.Inject

/**
 * Estado da UI da tela de avaliação do local.
 */
data class RatePlaceUiState(
    val rating: Int = 0,
    val comment: String = "",
    val selectedTags: Set<String> = emptySet(),
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)

/**
 * ViewModel da tela de avaliação do local.
 * Gerencia o estado e lógica da avaliação.
 */
@HiltViewModel
class RatePlaceViewModel @Inject constructor(
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RatePlaceUiState())
    val uiState: StateFlow<RatePlaceUiState> = _uiState.asStateFlow()

    /**
     * Atualiza a nota selecionada (1 a 5).
     */
    fun onSelectRating(rating: Int) {
        if (rating in 1..5) {
            _uiState.value = _uiState.value.copy(
                rating = rating,
                errorMessage = null
            )
        }
    }

    /**
     * Atualiza o comentário do usuário.
     */
    fun onCommentChange(comment: String) {
        // Limita a 250 caracteres
        val limitedComment = if (comment.length <= 250) comment else comment.take(250)
        _uiState.value = _uiState.value.copy(
            comment = limitedComment,
            errorMessage = null
        )
    }

    /**
     * Alterna uma tag selecionada.
     * Limita a 3 tags selecionadas no máximo.
     */
    fun toggleTag(tag: String) {
        val currentTags = _uiState.value.selectedTags
        val newTags = if (currentTags.contains(tag)) {
            currentTags - tag
        } else {
            if (currentTags.size < 3) {
                currentTags + tag
            } else {
                currentTags // Não adiciona se já tem 3
            }
        }
        _uiState.value = _uiState.value.copy(selectedTags = newTags)
    }

    /**
     * Envia a avaliação.
     */
    fun submit() {
        val currentState = _uiState.value
        
        // Validação: rating deve estar entre 1 e 5
        if (currentState.rating < 1 || currentState.rating > 5) {
            _uiState.value = currentState.copy(
                errorMessage = "Por favor, selecione uma nota"
            )
            return
        }

        // Inicia o envio
        _uiState.value = currentState.copy(
            isSubmitting = true,
            errorMessage = null
        )

        viewModelScope.launch {
            ratingRepository.submitRating(
                rating = currentState.rating,
                comment = currentState.comment
            ).fold(
                onSuccess = {
                    // Sucesso
                    _uiState.value = _uiState.value.copy(
                        isSubmitting = false,
                        success = true
                    )
                },
                onFailure = { error ->
                    // Erro
                    _uiState.value = _uiState.value.copy(
                        isSubmitting = false,
                        errorMessage = error.message ?: "Erro ao enviar avaliação. Tente novamente."
                    )
                }
            )
        }
    }

    /**
     * Reseta o estado de sucesso (útil após navegação).
     */
    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(success = false)
    }
}

