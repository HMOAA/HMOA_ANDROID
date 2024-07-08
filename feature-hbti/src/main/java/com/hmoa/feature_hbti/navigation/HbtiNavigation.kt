package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_hbti.screen.HbtiRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")

fun NavGraphBuilder.hbtiScreen() {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(onAfterOrderClick = {}, onHbtiTestClick = {})
    }
}