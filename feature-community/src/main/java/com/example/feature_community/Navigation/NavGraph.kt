package com.example.feature_community.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.feature_community.CommunityDescriptionRoute
import com.example.feature_community.CommunityEditRoute
import com.example.feature_community.CommunityPageRoute
import com.example.feature_community.CommunityPostRoute

//게시글 기본 화면
fun NavController.navigateToCommunityRoute() = navigate(Route.CommunityPageRoute.name)

//게시글 등록 화면
fun NavController.navigateToCommunityPostRoute() = navigate(Route.CommunityPostRoute.name)

//게시글 수정 화면
fun NavController.navigateToCommunityEditRoute() = navigate(Route.CommunityEditRoute.name)

//게시글 상세 화면
fun NavController.navigateToCommunityDescriptionRoute() = navigate(Route.CommunityDescriptionRoute.name)

fun NavGraphBuilder.nestedCommunityGraph(
    onNavBack : () -> Unit,
    onNavCommunityPost : () -> Unit,
    onNavCommunityEdit : () -> Unit,
    onNavCommunityDescription : () -> Unit
){
    navigation(
        startDestination = Route.CommunityPageRoute.name,
        route = "community_graph"
    ){
        composable(route = Route.CommunityPageRoute.name){
            CommunityPageRoute(
                onNavBack = onNavBack,
                onNavCommunityDescription = onNavCommunityDescription
            )
        }
        composable(route = "${Route.CommunityPostRoute.name}/{type}") {
            val type = it.arguments?.getString("type")

            CommunityPostRoute(
                onNavBack = onNavBack,
                category = type
            )
        }
        composable(route = "${Route.CommunityEditRoute.name}/{id}"){
            val id = it.arguments?.getInt("id")

            CommunityEditRoute(
                id = id,
                onNavBack = onNavBack
            )
        }
        composable(route = "${Route.CommunityDescriptionRoute.name}/{id}") {
            val id = it.arguments?.getInt("id")

            CommunityDescriptionRoute (
                id = id,
                onNavBack = onNavBack
            )
        }
    }
}