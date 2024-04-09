package com.hmoa.feature_authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_authentication.LoginRoute
import com.hmoa.feature_authentication.PickNicknameRoute
import com.hmoa.feature_authentication.PickPersonalInfoRoute
import com.hmoa.feature_authentication.SignupRoute

fun NavController.navigateToLogin() = navigate("${AuthenticationRoute.Login}")
fun NavController.navigateToSignup() = navigate("${AuthenticationRoute.Signup}")
fun NavController.navigateToPickNickname() = navigate("${AuthenticationRoute.PickNickname}")
fun NavController.navigateToPickPersonalInfo() = navigate("${AuthenticationRoute.PickPersonalInfo}")
fun NavGraphBuilder.loginScreen(
    onSignupClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    composable(route = "${AuthenticationRoute.Login}") {
        LoginRoute(onSignupClick, onHomeClick)
    }
}

fun NavGraphBuilder.signupScreen(
    onPickNicknameClick: () -> Unit,
) {
    composable(route = "${AuthenticationRoute.Signup}") {
        SignupRoute(onPickNicknameClick)
    }
}

fun NavGraphBuilder.pickNicknameScreen(
    onPickPersonalInfoClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    composable(route = "${AuthenticationRoute.PickNickname}") {
        PickNicknameRoute(onPickPersonalInfoClick, onSignupClick)
    }
}


fun NavGraphBuilder.pickPersonalInfoScreen(
    onHomeClick: () -> Unit,
    onPickNicknameClick: () -> Unit,
) {
    composable(route = "${AuthenticationRoute.PickPersonalInfo}") {
        PickPersonalInfoRoute(onHomeClick, onPickNicknameClick)
    }
}
