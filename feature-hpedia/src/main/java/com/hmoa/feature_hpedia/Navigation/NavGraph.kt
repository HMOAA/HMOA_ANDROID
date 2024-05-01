package com.hmoa.feature_hpedia.Navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.hmoa.feature_community.Navigation.nestedCommunityGraph
import com.hmoa.feature_hpedia.Screen.HPediaDescRoute
import com.hmoa.feature_hpedia.Screen.HPediaRoute
import com.hmoa.feature_hpedia.Screen.HPediaSearchRoute

fun NavController.navigateToHPedia() = navigate(HPediaRoute.HPediaGraphRoute.name)

fun NavController.navigateToHPediaHomeRoute() = navigate(HPediaRoute.HPedia.name)

fun NavController.navigateToHPediaDescRoute(id: Int, type: String) =
    navigate("${HPediaRoute.HPediaDescRoute.name}/${id}/${type}")

fun NavController.navigateToHPediaSearchRoute(type: String) = navigate("${HPediaRoute.HPediaSearchRoute.name}/${type}")

fun NavGraphBuilder.nestedHPediaGraph(
    onNavBack: () -> Unit,
    onNavCommunityPage: () -> Unit,
    onNavCommunityPost: (String) -> Unit,
    onNavCommunityEdit: (Int) -> Unit,
    onNavCommunityDesc: (Int) -> Unit,
    onNavCommunityGraph: () -> Unit,
    onNavCommunitySearch: () -> Unit,
    onNavCommunityCommentEdit: (Int) -> Unit,
    onNavHPediaSearch: (String) -> Unit,
    onNavHPediaDesc: (Int, String) -> Unit,
    onNavLogin: () -> Unit,
) {
    navigation(
        startDestination = HPediaRoute.HPedia.name,
        route = HPediaRoute.HPediaGraphRoute.name
    ) {
        this.nestedCommunityGraph(
            onNavBack = onNavBack,
            onNavCommunityPage = onNavCommunityPage,
            onNavCommunityPost = onNavCommunityPost,
            onNavCommunityEdit = onNavCommunityEdit,
            onNavCommunityDescription = onNavCommunityDesc,
            onNavCommunitySearch = onNavCommunitySearch,
            onNavCommunityCommentEdit = onNavCommunityCommentEdit,
            onErrorHandleLoginAgain = onNavLogin
        )

        composable("${HPediaRoute.HPediaSearchRoute.name}/{type}") {
            val type = it.arguments?.getString("type")
            HPediaSearchRoute(
                type = type,
                onNavBack = onNavBack,
                onNavHPediaDesc = onNavHPediaDesc
            )
        }

        composable(
            route = "${HPediaRoute.HPediaDescRoute.name}/{id}/{type}",
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

        composable(HPediaRoute.HPedia.name) {
            HPediaRoute(
                onNavBack = onNavBack,
                onNavHPediaSearch = onNavHPediaSearch,
                onNavCommunityDesc = onNavCommunityDesc,
                onNavCommunityGraph = onNavCommunityGraph,
                onNavLogin = onNavLogin
            )
        }
    }
}