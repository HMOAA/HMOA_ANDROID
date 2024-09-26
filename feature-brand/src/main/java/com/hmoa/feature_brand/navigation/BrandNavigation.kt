package com.hmoa.feature_brand.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hmoa.core_domain.entity.navigation.BrandRoute

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
        com.hmoa.feature_brand.screen.BrandRoute(
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
        com.hmoa.feature_brand.screen.BrandSearchRoute(
            onBackClick = { onBackClick() },
            onBrandClick = { onBrandClick(it) }
        )
    }
}