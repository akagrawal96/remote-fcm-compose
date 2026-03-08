package com.notifications.mobiteq.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notifications.mobiteq.core.utils.AppResult
import com.notifications.mobiteq.domain.usecase.GetAppConfigUseCase
import com.notifications.mobiteq.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val navigateToMain: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val getAppConfigUseCase: GetAppConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onGoogleSignInResult(idToken: String) = viewModelScope.launch {
        _uiState.value = LoginUiState(isLoading = true)

        // Step 1: Sign in + save user if new
        when (val authResult = signInWithGoogleUseCase(idToken)) {
            is AppResult.Success -> {
                // Step 2: Fetch and cache AppConfig
                when (val configResult = getAppConfigUseCase()) {
                    is AppResult.Success -> {
                        _uiState.value = LoginUiState(
                            isLoading = false,
                            navigateToMain = true
                        )
                    }
                    is AppResult.Error -> {
                        _uiState.value = LoginUiState(
                            isLoading = false,
                            error = "Failed to load config: ${configResult.exception.message}"
                        )
                    }
                    is AppResult.Loading -> Unit
                }
            }
            is AppResult.Error -> {
                _uiState.value = LoginUiState(
                    isLoading = false,
                    error = authResult.exception.message
                )
            }
            is AppResult.Loading -> Unit
        }
    }
}
