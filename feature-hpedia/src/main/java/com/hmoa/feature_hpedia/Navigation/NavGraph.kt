package com.hmoa.feature_hpedia.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hmoa.feature_community.Navigation.nestedCommunityGraph
import com.hmoa.feature_hpedia.Screen.HPediaDescRoute
import com.hmoa.feature_hpedia.Screen.HPediaRoute
import com.hmoa.feature_hpedia.Screen.HPediaSearchRoute

fun NavController.navigateToHPedia() = navigate(HPediaRoute.HPediaGraphRoute.name)

fun NavController.navigateToHPediaHome() = navigate(HPediaRoute.HPeidaRoute.name)

fun NavController.navigateToHPediaDesc(id : Int) = navigate("${HPediaRoute.HPediaDescRoute.name}/${id}")

fun NavController.navigateToHPediaSearch(type : String) = navigate("${HPediaRoute.HPediaSearchRoute.name}/${type}")

fun NavGraphBuilder.nestedHPediaGraph(
    onNavBack : () -> Unit,
    onNavCommunityPage : () -> Unit,
    onNavCommunityPost : (String) -> Unit,
    onNavCommunityEdit : (Int) -> Unit,
    onNavCommunityDesc : (Int) -> Unit,
    onNavCommunityGraph : () -> Unit,
    onNavHPediaSearch : (String) -> Unit,
    onNavHPediaDesc : (Int) -> Unit,
){
    navigation(
        startDestination = HPediaRoute.HPeidaRoute.name,
        route = HPediaRoute.HPediaGraphRoute.name
    ){
        this.nestedCommunityGraph(
            onNavBack = onNavBack,
            onNavCommunityPage = onNavCommunityPage,
            onNavCommunityPost = onNavCommunityPost,
            onNavCommunityEdit = onNavCommunityEdit,
            onNavCommunityDescription = onNavCommunityDesc
        )

        composable("${HPediaRoute.HPediaSearchRoute.name}/{type}"){
            val type = it.arguments?.getString("type")
            HPediaSearchRoute(
                type = type,
                onNavBack = onNavBack,
                onNavHPediaDesc = onNavHPediaDesc
            )
        }

        composable("${HPediaRoute.HPediaDescRoute.name}/{id}"){
            val id = it.arguments?.getString("id")?.toInt()
            HPediaDescRoute(
                id = id,
                onNavBack = onNavBack,
            )
        }

        composable(HPediaRoute.HPeidaRoute.name){
            HPediaRoute(
                onNavBack = onNavBack,
                onNavHPediaSearch = onNavHPediaSearch,
                onNavCommunityDesc = onNavCommunityDesc,
                onNavCommunityGraph = onNavCommunityGraph
            )
        }
    }
}