package com.example.feature_userinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController

//test
@Composable
fun userInfo(){

    val navController = rememberNavController()

    /** viewModel 초기화 */

    /** viewModel에서 인증 여부에 관한 가져옴 */
    val isAuthenticated = false

    UserInfoNavGraph(
        navController,
        isAuthenticated,
        navLoginPage = {}
    )
}