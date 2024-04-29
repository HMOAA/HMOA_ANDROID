package com.example.feature_userinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.userinfo.MyActivityRoute
import com.example.userinfo.MyCommentRoute
import com.example.userinfo.MyPageRoute
import com.hmoa.feature_userinfo.MyFavoriteCommentRoute
import com.hmoa.feature_userinfo.MyPostRoute
import com.hmoa.feature_userinfo.NoAuthMyPage

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

//뒤로가기
fun NavController.navigateToBack() = navigateUp()

fun NavGraphBuilder.nestedUserInfoGraph(
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
    onNavMyGender: () -> Unit
) {
    navigation(
        startDestination = UserInfoGraph.MyPage.name,
        route = UserInfoGraph.UserInfoGraph.name
    ) {
        composable(route = UserInfoGraph.MyPage.name) {
            MyPageRoute(
                onNavEditProfile = onNavEditProfile,
                onNavMyActivity = onNavMyActivity,
                onNavManageMyInfo = onNavManageMyInfo,
                onNavLogin = onNavLogin
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
                onNavCommunity = onNavCommunity
            )
        }
        composable(route = UserInfoGraph.MyFavoriteCommentRoute.name) {
            MyFavoriteCommentRoute(
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity
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
        composable(route = UserInfoGraph.MyGenderRoute.name){
            MyGenderRoute(onNavBack = onNavBack)
        }
        composable(route = UserInfoGraph.NoAuthMyPage.name) {
            NoAuthMyPage(onNavLogin = onNavLogin)
        }
    }
}