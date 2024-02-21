package com.example.feature_userinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController : NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.MyPage.name
    ){
        
    }
}