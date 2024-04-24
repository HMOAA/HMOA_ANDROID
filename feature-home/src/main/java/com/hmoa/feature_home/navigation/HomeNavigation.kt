package com.hmoa.feature_home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_home.AllPerfumeScreenId
import com.hmoa.feature_home.screen.HomeRoute
import com.hmoa.feature_home.screen.PerfumeSearchRoute


fun NavController.navigateToHome() = navigate("${HomeRoute.Home}")
fun NavController.navigateToPerfumeSearch() = navigate("${HomeRoute.PerfumeSearch}")
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