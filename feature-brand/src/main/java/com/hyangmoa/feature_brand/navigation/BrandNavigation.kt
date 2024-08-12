package com.hyangmoa.feature_brand.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateToBrand(brandId: String) = navigate("${BrandRoute.Brand.name}/${brandId}")
fun NavController.navigateToBrandSearch() = navigate("${BrandRoute.BrandSearch.name}")

fun NavGraphBuilder.brandScreen(
    onBackClck: () -> Unit,
    onHomeClick: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    composable(
        route = "${BrandRoute.Brand.name}/{brandId}",
        arguments = listOf(navArgument("brandId") { type = NavType.IntType })
    ) {
        val brandId = it.arguments?.getInt("brandId")
        com.hyangmoa.feature_brand.screen.BrandRoute(
            brandId = brandId,
            onBackClick = { onBackClck() },
            onHomeClick = { onHomeClick() },
            onPerfumeClick = { onPerfumeClick(it) }
        )
    }
}

fun NavGraphBuilder.brandSearchScreen(
    onBackClick: () -> Unit,
    onBrandClick: (brandId: Int) -> Unit,
) {
    composable(
        route = "${BrandRoute.BrandSearch.name}",
    ) {
        com.hyangmoa.feature_brand.screen.BrandSearchRoute(
            onBackClick = { onBackClick() },
            onBrandClick = { onBrandClick(it) }
        )
    }
}