package com.hmoa.feature_home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() = navigate(HOME_ROUTE)
fun NavGraphBuilder.homeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId:AllPerfumeScreenId) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(onPerfumeClick = { onPerfumeClick(it) }, onAllPerfumeClick = {onAllPerfumeClick(it)})
    }
}