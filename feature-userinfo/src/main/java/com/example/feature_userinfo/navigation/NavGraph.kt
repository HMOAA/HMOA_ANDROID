package com.example.feature_userinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userinfo.MyActivityPage
import com.example.userinfo.MyCommentPage
import com.example.userinfo.MyFavoriteCommentPage
import com.example.userinfo.MyPage
import com.example.userinfo.NoAuthMyPage

const val MY_PAGE_ROUTE = "my_page_route"

//마이페이지로 이동
fun NavController.navigateToMyPage() = navigate(Screens.MyPage.name)

//내 활동 페이지
fun NavController.navigateToMyActivity() = navigate(Screens.MyActivityPage.name)

//내 댓글 페이지
fun NavController.navigateToMyCommentPage() = navigate(Screens.MyCommentPage.name)

//좋아요한 댓글 페이지
fun NavController.navigateToMyFavoriteCommentPage() = navigate(Screens.MyFavoriteCommentPage.name)

//프로필 수정 화면
fun NavController.navigateToEditProfilePage() = navigate(Screens.EditProfilePage.name)

//내 정보 페이지
fun NavController.navigateToMyInfoPage() = navigate(Screens.MyInfoPage.name)

//내 성별 페이지
fun NavController.navigateToMyGenderPage() = navigate(Screens.MyGenderPage.name)

//내 출생연도 페이지
fun NavController.navigateToMyBirth() = navigate(Screens.MyBirthPage.name)

//내 게시글 페이지
fun NavController.navigateToMyPostPage() = navigate(Screens.MyPostPage.name)

//로그인 되지 않았을 경우 화면
fun NavController.navigateToNoAuthPage() = navigate(Screens.NoAuthMyPage.name)

//뒤로가기
fun NavController.navigateToBack() = navigateUp()

fun NavGraphBuilder.myPage(
    onNavEditProfile : () -> Unit,
    onNavMyActivity : () -> Unit,
    onNavManageMyInfo : () -> Unit,
    onNavLogin : () -> Unit
) {
    composable(route = MY_PAGE_ROUTE){
        MyPage(
            onNavEditProfile = onNavEditProfile,
            onNavMyActivity = onNavMyActivity,
            onNavManageMyInfo = onNavManageMyInfo,
            onNavLogin = onNavLogin
        )
    }
}

fun NavGraphBuilder.editProfile(
    onNavBack : () -> Unit,
) {
    composable(route = Screens.EditProfilePage.name) {
        EditProfilePage (
            onNavBack = onNavBack
        )
    }
}

fun NavGraphBuilder.myPost(
    onNavBack : () -> Unit,
    onNavEditPost : () -> Unit,
){
    composable(route = Screens.MyPostPage.name){
        MyPostPage(
            onNavBack = onNavBack,
            onNavEditPost = onNavEditPost
        )
    }
}

fun NavGraphBuilder.myActivity(
    onNavMyFavoriteComment : () -> Unit,
    onNavMyComment : () -> Unit,
    onNavMyPost : () -> Unit,
    onNavBack : () -> Unit,
){
    composable(route = Screens.MyActivityPage.name){
        MyActivityPage(
            onNavMyFavoriteComment = onNavMyFavoriteComment,
            onNavMyComment = onNavMyComment,
            onNavMyPost = onNavMyPost,
            onNavBack = onNavBack
        )
    }
}

fun NavGraphBuilder.myComment(
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit,
){
    composable(route = Screens.MyCommentPage.name){
        MyCommentPage(
            onNavBack = onNavBack,
            onNavCommunity = onNavCommunity
        )
    }
}

fun NavGraphBuilder.myFavoriteComment(
    onNavBack : () -> Unit,
    onNavCommunity : () -> Unit, // 이것도 Community로?
){
    composable(route = Screens.MyFavoriteCommentPage.name){
        MyFavoriteCommentPage(
            onNavBack = onNavBack,
            onNavCommunity = onNavCommunity
        )
    }
}

fun NavGraphBuilder.myInfo(
    onNavBack : () -> Unit,
    onNavMyBirth : () -> Unit,
    onNavMyGender : () -> Unit,
){
    composable(route = Screens.MyInfoPage.name){
        MyInfoPage(
            onNavBack = onNavBack,
            onNavMyBirth = onNavMyBirth,
            onNavMyGender = onNavMyGender
        )
    }
}

fun NavGraphBuilder.myBirth(
    onNavBack : () -> Unit,
) {
    composable(route = Screens.MyBirthPage.name){
        MyBirthPage(
            onNavBack = onNavBack
        )
    }
}

fun NavGraphBuilder.myGender(
    onNavBack : () -> Unit,
) {
    composable(route = Screens.MyGenderPage.name){
        MyBirthPage (
            onNavBack = onNavBack
        )
    }
}

fun NavGraphBuilder.noAuthMyPage(
    onNavLogin : () -> Unit,
){
    composable(route = Screens.NoAuthMyPage.name){
        NoAuthMyPage(
            onNavLogin = onNavLogin
        )
    }
}