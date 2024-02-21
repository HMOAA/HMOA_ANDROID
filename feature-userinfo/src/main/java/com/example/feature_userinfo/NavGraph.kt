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

@Composable
fun NavGraph(
    navController : NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.MyPage.name
    ){
        composable(Screens.MyPage.name) {
            MyPage()
        }
        composable(Screens.MyActivityPage.name) {
            MyActivityPage()
        }
        composable(Screens.MyCommentPage.name) {
            MyCommentPage()
        }
        composable(Screens.MyFavoriteCommentPage.name) {
            MyFavoriteCommentPage()
        }
        composable(Screens.EditProfilePage.name){
            EditProfilePage()
        }
        composable(Screens.MyInfoPage.name){
            MyInfoPage()
        }
        composable(Screens.MyBirthPage.name) {
            MyBirthPage()
        }
        composable(Screens.MyGenderPage.name) {
            MyGenderPage()
        }
    }
}