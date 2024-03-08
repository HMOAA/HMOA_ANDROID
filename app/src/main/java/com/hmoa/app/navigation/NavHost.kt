package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.example.feature_userinfo.editProfile
import com.example.feature_userinfo.myActivity
import com.example.feature_userinfo.myBirth
import com.example.feature_userinfo.myComment
import com.example.feature_userinfo.myFavoriteComment
import com.example.feature_userinfo.myGender
import com.example.feature_userinfo.myInfo
import com.example.feature_userinfo.myPage
import com.example.feature_userinfo.myPost
import com.example.feature_userinfo.navigateToBack
import com.example.feature_userinfo.navigateToEditProfilePage
import com.example.feature_userinfo.navigateToMyActivity
import com.example.feature_userinfo.navigateToMyBirth
import com.example.feature_userinfo.navigateToMyCommentPage
import com.example.feature_userinfo.navigateToMyFavoriteCommentPage
import com.example.feature_userinfo.navigateToMyGenderPage
import com.example.feature_userinfo.navigateToMyInfoPage
import com.example.feature_userinfo.navigateToMyPostPage
import com.example.feature_userinfo.nestedUserInfoGraph
import com.example.feature_userinfo.noAuthMyPage
import com.hmoa.feature_authentication.navigation.*

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {

        /** authentication 모듈 */
        loginScreen(onSignupClick = navController::navigateToSignup)
        signupScreen(onPickNicknameClick = navController::navigateToPickNickname)
        pickNicknameScreen (onPickPersonalInfoClick = navController::navigateToPickPersonalInfo, onSignupClick = navController::navigateToSignup)
        
        /** user info 모듈 */
        nestedUserInfoGraph(
            onNavLogin = navController::navigateToLogin,
            onNavBack = navController::navigateToBack,
            /** onNavCommunity는 Community 모듈로의 이동 */
            onNavCommunity = navController::navigateToBack,
            /** 여기서 게시글 수정으로 이동해야.. 하나요..? */
            onNavEditPost = navController::navigateToBack,
            onNavEditProfile = navController::navigateToEditProfilePage,
            onNavManageMyInfo = navController::navigateToMyInfoPage,
            onNavMyActivity = navController::navigateToMyActivity,
            onNavMyFavoriteComment = navController::navigateToMyFavoriteCommentPage,
            onNavMyPost = navController::navigateToMyPostPage,
            onNavMyComment = navController::navigateToMyCommentPage,
            onNavMyBirth = navController::navigateToMyBirth,
            onNavMyGender = navController::navigateToMyGenderPage
        )
    }
}