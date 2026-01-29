package com.speedmenu.tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speedmenu.tablet.data.local.RestaurantSession
import com.speedmenu.tablet.domain.model.AppConfig
import com.speedmenu.tablet.domain.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel para gerenciar AppConfig.
 * Expõe o AppConfig atual e o ColorScheme resolvido baseado no dark mode do sistema.
 */
@HiltViewModel
class AppConfigViewModel @Inject constructor(
    private val repository: AppConfigRepository,
    private val restaurantSession: RestaurantSession
) : ViewModel() {
    
    private val _appConfig = MutableStateFlow<AppConfig?>(null)
    val appConfig: StateFlow<AppConfig?> = _appConfig.asStateFlow()
    
    private val _restaurantId = MutableStateFlow<String>("default")
    val restaurantId: StateFlow<String> = _restaurantId.asStateFlow()
    
    private var configLoadJob: Job? = null
    
    init {
        // Inicializa com último restaurante usado
        viewModelScope.launch {
            restaurantSession.initializeWithLastRestaurant()
        }
        
        // Observa restaurantId e atualiza config quando mudar
        viewModelScope.launch {
            restaurantSession.observeRestaurantId()
                .collect { id ->
                    Timber.d("AppConfigViewModel: restaurantId changed to $id")
                    _restaurantId.value = id
                    loadConfig(id)
                }
        }
    }
    
    /**
     * Carrega a configuração para o restaurante especificado.
     * Cancela a coleta anterior se houver uma em andamento.
     */
    private fun loadConfig(restaurantId: String) {
        // Cancela coleta anterior se existir
        configLoadJob?.cancel()
        
        // Inicia nova coleta
        configLoadJob = viewModelScope.launch {
            repository.observeConfig(restaurantId)
                .collect { config ->
                    Timber.d("AppConfigViewModel: received config version=${config.version} for restaurant=$restaurantId")
                    _appConfig.value = config
                }
        }
    }
    
    /**
     * Atualiza o restaurantId.
     */
    fun setRestaurantId(restaurantId: String) {
        viewModelScope.launch {
            restaurantSession.setRestaurantId(restaurantId)
        }
    }
    
    /**
     * Força refresh da configuração.
     */
    fun refreshConfig() {
        viewModelScope.launch {
            repository.refreshConfig(_restaurantId.value)
        }
    }
}

