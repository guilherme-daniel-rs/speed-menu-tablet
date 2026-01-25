package com.speedmenu.tablet.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

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
    // Estado do scroll para preservar posição
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
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SpeedMenuColors.BackgroundPrimary)
            .padding(vertical = 28.dp, horizontal = 24.dp) // Mais espaçamento para tablets
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            topics.forEachIndexed { topicIndex, topic ->
                // Espaço antes do tópico (exceto o primeiro)
                if (topicIndex > 0) {
                    // Espaço antes da divisória
                    item(key = "spacer_before_divider_$topicIndex") {
                        Spacer(modifier = Modifier.height(28.dp)) // Espaço respirável antes da divisória
                    }
                    
                    // Divisória sutil entre tópicos
                    item(key = "divider_$topicIndex") {
                        TopicDivider()
                    }
                    
                    // Espaço após a divisória
                    item(key = "spacer_after_divider_$topicIndex") {
                        Spacer(modifier = Modifier.height(20.dp)) // Espaço após divisória
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
                                    top = 0.dp, // Espaçamento controlado pelos Spacers acima
                                    bottom = 28.dp // Espaço aumentado entre título e categorias
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
 */
@Composable
private fun CategoryItem(
    category: MenuCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura aumentada para melhor espaçamento vertical
            .background(
                color = if (isSelected) {
                    SpeedMenuColors.Primary.copy(alpha = 0.08f) // Fundo muito discreto
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(8.dp) // Cantos mais arredondados
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 16.dp), // Padding vertical aumentado
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador visual sutil (linha vertical fina) quando selecionado
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(
                            color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.65f), // Levemente mais visível
                            shape = RoundedCornerShape(1.5.dp)
                        )
                )
                Spacer(modifier = Modifier.width(12.dp)) // Mais espaço para respiração
            } else {
                // Espaço reservado para manter alinhamento quando não selecionado
                Spacer(modifier = Modifier.width(15.dp))
            }
            
            // Texto da categoria
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal, // Mantém peso regular
                color = if (isSelected) {
                    SpeedMenuColors.TextPrimary.copy(alpha = 0.98f) // Texto mais claro quando selecionado
                } else {
                    SpeedMenuColors.TextSecondary.copy(alpha = 0.9f) // Texto secundário com melhor legibilidade
                },
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
    Box(
        modifier = modifier
            .height(18.dp)
            .fillMaxWidth()
            .drawBehind {
                if (this.size.width > 0f && this.size.height > 0f) {
                    val y = this.size.height / 2f
                    val strokeWidth = 2.dp.toPx()
                    val baseAlpha = 0.12f
                    val lineColor = Color.White.copy(alpha = baseAlpha)
                    
                    val width = this.size.width
                    val segment1End = width * 0.12f // 12% inicial
                    val segment2Start = width * 0.12f
                    val segment2End = width * 0.88f // 76% meio (12% + 76% = 88%)
                    val segment3Start = width * 0.88f
                    
                    // Segmento 1 (12% inicial): fade in (alpha 0.00 -> 0.12)
                    if (segment1End > 0f) {
                        for (i in 0..10) {
                            val x1 = (segment1End / 10f) * i
                            val x2 = (segment1End / 10f) * (i + 1)
                            val alpha = (baseAlpha * i / 10f).coerceIn(0f, baseAlpha)
                            this.drawLine(
                                color = Color.White.copy(alpha = alpha),
                                start = Offset(x1, y),
                                end = Offset(x2, y),
                                strokeWidth = strokeWidth
                            )
                        }
                    }
                    
                    // Segmento 2 (76% meio): alpha constante 0.12
                    if (segment2End > segment2Start) {
                        this.drawLine(
                            color = lineColor,
                            start = Offset(segment2Start, y),
                            end = Offset(segment2End, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    
                    // Segmento 3 (12% final): fade out (alpha 0.12 -> 0.00)
                    if (width > segment3Start) {
                        for (i in 0..10) {
                            val x1 = segment3Start + ((width - segment3Start) / 10f) * i
                            val x2 = segment3Start + ((width - segment3Start) / 10f) * (i + 1)
                            val alpha = (baseAlpha * (10f - i) / 10f).coerceIn(0f, baseAlpha)
                            this.drawLine(
                                color = Color.White.copy(alpha = alpha),
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
            color = SpeedMenuColors.PrimaryLight.copy(alpha = 0.85f), // Laranja suave mais visível
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(vertical = 0.dp) // Sem padding extra, o espaçamento é controlado pelos Spacers
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        SpeedMenuColors.BorderSubtle.copy(alpha = 0.1f), // Sutil e discreto
                        SpeedMenuColors.BorderSubtle.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
    )
}

