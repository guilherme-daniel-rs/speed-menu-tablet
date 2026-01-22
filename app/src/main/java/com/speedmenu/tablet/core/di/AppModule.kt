package com.speedmenu.tablet.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo principal de injeção de dependências da aplicação.
 * Contém as configurações de DI para componentes singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Módulos de DI serão adicionados aqui conforme necessário
}

