package com.speedmenu.tablet.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * Tela de splash/boas-vindas.
 * Placeholder para desenvolvimento futuro.
 *
 * @param onNavigateToPlaceholder Callback para navegação (temporário)
 */
@Composable
fun SplashScreen(
    onNavigateToPlaceholder: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Splash Screen\n(Placeholder)",
            textAlign = TextAlign.Center
        )
    }
}

