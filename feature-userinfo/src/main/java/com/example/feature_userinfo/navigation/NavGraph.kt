package com.example.feature_userinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.userinfo.MyActivityPage
import com.example.userinfo.MyCommentPage
import com.example.userinfo.MyFavoriteCommentPage
import com.example.userinfo.MyPage
import com.example.userinfo.MyPageRoute
import com.example.userinfo.NoAuthMyPage

//마이페이지로 이동
fun NavController.navigateToMyPage() = navigate(Screens.MyPageRoute.name)

//내 활동 페이지
fun NavController.navigateToMyActivity() = navigate(Screens.MyActivityRoute.name)

//내 댓글 페이지
fun NavController.navigateToMyCommentPage() = navigate(Screens.MyCommentRoute.name)

//좋아요한 댓글 페이지
fun NavController.navigateToMyFavoriteCommentPage() = navigate(Screens.MyFavoriteCommentRoute.name)

//프로필 수정 화면
fun NavController.navigateToEditProfilePage() = navigate(Screens.EditProfileRoute.name)

//내 정보 페이지
fun NavController.navigateToMyInfoPage() = navigate(Screens.MyInfoRoute.name)

//내 성별 페이지
fun NavController.navigateToMyGenderPage() = navigate(Screens.MyGenderRoute.name)

//내 출생연도 페이지
fun NavController.navigateToMyBirth() = navigate(Screens.MyBirthRoute.name)

//내 게시글 페이지
fun NavController.navigateToMyPostPage() = navigate(Screens.MyPostRoute.name)

//뒤로가기
fun NavController.navigateToBack() = navigateUp()

fun NavGraphBuilder.nestedUserInfoGraph(
    onNavLogin : () -> Unit,
    onNavBack : () -> Unit,
    /** onNavCommunity는 Community 모듈로의 이동 */
    onNavCommunity : () -> Unit,
    /** 여기서 게시글 수정으로 이동해야.. 하나요..? */
    onNavEditPost : () -> Unit,
    onNavEditProfile : () -> Unit,
    onNavManageMyInfo : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavMyFavoriteComment : () -> Unit,
    onNavMyPost : () -> Unit,
    onNavMyComment : () -> Unit,
    onNavMyBirth : () -> Unit,
    onNavMyGender : () -> Unit
) {
    navigation(
        startDestination = Screens.MyPageRoute.name,
        route = "user_info_graph"
    ){
        composable(route = Screens.MyPageRoute.name){
            MyPageRoute(
                onNavEditProfile = onNavEditProfile,
                onNavMyActivity = onNavMyActivity,
                onNavManageMyInfo = onNavManageMyInfo,
                onNavLogin = onNavLogin
            )
        }
        composable(route = Screens.EditProfileRoute.name) {
            EditProfilePage (
                onNavBack = onNavBack
            )
        }
        composable(route = Screens.MyPostRoute.name){
            MyPostPage(
                onNavBack = onNavBack,
                onNavEditPost = onNavEditPost
            )
        }
        composable(route = Screens.MyActivityRoute.name){
            MyActivityPage(
                onNavMyFavoriteComment = onNavMyFavoriteComment,
                onNavMyComment = onNavMyComment,
                onNavMyPost = onNavMyPost,
                onNavBack = onNavBack
            )
        }
        composable(route = Screens.MyCommentRoute.name){
            MyCommentPage(
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity
            )
        }
        composable(route = Screens.MyFavoriteCommentRoute.name){
            MyFavoriteCommentPage(
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity
            )
        }
        composable(route = Screens.MyInfoRoute.name){
            MyInfoPage(
                onNavBack = onNavBack,
                onNavMyBirth = onNavMyBirth,
                onNavMyGender = onNavMyGender
            )
        }
        composable(route = Screens.MyBirthRoute.name){
            MyBirthPage(
                onNavBack = onNavBack
            )
        }
        composable(route = Screens.MyGenderRoute.name){
            MyBirthPage (
                onNavBack = onNavBack
            )
        }
        composable(route = Screens.NoAuthMyPage.name){
            NoAuthMyPage(
                onNavLogin = onNavLogin
            )
        }
    }
}