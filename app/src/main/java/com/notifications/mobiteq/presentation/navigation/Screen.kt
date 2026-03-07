package com.notifications.mobiteq.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object NoConnection : Screen("no_connection")
}