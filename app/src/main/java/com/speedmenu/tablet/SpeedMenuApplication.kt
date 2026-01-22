package com.speedmenu.tablet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application do SpeedMenuTablet.
 * Configura o Hilt para injeção de dependências.
 */
@HiltAndroidApp
class SpeedMenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: Inicializações adicionais podem ser feitas aqui
    }
}

