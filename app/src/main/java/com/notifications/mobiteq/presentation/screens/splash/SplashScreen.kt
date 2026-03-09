package com.notifications.mobiteq.presentation.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(
    uiState: SplashUiState,
    onNavigateToMain: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Handle navigation based on state
    LaunchedEffect(uiState) {
        when {
            uiState.navigateToMain -> onNavigateToMain()
            uiState.navigateToLogin -> onNavigateToLogin()
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (uiState.isLoading) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "splash_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        // App name / logo
        Text(
            text = "Mobiteq",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.alpha(alpha)
        )

        // Loading indicator at bottom
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(32.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        }

        // Error state
        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
