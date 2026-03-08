package com.notifications.mobiteq.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notifications.mobiteq.domain.model.AppConfig
import com.notifications.mobiteq.domain.repository.AppConfigRepository
import com.notifications.mobiteq.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val appConfig: AppConfig? = null,
    val navigateToLogin: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        // Load from cache instantly — no network call needed here
        loadCachedConfig()
    }

    private fun loadCachedConfig() {
        val config = appConfigRepository.getCachedAppConfig()
        _uiState.value = MainUiState(appConfig = config)
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase()
        _uiState.value = MainUiState(navigateToLogin = true)
    }
}
