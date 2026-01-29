package com.speedmenu.tablet.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset

/**
 * Dados de tópico e categoria para o menu lateral do fluxo de pedido.
 */
data class MenuTopic(
    val id: String,
    val title: String,
    val categories: List<MenuCategory>
)

data class MenuCategory(
    val id: String,
    val name: String,
    val topicId: String
)

/**
 * Sidebar hierárquico para o fluxo "Iniciar pedido" em diante.
 * Exibe tópicos e categorias de forma organizada.
 */
@Composable
fun OrderFlowSidebar(
    topics: List<MenuTopic>,
    selectedCategoryId: String? = null,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado do scroll para preservar posição e rolagem suave
    val scrollState = rememberLazyListState()
    
    // Scroll automático para a categoria selecionada quando necessário
    LaunchedEffect(selectedCategoryId) {
        selectedCategoryId?.let { categoryId ->
            // Aguarda um frame para garantir que o layout está pronto
            delay(50)
            
            // Encontra o índice da categoria selecionada
            var itemIndex = 0
            
            topics.forEachIndexed { topicIndex, topic ->
                // Conta divisória antes do tópico (exceto o primeiro)
                if (topicIndex > 0) {
                    itemIndex++
                }
                
                // Conta o título do tópico
                itemIndex++
                
                // Verifica se a categoria está neste tópico
                val categoryIndex = topic.categories.indexOfFirst { it.id == categoryId }
                if (categoryIndex >= 0) {
                    // Adiciona o índice da categoria dentro do tópico
                    val targetIndex = itemIndex + categoryIndex
                    
                    // Verifica se está visível na viewport
                    val layoutInfo = scrollState.layoutInfo
                    val firstVisibleIndex = layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
                    val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                    
                    // Scroll suave apenas se não estiver visível
                    val isVisible = targetIndex >= firstVisibleIndex && targetIndex <= lastVisibleIndex
                    if (!isVisible) {
                        scrollState.animateScrollToItem(
                            index = targetIndex,
                            scrollOffset = -80 // Offset para centralizar melhor na viewport
                        )
                    }
                    return@LaunchedEffect
                } else {
                    // Adiciona o número de categorias deste tópico ao contador
                    itemIndex += topic.categories.size
                }
            }
        }
    }
    
    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(vertical = 28.dp, horizontal = 24.dp) // Mais espaçamento para tablets
    ) {
            // LazyColumn com scroll suave e sem scrollbar visível
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
            topics.forEachIndexed { topicIndex, topic ->
                // Divisória sutil entre tópicos (exceto o primeiro)
                if (topicIndex > 0) {
                    item(key = "divider_$topicIndex") {
                        TopicDivider()
                    }
                }
                
                // Título do tópico com linhas sutis
                item(key = "topic_${topic.id}") {
                    TopicTitle(
                        text = topic.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                PaddingValues(
                                    top = 0.dp,
                                    bottom = 16.dp // Espaço reduzido entre título e categorias para melhor agrupamento visual
                                )
                            )
                    )
                }
                
                // Lista de categorias do tópico (cada uma como item separado)
                items(
                    items = topic.categories,
                    key = { it.id }
                ) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = selectedCategoryId == category.id,
                        onClick = { onCategoryClick(category.id) }
                    )
                }
            }
        }
    }
}

/**
 * Item de categoria clicável com highlight sutil quando selecionado.
 * Inclui transições suaves para fundo, barra lateral e cor do texto.
 * Feedback visual imediato ao clicar (ripple + mudança de cor).
 */
