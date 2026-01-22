package com.speedmenu.tablet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.speedmenu.tablet.core.ui.theme.SpeedMenuColors

/**
 * Card do Design System.
 * Container escuro com cantos arredondados e padding confortável.
 */
@Composable
fun SpeedMenuCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    elevated: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (elevated) {
                    Modifier.shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = if (elevated) SpeedMenuColors.SurfaceElevated else SpeedMenuColors.Surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(contentPadding)
    ) {
        content()
    }
}

