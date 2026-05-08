package com.luminor.luminortestproject.ui.navigation

sealed class Screen(val route: String) {
    data object Auth : Screen("auth")
    data object Dashboard : Screen("dashboard")
}