@Composable
private fun CategoryItem(
    category: MenuCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    
    // Interaction source para detectar estado pressed
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animações suaves para transição de estados (150-200ms)
    // Mudança instantânea quando pressionado para feedback imediato
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isSelected -> colorScheme.primary.copy(alpha = 0.08f) // Fundo levemente destacado
            isPressed -> colorScheme.surface.copy(alpha = 0.15f) // Feedback imediato ao pressionar
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = if (isPressed) 0 else 180), // Mudança instantânea quando pressionado
        label = "category_background"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            colorScheme.onSurface.copy(alpha = 0.98f) // Texto mais claro quando selecionado
        } else {
            colorScheme.onSurfaceVariant.copy(alpha = 0.9f) // Texto secundário
        },
        animationSpec = tween(durationMillis = 180),
        label = "category_text_color"
    )
    
    val indicatorAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 180),
        label = "category_indicator_alpha"
    )
    
    val indicatorWidth by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isSelected) 3.dp.value else 0.dp.value,
        animationSpec = tween(durationMillis = 180),
        label = "category_indicator_width"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura aumentada para melhor espaçamento vertical
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp) // Cantos mais arredondados
            )
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colorScheme.primary.copy(alpha = 0.2f), // Ripple discreto
                    bounded = true
                ),
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 16.dp), // Padding vertical aumentado
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador visual sutil (barra lateral fina) com animação suave
            if (indicatorAlpha > 0f) {
                Box(
                    modifier = Modifier
                        .width(indicatorWidth.dp)
                        .fillMaxHeight()
                        .background(
                            color = colorScheme.primary.copy(alpha = 0.65f * indicatorAlpha),
                            shape = RoundedCornerShape(1.5.dp)
                        )
                )
                Spacer(modifier = Modifier.width(12.dp)) // Mais espaço para respiração
            } else {
                // Espaço reservado para manter alinhamento quando não selecionado
                Spacer(modifier = Modifier.width(15.dp))
            }

            // Texto da categoria com cor animada
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal, // Mantém peso regular
                color = textColor,
                fontSize = 17.sp, // Aumentado de 16.sp para melhor legibilidade à distância
                lineHeight = 24.sp, // Line height aumentado para melhor respiração
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Linha divisória sutil para tópicos, desenhada com Canvas para garantir visibilidade.
 */
@Composable
private fun TopicDividerLine(
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = modifier
            .height(18.dp)
            .fillMaxWidth()
            .drawBehind {
                if (this.size.width > 0f && this.size.height > 0f) {
                    val y = this.size.height / 2f
                    val strokeWidth = 2.dp.toPx() // Pode reduzir para 1.5.dp se ficar pesado
                    val baseAlpha = 0.14f // Ajuste fino: entre 0.11 (sutil) e 0.16 (mais visível)
                    val lineColor = colorScheme.onSurface.copy(alpha = baseAlpha)
                    
                    val width = this.size.width
                    val segment1End = width * 0.12f // 12% inicial
                    val segment2Start = width * 0.12f
                    val segment2End = width * 0.88f // 76% meio (12% + 76% = 88%)
                    val segment3Start = width * 0.88f
                    
                    // Segmento 1 (12% inicial): fade in (alpha 0.00 -> baseAlpha)
                    if (segment1End > 0f) {
                        for (i in 0..10) {
                            val x1 = (segment1End / 10f) * i
                            val x2 = (segment1End / 10f) * (i + 1)
                            val alpha = (baseAlpha * i / 10f).coerceIn(0f, baseAlpha)
                            this.drawLine(
                                color = colorScheme.onSurface.copy(alpha = alpha),
                                start = Offset(x1, y),
                                end = Offset(x2, y),
                                strokeWidth = strokeWidth
                            )
                        }
                    }
                    
                    // Segmento 2 (76% meio): alpha constante baseAlpha
                    if (segment2End > segment2Start) {
                        this.drawLine(
                            color = lineColor,
                            start = Offset(segment2Start, y),
                            end = Offset(segment2End, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    
                    // Segmento 3 (12% final): fade out (alpha baseAlpha -> 0.00)
                    if (width > segment3Start) {
                        for (i in 0..10) {
                            val x1 = segment3Start + ((width - segment3Start) / 10f) * i
                            val x2 = segment3Start + ((width - segment3Start) / 10f) * (i + 1)
                            val alpha = (baseAlpha * (10f - i) / 10f).coerceIn(0f, baseAlpha)
                            this.drawLine(
                                color = colorScheme.onSurface.copy(alpha = alpha),
                                start = Offset(x1, y),
                                end = Offset(x2, y),
                                strokeWidth = strokeWidth
                            )
                        }
                    }
                }
            }
    )
}

/**
 * Título do tópico com linhas sutis à esquerda e direita.
 */
@Composable
private fun TopicTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Row(
        modifier = modifier
            .heightIn(min = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Linha sutil à esquerda
        TopicDividerLine(
            modifier = Modifier.weight(1f)
        )
        
        // Texto centralizado
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = colorScheme.primary.copy(alpha = 0.85f), // Cor primária suave mais visível
            fontSize = 15.sp,
            letterSpacing = 0.8.sp,
            modifier = Modifier.padding(horizontal = 12.dp) // Espaçamento entre linhas e texto
        )
        
        // Linha sutil à direita
        TopicDividerLine(
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Divisória sutil entre tópicos.
 */
@Composable
private fun TopicDivider() {
    val colorScheme = MaterialTheme.colorScheme
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(vertical = 0.dp) // Sem padding extra, o espaçamento é controlado pelos Spacers
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        colorScheme.outlineVariant.copy(alpha = 0.1f), // Sutil e discreto
                        colorScheme.outlineVariant.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
    )
}

