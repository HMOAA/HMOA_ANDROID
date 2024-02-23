package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            //TODO("어떻게 액티비티의 context를 전달할 것인가. <- nowinandroid의 NiaAppState를 유심히 보면 될 것 같기도?")
            //LoginScreen(navController = navController, context = )
        }
    }
}