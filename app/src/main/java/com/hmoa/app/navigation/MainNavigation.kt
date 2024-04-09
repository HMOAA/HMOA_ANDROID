package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hmoa.app.MainRoute

const val MAIN_ROUTE = "main_route"
fun NavController.navigateToMain() = navigate(MAIN_ROUTE)
fun NavGraphBuilder.mainScreen(
    onNavCommunity: () -> Unit,
    navController: NavHostController
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            onNavCommunity = onNavCommunity,
            navController = navController
        )
    }
}

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
//    NavHost(navController = navController, startDestination = HOME_ROUTE){
//
//    }

}
