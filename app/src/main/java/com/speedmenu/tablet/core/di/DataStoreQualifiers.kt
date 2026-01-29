package com.speedmenu.tablet.core.di

import javax.inject.Qualifier

/**
 * Qualifier para o DataStore de configuração do app (app_config.preferences_pb).
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppConfigDS

/**
 * Qualifier para o DataStore de sessão do restaurante (restaurant_session.preferences_pb).
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestaurantSessionDS
