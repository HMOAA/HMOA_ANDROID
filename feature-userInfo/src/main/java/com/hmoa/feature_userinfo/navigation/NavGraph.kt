package com.example.feature_userinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.userinfo.MyActivityRoute
import com.example.userinfo.MyCommentRoute
import com.example.userinfo.MyPageRoute
import com.hmoa.feature_userinfo.MyFavoriteCommentRoute
import com.hmoa.feature_userinfo.MyPostRoute
import com.hmoa.feature_userinfo.NoAuthMyPage
import com.hmoa.feature_userinfo.Screen.OrderRecordRoute
import com.hmoa.feature_userinfo.Screen.RefundRoute

//graph 이동
fun NavController.navigateToUserInfoGraph() = navigate(UserInfoGraph.UserInfoGraph.name)

//마이페이지로 이동
fun NavController.navigateToMyPage() = navigate(UserInfoGraph.MyPage.name)

//내 활동 페이지
fun NavController.navigateToMyActivity() = navigate(UserInfoGraph.MyActivityRoute.name)

//내 댓글 페이지
fun NavController.navigateToMyCommentPage() = navigate(UserInfoGraph.MyCommentRoute.name)

//좋아요한 댓글 페이지
fun NavController.navigateToMyFavoriteCommentPage() = navigate(UserInfoGraph.MyFavoriteCommentRoute.name)

//프로필 수정 화면
fun NavController.navigateToEditProfilePage() = navigate(UserInfoGraph.EditProfileRoute.name)

//내 정보 페이지
fun NavController.navigateToMyInfoPage() = navigate(UserInfoGraph.MyInfoRoute.name)

//내 성별 페이지
fun NavController.navigateToMyGenderPage() = navigate(UserInfoGraph.MyGenderRoute.name)

//내 출생연도 페이지
fun NavController.navigateToMyBirth() = navigate(UserInfoGraph.MyBirthRoute.name)

//내 게시글 페이지
fun NavController.navigateToMyPostPage() = navigate(UserInfoGraph.MyPostRoute.name)

//환불 & 반품
fun NavController.navigateToRefund(type: String, orderId: Int) = navigate("${UserInfoGraph.RefundRoute.name}/${type}/${orderId}")
//주문 내역
fun NavController.navigateToOrderRecord() = navigate(UserInfoGraph.OrderRecordRoute.name)
//뒤로가기
fun NavController.navigateToBack() = navigateUp()

fun NavGraphBuilder.nestedUserInfoGraph(
    onNavMyPerfume : () -> Unit,
    onNavLogin: () -> Unit,
    onNavBack: () -> Unit,
    onNavCommunity: (Int) -> Unit,
    onNavEditPost: (Int) -> Unit,
    onNavEditProfile: () -> Unit,
    onNavManageMyInfo: () -> Unit,
    onNavMyActivity: () -> Unit,
    onNavMyFavoriteComment: () -> Unit,
    onNavMyPost: () -> Unit,
    onNavMyComment: () -> Unit,
    onNavMyBirth: () -> Unit,
    onNavMyGender: () -> Unit,
    onNavPerfume : (Int) -> Unit,
    navOrderRecord: () -> Unit,
    navRefund: (pageType: String, orderId: Int) -> Unit,
) {
    navigation(
        startDestination = UserInfoGraph.MyPage.name,
        route = UserInfoGraph.UserInfoGraph.name
    ) {
        composable(route = UserInfoGraph.MyPage.name) {
            MyPageRoute(
                onNavMyPerfume = onNavMyPerfume,
                onNavEditProfile = onNavEditProfile,
                onNavMyActivity = onNavMyActivity,
                onNavManageMyInfo = onNavManageMyInfo,
                onNavLogin = onNavLogin,
                onNavBack = onNavBack,
                navOrderRecord = navOrderRecord
            )
        }
        composable(route = UserInfoGraph.EditProfileRoute.name) {
            EditProfileRoute(onNavBack = onNavBack)
        }
        composable(route = UserInfoGraph.MyPostRoute.name) {
            MyPostRoute(
                onNavBack = onNavBack,
                onNavEditPost = onNavEditPost
            )
        }
        composable(route = UserInfoGraph.MyActivityRoute.name) {
            MyActivityRoute(
                onNavMyFavoriteComment = onNavMyFavoriteComment,
                onNavMyComment = onNavMyComment,
                onNavMyPost = onNavMyPost,
                onNavBack = onNavBack
            )
        }
        composable(route = UserInfoGraph.MyCommentRoute.name) {
            MyCommentRoute(
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity,
                onNavPerfume = onNavPerfume
            )
        }
        composable(route = UserInfoGraph.MyFavoriteCommentRoute.name) {
            MyFavoriteCommentRoute(
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity,
                onNavPerfume = onNavPerfume
            )
        }
        composable(route = UserInfoGraph.MyInfoRoute.name) {
            MyInfoRoute(
                onNavBack = onNavBack,
                onNavMyBirth = onNavMyBirth,
                onNavMyGender = onNavMyGender
            )
        }
        composable(route = UserInfoGraph.MyBirthRoute.name) {
            MyBirthRoute(onNavBack = onNavBack)
        }
        composable(route = UserInfoGraph.MyGenderRoute.name) {
            MyGenderRoute(onNavBack = onNavBack)
        }
        composable(route = UserInfoGraph.NoAuthMyPage.name) {
            NoAuthMyPage(onNavLogin = onNavLogin)
        }
        composable(route = UserInfoGraph.OrderRecordRoute.name){
            OrderRecordRoute(
                navBack = onNavBack,
                navReturnOrRefund = navRefund,
            )
        }
        composable(
            route = "${UserInfoGraph.RefundRoute.name}/{type}/{orderId}",
            arguments = listOf(
                navArgument("type"){type = NavType.StringType},
                navArgument("orderId"){type = NavType.IntType}
            )
        ){
            val type = it.arguments?.getString("type")
            val orderId = it.arguments?.getInt("orderId")
            RefundRoute(
                type = type,
                orderId = orderId,
                navBack = onNavBack
            )
        }
    }
}