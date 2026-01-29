package com.speedmenu.tablet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Classe Application do SpeedMenuTablet.
 * Configura o Hilt para injeção de dependências.
 */
@HiltAndroidApp
class SpeedMenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Inicializa Timber para logging
        // Sempre usa DebugTree (em produção pode ser substituído por CrashReportingTree)
        Timber.plant(Timber.DebugTree())
    }
}

