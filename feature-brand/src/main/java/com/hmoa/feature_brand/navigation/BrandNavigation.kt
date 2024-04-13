package com.hmoa.feature_brand.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateToBrand(brandId: Int) = navigate("${BrandRoute.Brand.name}/$brandId")

fun NavGraphBuilder.brandScreen(
    onBackClck: () -> Unit,
    onHomeClick: () -> Unit
) {
    composable(
        route = "${BrandRoute.Brand.name}/{brandId}",
        arguments = listOf(navArgument("brandId") { type = NavType.IntType })
    ) {
        val brandId = it.arguments?.getString("brandId")
        com.hmoa.feature_brand.screen.BrandRoute(
            brandId = brandId?.toInt(),
            onBackClick = { onBackClck() },
            onHomeClick = { onHomeClick() }
        )
    }
}