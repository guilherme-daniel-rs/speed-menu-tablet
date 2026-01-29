package com.speedmenu.tablet.ui.screens.rateplace

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.speedmenu.tablet.core.ui.components.SpeedMenuPrimaryButton
import com.speedmenu.tablet.core.ui.components.AppTopBar
import com.speedmenu.tablet.core.ui.components.WaiterCalledDialog
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors
import com.speedmenu.tablet.ui.viewmodel.WaiterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.delay

/**
 * Tela de avaliação do local (refatorada - premium).
 * Layout em 2 colunas para tablet landscape com hierarquia visual melhorada.
 */
@Composable
fun RatePlaceScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: RatePlaceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    // WaiterViewModel centralizado para gerenciar chamadas de garçom
    val waiterViewModel: WaiterViewModel = hiltViewModel()
    val waiterUiState by waiterViewModel.uiState.collectAsState()
    
    // Função para fechar teclado e remover foco
    fun dismissKeyboard() {
        focusManager.clearFocus(force = true)
        keyboardController?.hide()
    }

    // Estados mockados (mesmo padrão das outras telas)
    val isConnected = true
    val tableNumber = "17"

    // Mostra modal de sucesso após envio bem-sucedido
    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            showSuccessDialog = true
        }
    }

    // Mostra snackbar de erro se houver
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
            // Detectar toques fora dos componentes interativos para fechar teclado
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        dismissKeyboard()
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Action Bar
            AppTopBar(
                showBackButton = true,
                onBackClick = onNavigateBack,
                isConnected = isConnected,
                tableNumber = tableNumber,
                onCallWaiterClick = {
                    waiterViewModel.requestWaiter("RatePlaceScreen")
                },
                screenName = "RatePlaceScreen"
            )

            // Conteúdo principal (scrollável)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 40.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // Cabeçalho
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Avaliar o local",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Sua opinião ajuda a melhorar a experiência da mesa.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = SpeedMenuColors.TextSecondary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
                        )
                    }

                    // Layout em 2 colunas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        // Coluna esquerda: Estrelas + Chips
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(32.dp)
                        ) {
                            // Seção de avaliação por estrelas
                            StarRatingRow(
                                rating = uiState.rating,
                                onRatingSelected = { rating ->
                                    viewModel.onSelectRating(rating)
                                    dismissKeyboard()
                                }
                            )

                            // Chips de feedback rápido
                            FeedbackChipsRow(
                                selectedTags = uiState.selectedTags,
                                onTagToggle = { tag ->
                                    viewModel.toggleTag(tag)
                                    // Não fecha teclado ao tocar em chips (comportamento opcional)
                                }
                            )
                        }

                        // Coluna direita: Observação + Botão
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            // Campo de observação
                            CommentTextField(
                                value = uiState.comment,
                                onValueChange = viewModel::onCommentChange,
                                onDismissKeyboard = { dismissKeyboard() },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Botão de enviar (flat premium)
                            FlatPremiumButton(
                                text = if (uiState.isSubmitting) "Enviando…" else "Enviar avaliação",
                                onClick = {
                                    // Fechar teclado e remover foco antes de enviar
                                    dismissKeyboard()
                                    viewModel.submit()
                                },
                                enabled = uiState.rating >= 1 && !uiState.isSubmitting,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // Snackbar host (apenas para erros)
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
                .padding(horizontal = 40.dp)
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                shape = RoundedCornerShape(12.dp),
                containerColor = SpeedMenuColors.SurfaceElevated,
                contentColor = SpeedMenuColors.TextPrimary
            )
        }

        // Modal de sucesso (em tela cheia)
        // Dialog de garçom chamado (gerenciado pelo WaiterViewModel)
        WaiterCalledDialog(
            visible = waiterUiState.showDialog,
            onDismiss = { waiterViewModel.dismissDialog() },
            onConfirm = { waiterViewModel.confirmWaiterCall() }
        )
        
        RatePlaceSuccessDialog(
            visible = showSuccessDialog,
            onDismiss = {
                // Fechar teclado antes de fechar modal
                dismissKeyboard()
                showSuccessDialog = false
                viewModel.resetSuccess()
            },
            onConfirm = {
                // Fechar teclado antes de navegar
                dismissKeyboard()
                showSuccessDialog = false
                viewModel.resetSuccess()
                onNavigateToHome()
            }
        )
    }
}

