package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hmoa.feature_authentication.navigation.*

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {

        /** authentication 모듈 */
        loginScreen(onSignupClick = navController::navigateToSignup)
        signupScreen(onPickNicknameClick = navController::navigateToPickNickname)
        pickNicknameScreen(
            onPickPersonalInfoClick = navController::navigateToPickPersonalInfo,
            onSignupClick = navController::navigateToSignup
        )

    }
}