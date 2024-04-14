package com.hmoa.feature_home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_home.AllPerfumeScreenId
import com.hmoa.feature_home.screen.HomeRoute


fun NavController.navigateToHome() = navigate("${HomeRoute.Home}")
fun NavGraphBuilder.homeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit
) {
    composable(route = "${HomeRoute.Home}") {
        HomeRoute(onPerfumeClick = { onPerfumeClick(it) }, onAllPerfumeClick = {onAllPerfumeClick(it)})
    }
}