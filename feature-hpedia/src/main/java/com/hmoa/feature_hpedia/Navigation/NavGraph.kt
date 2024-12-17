package com.hmoa.feature_hpedia.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.feature_hpedia.Screen.HPediaDescRoute
import com.hmoa.feature_hpedia.Screen.HPediaRoute
import com.hmoa.feature_hpedia.Screen.HPediaSearchRoute

fun NavController.navigateToHPedia() = navigate(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaGraphRoute.name)

fun NavController.navigateToHPediaHomeRoute() = navigate(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name)

fun NavController.navigateToHPediaDescRoute(id: Int, type: String) =
    navigate("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaDescRoute.name}/${id}/${type}")

fun NavController.navigateToHPediaSearchRoute(type: String) = navigate("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaSearchRoute.name}/${type}")

fun NavGraphBuilder.nestedHPediaGraph(
    navBack: () -> Unit,
    navCommunityDesc: (befRoute: CommunityRoute, community: Int) -> Unit,
    navCommunityGraph: () -> Unit,
    navHPediaSearch: (String) -> Unit,
    navHPediaDesc: (Int, String) -> Unit,
    navLogin: () -> Unit,
    navHome : () -> Unit,
) {
    navigation(
        startDestination = com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name,
        route = com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaGraphRoute.name,
    ) {
        composable("${com.hmoa.core_domain.entity.navigation.HPediaRoute.HPediaSearchRoute.name}/{type}") {
            val type = it.arguments?.getString("type")
            HPediaSearchRoute(
                type = type,
                navBack = navBack,
                navHPediaDesc = navHPediaDesc
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
                navBack = navBack,
            )
        }

        composable(com.hmoa.core_domain.entity.navigation.HPediaRoute.HPedia.name) {
            HPediaRoute(
                navHPediaSearch = navHPediaSearch,
                navCommunityDesc = navCommunityDesc,
                navCommunityGraph = navCommunityGraph,
                navLogin = navLogin,
                navHome = navHome,
            )
        }
    }
}