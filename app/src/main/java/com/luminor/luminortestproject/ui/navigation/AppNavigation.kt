package com.luminor.luminortestproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luminor.luminortestproject.data.local.AppDatabase
import com.luminor.luminortestproject.data.local.SessionManager
import com.luminor.luminortestproject.data.repository.UserRepository
import com.luminor.luminortestproject.ui.screens.auth.AuthScreen
import com.luminor.luminortestproject.ui.screens.auth.AuthViewModel
import com.luminor.luminortestproject.ui.screens.dashboard.DashboardScreen
import com.luminor.luminortestproject.ui.screens.dashboard.DashboardViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val userRepository = remember {
        val dao = AppDatabase.getDatabase(context).userDao()
        val sessionManager = SessionManager(context)
        UserRepository(dao, sessionManager)
    }

    val loggedInEmail by userRepository.loggedInEmail.collectAsState(initial = null)
    val startDestination = if (loggedInEmail != null) Screen.Dashboard.route else Screen.Auth.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                viewModel = viewModel(factory = AuthViewModel.Factory(userRepository)),
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel(factory = DashboardViewModel.Factory(userRepository)),
                onLogout = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