/**
 * Componente de avaliação por estrelas com texto dinâmico.
 * Estrelas maiores e mais elegantes com feedback textual melhorado.
 */
@Composable
private fun StarRatingRow(
    rating: Int,
    onRatingSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Texto emocional premium baseado na nota
    val feedbackText = when (rating) {
        0 -> "Toque para avaliar"
        1 -> "Péssimo"
        2 -> "Ruim"
        3 -> "Bom! Sempre podemos melhorar."
        4 -> "Muito bom! Obrigado pelo feedback."
        5 -> "Excelente! Ficamos felizes com sua experiência."
        else -> "Toque para avaliar"
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Estrelas (centralizadas)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (starIndex in 1..5) {
                val isSelected = starIndex <= rating
                
                // Animação de cor
                val starColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        SpeedMenuColors.Primary
                    } else {
                        SpeedMenuColors.TextTertiary.copy(alpha = 0.3f)
                    },
                    animationSpec = tween(200),
                    label = "star_color_$starIndex"
                )

                // Animação de escala ao tocar
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val scale by animateFloatAsState(
                    targetValue = if (isPressed) 0.85f else 1f,
                    animationSpec = tween(150),
                    label = "star_scale_$starIndex"
                )

                Box(
                    modifier = Modifier
                        .size(72.dp) // Aumentado de 64 para 72 (+12%)
                        .scale(scale)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onRatingSelected(starIndex)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrela $starIndex de 5",
                        tint = starColor,
                        modifier = Modifier.size(72.dp)
                    )
                    // Overlay de brilho sutil quando selecionado
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            SpeedMenuColors.PrimaryLight.copy(alpha = 0.15f),
                                            Color.Transparent
                                        ),
                                        radius = 36f
                                    )
                                )
                        )
                    }
                }
                
                // Espaçamento entre estrelas
                if (starIndex < 5) {
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }

        // Texto de feedback (com animação fade suave)
        androidx.compose.animation.AnimatedVisibility(
            visible = rating > 0,
            enter = fadeIn(animationSpec = tween(400)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = feedbackText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = SpeedMenuColors.PrimaryLight,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Sua nota: $rating/5",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = SpeedMenuColors.TextSecondary,
                    fontSize = 14.sp
                )
            }
        }

        // Texto quando não há nota selecionada
        androidx.compose.animation.AnimatedVisibility(
            visible = rating == 0,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Text(
                text = feedbackText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = SpeedMenuColors.TextTertiary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Componente de chips de feedback rápido.
 * Permite selecionar até 3 tags relacionadas à experiência.
 */
@Composable
private fun FeedbackChipsRow(
    selectedTags: Set<String>,
    onTagToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val availableTags = listOf(
        "Atendimento",
        "Comida",
        "Ambiente",
        "Rapidez",
        "Preço justo",
        "Limpeza"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "O que mais chamou atenção? (opcional)",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = SpeedMenuColors.TextSecondary,
            fontSize = 14.sp
        )

        // Chips em duas linhas (flexível)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Primeira linha de chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                availableTags.take(3).forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    val isDisabled = !isSelected && selectedTags.size >= 3
                    
                    FeedbackChip(
                        tag = tag,
                        isSelected = isSelected,
                        isDisabled = isDisabled,
                        onToggle = { onTagToggle(tag) }
                    )
                }
            }
            // Segunda linha de chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                availableTags.drop(3).forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    val isDisabled = !isSelected && selectedTags.size >= 3
                    
                    FeedbackChip(
                        tag = tag,
                        isSelected = isSelected,
                        isDisabled = isDisabled,
                        onToggle = { onTagToggle(tag) }
                    )
                }
            }
        }

        // Indicador de limite (discreto)
        if (selectedTags.size >= 3) {
            Text(
                text = "Máximo de 3 selecionados",
                style = MaterialTheme.typography.bodySmall,
                color = SpeedMenuColors.TextTertiary.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}

/**
 * Chip individual de feedback.
 */
@Composable
private fun FeedbackChip(
    tag: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onToggle: () -> Unit
) {
    // Animação de cor
    val chipColor by animateColorAsState(
        targetValue = if (isSelected) {
            SpeedMenuColors.SurfaceElevated.copy(alpha = 0.8f) // Fundo levemente mais claro quando selecionado
        } else {
            SpeedMenuColors.Surface.copy(alpha = 0.3f) // Reduzido contraste dos não selecionados
        },
        animationSpec = tween(200),
        label = "chip_color_$tag"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            SpeedMenuColors.Primary.copy(alpha = 0.7f) // Borda laranja quando selecionado
        } else {
            SpeedMenuColors.BorderSubtle.copy(alpha = 0.3f) // Borda mais discreta quando não selecionado
        },
        animationSpec = tween(200),
        label = "chip_border_$tag"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            SpeedMenuColors.PrimaryLight // Texto laranja quando selecionado
        } else if (isDisabled) {
            SpeedMenuColors.TextTertiary.copy(alpha = 0.3f)
        } else {
            SpeedMenuColors.TextSecondary.copy(alpha = 0.7f) // Texto mais discreto quando não selecionado
        },
        animationSpec = tween(200),
        label = "chip_text_$tag"
    )

    Box(
        modifier = Modifier
            .background(
                color = chipColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                enabled = !isDisabled,
                onClick = onToggle
            )
            .padding(horizontal = 16.dp, vertical = 8.dp) // Reduzido de 10 para 8
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor,
            fontSize = 14.sp
        )
    }
}

