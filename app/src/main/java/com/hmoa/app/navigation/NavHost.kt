package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hmoa.feature_authentication.LoginScreen
import com.hmoa.feature_authentication.SignupScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController,
            )
        }
        composable(route = Screen.SignupScreen.route) {
            SignupScreen(navController = navController)
        }
    }
}