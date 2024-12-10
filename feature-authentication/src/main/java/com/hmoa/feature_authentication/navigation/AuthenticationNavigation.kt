package com.hmoa.feature_authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hmoa.core_domain.entity.navigation.AuthenticationRoute
import com.hmoa.feature_authentication.LoginRoute
import com.hmoa.feature_authentication.PickNicknameRoute
import com.hmoa.feature_authentication.PickPersonalInfoRoute
import com.hmoa.feature_authentication.SignupRoute

fun NavController.navigateToLogin() = navigate("${AuthenticationRoute.Login}")
fun NavController.navigateToSignup(loginProvider: String) =
    navigate("${AuthenticationRoute.Signup}/${loginProvider}")

fun NavController.navigateToPickNickname(loginProvider: String) =
    navigate("${AuthenticationRoute.PickNickname}/${loginProvider}")

fun NavController.navigateToPickPersonalInfo(loginProvider: String) =
    navigate("${AuthenticationRoute.PickPersonalInfo}/${loginProvider}")

fun NavGraphBuilder.loginScreen(
    onSignupClick: (loginProvider: String) -> Unit,
    onHomeClick: () -> Unit,
) {
    composable(
        route = "${AuthenticationRoute.Login}"
    ) {
        LoginRoute(
            onSignup = { onSignupClick(it) },
            onHome = { onHomeClick() })
    }
}

fun NavGraphBuilder.signupScreen(
    onPickNicknameClick: (loginProvider: String) -> Unit,
) {
    composable(
        "${AuthenticationRoute.Signup}/{loginProvider}",
        arguments = listOf(navArgument("loginProvider") { type = NavType.StringType })
    ) {
        val loginProvider = it.arguments?.getString("loginProvider")
        SignupRoute(onPickNicknameClick = { onPickNicknameClick(loginProvider!!) })
    }
}

fun NavGraphBuilder.pickNicknameScreen(
    onPickPersonalInfoClick: (loginProvider: String) -> Unit,
    onSignupClick: (loginProvider: String) -> Unit
) {
    composable(
        route = "${AuthenticationRoute.PickNickname}/{loginProvider}",
        arguments = listOf(navArgument("loginProvider") { type = NavType.StringType })
    ) {
        val loginProvider = it.arguments?.getString("loginProvider")
        PickNicknameRoute(
            onPickPersonalInfoClick = { onPickPersonalInfoClick(loginProvider!!) },
            onSignupClick = { onSignupClick(loginProvider!!) }
        )
    }
}


fun NavGraphBuilder.pickPersonalInfoScreen(
    onHomeClick: () -> Unit,
    onPickNicknameClick: (loginProvider: String) -> Unit,
) {
    composable(
        route = "${AuthenticationRoute.PickPersonalInfo}/{loginProvider}",
        arguments = listOf(navArgument("loginProvider") { type = NavType.StringType })
    ) {
        val loginProvider = it.arguments?.getString("loginProvider")
        PickPersonalInfoRoute(
            onHomeClick = { onHomeClick() },
            onPickNicknameClick = { onPickNicknameClick(loginProvider!!) }, loginProvider = loginProvider!!
        )
    }
}
