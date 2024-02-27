package com.hmoa.feature_authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_authentication.LoginRoute
import com.hmoa.feature_authentication.PickNicknameRoute
import com.hmoa.feature_authentication.SignupRoute

private const val AUTHENTICATION_GRAPH = "authentication_graph"
const val LOGIN_ROUTE = "login_route"
const val SIGNUP_ROUTE = "signup_route"
const val PICKNICKNAME_ROUTE = "pickNickname_route"
const val PICKPERSONALINFO_ROUTE = "pickPersonalInto_route"
fun NavController.navigateToLogin() = navigate(LOGIN_ROUTE)
fun NavController.navigateToSignup() = navigate(SIGNUP_ROUTE)
fun NavController.navigateToPickNickname() = navigate(PICKNICKNAME_ROUTE)
fun NavController.navigateToPickPersonalInfo() = navigate(PICKPERSONALINFO_ROUTE)
fun NavGraphBuilder.loginScreen(
    onSignupClick: () -> Unit
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(onSignupClick)
    }
}

fun NavGraphBuilder.signupScreen(
    onPickNicknameClick: () -> Unit
) {
    composable(route = SIGNUP_ROUTE) {
        SignupRoute(onPickNicknameClick)
    }
}

fun NavGraphBuilder.pickNicknameScreen(
    onPickPersonalInfoClick: () -> Unit,
) {
    composable(route = PICKNICKNAME_ROUTE) {
        PickNicknameRoute(onPickPersonalInfoClick)
    }
}


fun NavGraphBuilder.pickPersonalInfoScreen(
    onHomeClick: () -> Unit,
) {
    composable(route = PICKPERSONALINFO_ROUTE) {
        PickNicknameRoute(onHomeClick)
    }
}
