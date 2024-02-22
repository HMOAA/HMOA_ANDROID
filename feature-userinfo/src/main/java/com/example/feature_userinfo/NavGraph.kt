package com.example.feature_userinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userinfo.MyActivityPage
import com.example.userinfo.MyCommentPage
import com.example.userinfo.MyFavoriteCommentPage
import com.example.userinfo.MyPage
import com.example.userinfo.NoAuthMyPage

@Composable
fun UserInfoNavGraph(
    navController : NavHostController,
    isAuthenticated : Boolean,
    navLoginPage : () -> Unit,
    navCommunityPage : () -> Unit
){
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screens.MyPage.name else Screens.NoAuthMyPage.name
    ){
        composable(Screens.MyPage.name) {
            MyPage(
                navController = navController,
                navLoginPage = navLoginPage
            )
        }
        composable(Screens.MyActivityPage.name) {
            MyActivityPage(navController)
        }
        composable(Screens.MyCommentPage.name) {
            MyCommentPage(navController)
        }
        composable(Screens.MyFavoriteCommentPage.name) {
            MyFavoriteCommentPage(
                navController = navController,
                navCommunityPage = navCommunityPage
            )
        }
        composable(Screens.EditProfilePage.name){
            EditProfilePage(navController)
        }
        composable(Screens.MyInfoPage.name){
            MyInfoPage(navController)
        }
        composable(Screens.MyBirthPage.name) {
            MyBirthPage(navController)
        }
        composable(Screens.MyGenderPage.name) {
            MyGenderPage(navController)
        }
        composable(Screens.NoAuthMyPage.name) {
            NoAuthMyPage(navLoginPage)
        }
        composable(Screens.MyPostPage.name) {
            MyPostPage(navController)
        }
    }
}