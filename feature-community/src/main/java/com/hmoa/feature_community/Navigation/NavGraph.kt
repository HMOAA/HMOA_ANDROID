package com.hmoa.feature_community.Navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hmoa.feature_community.Screen.CommunityCommentEditRoute
import com.hmoa.feature_community.Screen.CommunityDescriptionRoute
import com.hmoa.feature_community.Screen.CommunityEditRoute
import com.hmoa.feature_community.Screen.CommunityHomeRoute
import com.hmoa.feature_community.Screen.CommunityPageRoute
import com.hmoa.feature_community.Screen.CommunityPostRoute
import com.hmoa.feature_community.Screen.CommunitySearchRoute

//게시글 기본 화면
fun NavController.navigateToCommunityRoute() = navigate(CommunityRoute.CommunityGraphRoute.name)

//홈 화면
fun NavController.navigateToCommunityPage() = navigate(CommunityRoute.CommunityPageRoute.name)

//카테고리 별 화면
fun NavController.navigateToCommunityHome() = navigate(CommunityRoute.CommunityHomeRoute.name)

//게시글 등록 화면
fun NavController.navigateToCommunityPostRoute(type : String) = navigate("${CommunityRoute.CommunityPostRoute.name}/${type}")

//게시글 수정 화면
fun NavController.navigateToCommunityEditRoute(id : Int) = navigate("${CommunityRoute.CommunityEditRoute.name}/${id}")

//게시글 상세 화면
fun NavController.navigateToCommunityDescriptionRoute(id : Int) = navigate("${CommunityRoute.CommunityDescriptionRoute.name}/${id}"){
    popUpTo("${CommunityRoute.CommunityDescriptionRoute.name}/{id}"){inclusive = true}
}

//게시글 검색 화면
fun NavController.navigateToCommunitySearchRoute() = navigate(CommunityRoute.CommunitySearchRoute.name)

//댓글 수정 화면
fun NavController.navigateToCommunityCommentEditRoute(commentId : Int) = navigate("${CommunityRoute.CommunityCommentEditRoute.name}/${commentId}")

fun NavGraphBuilder.nestedCommunityGraph(
    onNavBack : () -> Unit,
    onNavCommunityPage : () -> Unit,
    onNavCommunityPost : (String) -> Unit,
    onNavCommunityEdit : (Int) -> Unit,
    onNavCommunityDescription : (Int) -> Unit,
    onNavCommunitySearch : () -> Unit,
    onNavCommunityCommentEdit : (Int) -> Unit
){
    navigation(
        startDestination = CommunityRoute.CommunityPageRoute.name,
        route = CommunityRoute.CommunityGraphRoute.name
    ){
        composable(route = CommunityRoute.CommunityHomeRoute.name) {
            CommunityHomeRoute(
                onNavCommunityDescription = onNavCommunityDescription,
                onNavCommunityGraph = onNavCommunityPage
            )
        }
        composable(route = CommunityRoute.CommunityPageRoute.name){
            CommunityPageRoute(
                onNavBack = onNavBack,
                onNavSearch = onNavCommunitySearch,
                onNavCommunityDescription = onNavCommunityDescription,
                onNavPost = onNavCommunityPost
            )
        }
        composable(route = "${CommunityRoute.CommunityPostRoute.name}/{type}") {
            val type = it.arguments?.getString("type")

            CommunityPostRoute(
                onNavBack = onNavBack,
                _category = type
            )
        }
        composable(route = "${CommunityRoute.CommunityEditRoute.name}/{id}"){
            val id = it.arguments?.getString("id")

            CommunityEditRoute(
                id = id?.toInt(),
                onNavBack = onNavBack,
                onNavCommunityDesc = onNavCommunityDescription
            )
        }
        composable(route = "${CommunityRoute.CommunityDescriptionRoute.name}/{id}") {
            val id = it.arguments?.getString("id")?: "0"

            CommunityDescriptionRoute (
                _id = id.toInt(),
                onNavCommunityEdit = onNavCommunityEdit,
                onNavCommentEdit = onNavCommunityCommentEdit,
                onNavBack = onNavBack
            )
        }
        composable(route = CommunityRoute.CommunitySearchRoute.name){
            CommunitySearchRoute(
                onNavBack = onNavBack,
                onNavCommunityDesc = onNavCommunityDescription
            )
        }
        composable(route = "${CommunityRoute.CommunityCommentEditRoute.name}/{commentId}") {
            val id = it.arguments?.getString("commentId")?.toInt()
            CommunityCommentEditRoute(
                _commentId = id,
                onNavBack = onNavBack
            )
        }
    }
}