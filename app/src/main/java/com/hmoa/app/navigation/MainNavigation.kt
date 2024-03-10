package com.hmoa.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.app.MainRoute
import com.hmoa.feature_authentication.navigation.LOGIN_ROUTE

const val MAIN_ROUTE = "main_route"
fun NavController.navigateToMain() = navigate(MAIN_ROUTE)
fun NavGraphBuilder.mainScreen(
) {
    composable(route = LOGIN_ROUTE) {
        MainRoute()
    }
}