/**
 * Campo de texto para comentário opcional (mais enxuto e elegante).
 * Reduz altura inicial e expande naturalmente.
 */
@Composable
private fun CommentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDismissKeyboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Animação da borda e elevação ao focar
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) {
            SpeedMenuColors.Primary.copy(alpha = 0.6f) // Borda laranja discreta
        } else {
            SpeedMenuColors.BorderSubtle.copy(alpha = 0.4f)
        },
        animationSpec = tween(200),
        label = "border_color"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isFocused) 4f else 0f, // Elevação mais suave
        animationSpec = tween(200),
        label = "elevation"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Observação (opcional)",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = SpeedMenuColors.TextSecondary,
            fontSize = 14.sp
        )

        Box {
            // Background e borda com elevação ao focar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp, max = 200.dp) // Reduzido de 100 para 80
                    .shadow(
                        elevation = elevation.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = SpeedMenuColors.Overlay.copy(alpha = 0.15f), // Shadow mais suave
                        ambientColor = SpeedMenuColors.Overlay.copy(alpha = 0.08f)
                    )
                    .background(
                        color = SpeedMenuColors.Surface.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = if (isFocused) 1.5.dp else 1.dp, // Borda laranja discreta quando focado
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester)
                        .focusable(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    ),
                    maxLines = 8,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(force = true)
                            keyboardController?.hide()
                        }
                    ),
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "Conte como foi sua experiência (opcional)",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = SpeedMenuColors.TextTertiary,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            // Contador de caracteres (canto inferior direito) - mais discreto
            Text(
                text = "${value.length}/250",
                style = MaterialTheme.typography.bodySmall,
                color = SpeedMenuColors.TextTertiary.copy(alpha = 0.5f), // Mais discreto
                fontSize = 11.sp, // Menor
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 12.dp, bottom = 8.dp)
            )
        }
    }
}

/**
 * Botão flat premium (sem efeito glossy pesado).
 * Estilo mais limpo e elegante, similar ao botão "Ok, entendi".
 */
