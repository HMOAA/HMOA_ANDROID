package com.hyangmoa.feature_home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hyangmoa.feature_home.AllPerfumeScreenId
import com.hyangmoa.feature_home.screen.AllPerfumeRoute
import com.hyangmoa.feature_home.screen.HomeRoute
import com.hyangmoa.feature_home.screen.PerfumeSearchRoute


fun NavController.navigateToHome() = navigate("${HomeRoute.Home}")
fun NavController.navigateToPerfumeSearch() = navigate("${HomeRoute.PerfumeSearch}")
fun NavController.navigateToAllPerfume(screenId: AllPerfumeScreenId) = navigate("${HomeRoute.AllPerfume}/${screenId}")

fun NavGraphBuilder.homeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit
) {
    composable(route = "${HomeRoute.Home}") {
        HomeRoute(onPerfumeClick = { onPerfumeClick(it) }, onAllPerfumeClick = { onAllPerfumeClick(it) })
    }
}

fun NavGraphBuilder.perfumeSearchScreen(
    onBackClick: () -> Unit
) {
    composable(route = "${HomeRoute.PerfumeSearch}") {
        PerfumeSearchRoute(
            onBackClick = { onBackClick() })
    }
}

fun NavGraphBuilder.allPerfumeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onNavBack: () -> Unit,
    onNavLogin: () -> Unit
) {
    composable(
        route = "${HomeRoute.AllPerfume}/{screenId}",
        arguments = listOf(navArgument("screenId") { type = NavType.StringType })
    ) {
        val screenId = it.arguments?.getString("screenId")
        AllPerfumeRoute(
            onPerfumeClick = { onPerfumeClick(it) },
            onNavBack = onNavBack,
            screenId = screenId,
            onNavLogin = onNavLogin
        )
    }
}