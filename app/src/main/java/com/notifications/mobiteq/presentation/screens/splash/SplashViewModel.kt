package com.notifications.mobiteq.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notifications.mobiteq.core.utils.AppResult
import com.notifications.mobiteq.domain.repository.AuthRepository
import com.notifications.mobiteq.domain.usecase.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isLoading: Boolean = true,
    val navigateToMain: Boolean = false,
    val navigateToLogin: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getAppConfigUseCase: GetAppConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkSessionAndConfig()
    }

    private fun checkSessionAndConfig() = viewModelScope.launch {
        val isLoggedIn = authRepository.currentUser != null

        if (!isLoggedIn) {
            // Not logged in — go to login directly
            _uiState.value = SplashUiState(
                isLoading = false,
                navigateToLogin = true
            )
            return@launch
        }

        // Logged in — fetch config (from cache if available)
        when (val result = getAppConfigUseCase()) {
            is AppResult.Success -> {
                _uiState.value = SplashUiState(
                    isLoading = false,
                    navigateToMain = true
                )
            }
            is AppResult.Error -> {
                _uiState.value = SplashUiState(
                    isLoading = false,
                    error = result.exception.message
                )
            }
            is AppResult.Loading -> Unit
        }
    }
}
