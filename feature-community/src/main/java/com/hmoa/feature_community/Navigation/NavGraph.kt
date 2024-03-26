package com.hmoa.feature_community.Navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.hmoa.feature_community.CommunityDescriptionRoute
import com.hmoa.feature_community.CommunityEditRoute
import com.hmoa.feature_community.CommunityHome
import com.hmoa.feature_community.CommunityHomeRoute
import com.hmoa.feature_community.CommunityPageRoute
import com.hmoa.feature_community.CommunityPostRoute
import com.hmoa.feature_community.Navigation.Route

//게시글 기본 화면
fun NavController.navigateToCommunityRoute() = navigate(Route.CommunityGraphRoute.name)

//홈 화면
fun NavController.navigateToCommunityPage() = navigate(Route.CommunityPageRoute.name)

//카테고리 별 화면
fun NavController.navigateToCommunityHome() = navigate(Route.CommunityHomeRoute.name)

//게시글 등록 화면
fun NavController.navigateToCommunityPostRoute(type : String) = navigate("${Route.CommunityPostRoute.name}/${type}")

//게시글 수정 화면
fun NavController.navigateToCommunityEditRoute(id : Int) = navigate("${Route.CommunityEditRoute.name}/${id}")

//게시글 상세 화면
fun NavController.navigateToCommunityDescriptionRoute(id : Int) = navigate("${Route.CommunityDescriptionRoute.name}/${id}")

fun NavGraphBuilder.nestedCommunityGraph(
    onNavHome : () -> Unit,
    onNavHPedia : () -> Unit,
    onNavLike : () -> Unit,
    onNavMyPage : () -> Unit,
    onNavBack : () -> Unit,
    onNavCommunityPage : () -> Unit,
    onNavCommunityPost : (String) -> Unit,
    onNavCommunityEdit : (Int) -> Unit,
    onNavCommunityDescription : (Int) -> Unit
){
    navigation(
        startDestination = Route.CommunityHomeRoute.name,
        route = Route.CommunityGraphRoute.name
    ){
        composable(Route.CommunityHomeRoute.name) {
            CommunityHomeRoute(
                onNavCommunityDescription = onNavCommunityDescription,
                onNavCommunityByCategory = onNavCommunityPage,
                onNavHome = onNavHome,
                onNavHPedia = onNavHPedia,
                onNavLike = onNavLike,
                onNavMyPage = onNavMyPage
            )
        }
        composable(route = Route.CommunityPageRoute.name){
            CommunityPageRoute(
                onNavBack = onNavBack,
                onNavCommunityDescription = onNavCommunityDescription,
                onNavPost = onNavCommunityPost,
                onNavHome = onNavHome,
                onNavHPedia = onNavHPedia,
                onNavLike = onNavLike,
                onNavMyPage = onNavMyPage
            )
        }
        composable(route = "${Route.CommunityPostRoute.name}/{type}") {
            val type = it.arguments?.getString("type")

            CommunityPostRoute(
                onNavBack = onNavBack,
                _category = type
            )
        }
        composable(route = "${Route.CommunityEditRoute.name}/{id}"){
            val id = it.arguments?.getString("id") ?: "0"

            CommunityEditRoute(
                id = id.toInt(),
                onNavBack = onNavBack
            )
        }
        composable(route = "${Route.CommunityDescriptionRoute.name}/{id}") {
            val id = it.arguments?.getString("id")?: "0"

            CommunityDescriptionRoute (
                _id = id.toInt(),
                onNavCommunityEdit = onNavCommunityEdit,
                onNavBack = onNavBack
            )
        }
    }
}