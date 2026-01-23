package com.speedmenu.tablet.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Seção accordion colapsável (fechada por padrão) com resumo quando fechado.
 */
@Composable
fun AccordionSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    summary: String? = null, // Resumo mostrado quando fechado
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SpeedMenuColors.Surface.copy(alpha = 0.15f), // Ainda mais discreto
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp) // Padding reduzido
    ) {
        // Cabeçalho clicável
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandedChange(!expanded) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp) // Espaçamento reduzido
                ) {
                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = SpeedMenuColors.TextSecondary.copy(alpha = 0.6f), // Mais discreto
                            modifier = Modifier.size(16.dp) // Ícone menor
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SpeedMenuColors.TextPrimary,
                        fontSize = 14.sp // Label menor
                    )
                }
                
                // Resumo quando fechado
                if (!expanded && summary != null) {
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = SpeedMenuColors.TextTertiary,
                        fontSize = 12.sp, // Label menor
                        modifier = Modifier.padding(top = 3.dp, start = if (icon != null) 22.dp else 0.dp)
                    )
                }
            }
            
            // Divisor fino + chevron menor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Divisor fino vertical
                Box(
                    modifier = Modifier
                        .width(0.5.dp)
                        .height(16.dp)
                        .background(SpeedMenuColors.BorderSubtle.copy(alpha = 0.3f))
                )
                
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Recolher" else "Expandir",
                    tint = SpeedMenuColors.TextTertiary.copy(alpha = 0.5f), // Mais discreto
                    modifier = Modifier.size(18.dp) // Chevron menor
                )
            }
        }
        
        // Conteúdo expandível com animação spring suave
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = spring(
                    dampingRatio = 0.85f,
                    stiffness = 300f
                )
            ),
            exit = shrinkVertically(
                animationSpec = spring(
                    dampingRatio = 0.85f,
                    stiffness = 300f
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * TextField minimalista para observações.
 * Uma linha por padrão, expande até 3 linhas.
 */
@Composable
fun MinimalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp, max = 80.dp) // 1 linha por padrão, expande até 3 linhas
            .background(
                color = SpeedMenuColors.Surface.copy(alpha = 0.15f), // Mais sutil
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = SpeedMenuColors.TextPrimary,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        maxLines = 3,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SpeedMenuColors.TextTertiary,
                    fontSize = 14.sp
                )
            }
            innerTextField()
        }
    )
}

