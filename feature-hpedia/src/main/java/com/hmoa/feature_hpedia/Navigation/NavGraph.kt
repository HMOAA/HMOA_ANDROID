package com.hmoa.feature_hpedia.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.hmoa.feature_hpedia.Screen.HPediaDescRoute
import com.hmoa.feature_hpedia.Screen.HPediaRoute
import com.hmoa.feature_hpedia.Screen.HPediaSearchRoute

fun NavController.navigateToHPedia() = navigate(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaGraphRoute.name)

fun NavController.navigateToHPediaHomeRoute() = navigate(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name)

fun NavController.navigateToHPediaDescRoute(id: Int, type: String) =
    navigate("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaDescRoute.name}/${id}/${type}")

fun NavController.navigateToHPediaSearchRoute(type: String) = navigate("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaSearchRoute.name}/${type}")

fun NavGraphBuilder.nestedHPediaGraph(
    onNavBack: () -> Unit,
    onNavCommunityDesc: (Int) -> Unit,
    onNavCommunityGraph: () -> Unit,
    onNavHPediaSearch: (String) -> Unit,
    onNavHPediaDesc: (Int, String) -> Unit,
    onNavLogin: () -> Unit,
    onNavHome : () -> Unit,
) {
    navigation(
        startDestination = com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name,
        route = com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaGraphRoute.name,
    ) {
        composable("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaSearchRoute.name}/{type}") {
            val type = it.arguments?.getString("type")
            HPediaSearchRoute(
                type = type,
                onNavBack = onNavBack,
                onNavHPediaDesc = onNavHPediaDesc
            )
        }

        composable(
            route = "${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaDescRoute.name}/{id}/{type}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("type") { type = NavType.StringType }
            )
        ) {

            val id = it.arguments?.getInt("id")
            val type = it.arguments?.getString("type")
            HPediaDescRoute(
                id = id,
                type = type,
                onNavBack = onNavBack,
            )
        }

        composable(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name) {
            HPediaRoute(
                onNavHPediaSearch = onNavHPediaSearch,
                onNavCommunityDesc = onNavCommunityDesc,
                onNavCommunityGraph = onNavCommunityGraph,
                onNavLogin = onNavLogin,
                onNavHome = onNavHome,
            )
        }
    }
}