@Composable
private fun FlatPremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animação suave de scale ao pressionar
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )

    val buttonShape = RoundedCornerShape(16.dp)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp) // Altura mais moderada
            .scale(scale)
            .shadow(
                elevation = if (enabled) 8.dp else 0.dp, // Shadow mais suave
                shape = buttonShape,
                spotColor = SpeedMenuColors.Overlay.copy(alpha = 0.2f),
                ambientColor = SpeedMenuColors.Overlay.copy(alpha = 0.1f)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                color = if (enabled) {
                    SpeedMenuColors.Primary
                } else {
                    SpeedMenuColors.Disabled.copy(alpha = 0.6f) // Opacidade reduzida quando desabilitado
                },
                shape = buttonShape
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SpeedMenuColors.TextOnPrimary,
            fontSize = 18.sp
        )
    }
}

/**
 * Dialog premium para confirmar envio de avaliação.
 * Design idêntico ao WaiterCalledDialog, reutilizando padrão visual.
 */
@Composable
private fun RatePlaceSuccessDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    
    if (!visible) return

    // Animações de entrada (mesmo padrão do WaiterCalledDialog)
    var showContent by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0.98f,
        animationSpec = tween(200, easing = LinearOutSlowInEasing),
        label = "dialog_scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(180, easing = LinearOutSlowInEasing),
        label = "dialog_alpha"
    )

    // Animação de pulse suave no ícone
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val iconPulseAlpha = infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_pulse_alpha"
    ).value

    LaunchedEffect(visible) {
        if (visible) {
            showContent = true
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrim padronizado (overlay escuro)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
            )
            
            // Card do dialog (largura limitada, centralizado)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth(0.7f)
                    .heightIn(max = 420.dp)
                    .padding(horizontal = 24.dp)
                    .scale(scale)
                    .alpha(alpha)
            ) {
                // Background do card com borda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF11161C).copy(alpha = 0.95f),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .drawBehind {
                            // Borda branca sutil
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.12f),
                                style = Stroke(width = 1.dp.toPx()),
                                cornerRadius = CornerRadius(28.dp.toPx(), 28.dp.toPx())
                            )
                        }
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(28.dp),
                            spotColor = Color.Black.copy(alpha = 0.35f),
                            ambientColor = Color.Black.copy(alpha = 0.18f)
                        )
                )
                
                // Conteúdo sobreposto
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ========== TOPO: Ícone de estrela ==========
                    Box(
                        modifier = Modifier.size(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Glow suave
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            SpeedMenuColors.Primary.copy(alpha = 0.3f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        )
                        
                        // Círculo laranja principal
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    color = SpeedMenuColors.Primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SpeedMenuColors.TextPrimary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(32.dp)
                                    .alpha(iconPulseAlpha)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ========== MEIO: Título, subtítulo, badge ==========
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Título grande
                        Text(
                            text = "Avaliação enviada",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SpeedMenuColors.TextPrimary,
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Subtítulo
                        Text(
                            text = "Obrigado por compartilhar sua experiência com a gente.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = SpeedMenuColors.TextSecondary.copy(alpha = 0.75f),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Badge de sucesso verde
                        Row(
                            modifier = Modifier
                                .background(
                                    color = SpeedMenuColors.Success.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SpeedMenuColors.Success,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Feedback registrado",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = SpeedMenuColors.Success,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Spacer para empurrar botão para o rodapé
                    Spacer(modifier = Modifier.weight(1f))

                    // Espaçamento entre badge e CTA
                    Spacer(modifier = Modifier.height(28.dp))

                    // ========== RODAPÉ: Botão principal ==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(999.dp),
                                spotColor = Color.Black.copy(alpha = 0.25f),
                                ambientColor = Color.Black.copy(alpha = 0.12f)
                            )
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        SpeedMenuColors.Primary,
                                        Color(0xFFC76A05)
                                    )
                                ),
                                shape = RoundedCornerShape(999.dp)
                            )
                            .clickable(onClick = {
                                // Fechar teclado antes de confirmar
                                focusManager.clearFocus(force = true)
                                keyboardController?.hide()
                                onConfirm()
                            }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ok, entendi",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
