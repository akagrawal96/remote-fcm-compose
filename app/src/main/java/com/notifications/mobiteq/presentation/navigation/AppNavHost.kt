package com.notifications.mobiteq.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notifications.mobiteq.presentation.screens.login.LoginScreen
import com.notifications.mobiteq.presentation.screens.login.LoginViewModel
import com.notifications.mobiteq.presentation.screens.main.MainScreen
import com.notifications.mobiteq.presentation.screens.main.MainViewModel
import com.notifications.mobiteq.presentation.screens.noconnection.NoConnectionScreen
import com.notifications.mobiteq.presentation.screens.splash.SplashScreen
import com.notifications.mobiteq.presentation.screens.splash.SplashViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SplashScreen(
                uiState = uiState,
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LoginScreen(
                uiState = uiState,
                onGoogleSignInResult = viewModel::onGoogleSignInResult,
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            val viewModel: MainViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MainScreen(
                uiState = uiState,
                onLogout = viewModel::logout,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                onNavigateToNoConnection = {
                    navController.navigate(Screen.NoConnection.route)
                }
            )
        }

        composable(Screen.NoConnection.route) {
            NoConnectionScreen(
                onRetry = {
                    navController.popBackStack()
                }
            )
        }
    }
}
