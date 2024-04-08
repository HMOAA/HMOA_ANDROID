package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.feature_userinfo.*
import com.hmoa.feature_authentication.navigation.*
import com.hmoa.feature_community.Navigation.*
import com.hmoa.feature_home.homeScreen
import com.hmoa.feature_home.navigateToHome
import com.hmoa.feature_perfume.navigation.navigateToPerfume
import com.hmoa.feature_perfume.navigation.navigateToPerfumeComment
import com.hmoa.feature_perfume.navigation.perfumeComment
import com.hmoa.feature_perfume.navigation.perfumeScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {

        mainScreen(
            onNavCommunity = navController::navigateToCommunityRoute, navController = navController
        )
        /** home 모듈 */
        homeScreen(onPerfumeClick = { perfumeId -> navController.navigateToPerfume(perfumeId) }, onAllPerfumeClick = {})

        /** authentication 모듈 */
        loginScreen(onSignupClick = navController::navigateToSignup, onHomeClick = navController::navigateToMain)
        signupScreen(onPickNicknameClick = navController::navigateToPickNickname)
        pickNicknameScreen(
            onPickPersonalInfoClick = navController::navigateToPickPersonalInfo,
            onSignupClick = navController::navigateToSignup
        )
        pickPersonalInfoScreen(
            onHomeClick = navController::navigateToMain,
            onPickNicknameClick = navController::navigateToPickNickname
        )

        /** user info 모듈 */
        this.nestedUserInfoGraph(
            onNavLogin = navController::navigateToLogin,
            onNavBack = navController::navigateToBack,
            onNavCommunity = { /** onNavCommunity는 Community 모듈로의 이동 */ },
            onNavEditPost = { /** 여기서 게시글 수정으로 이동 */ },
            onNavEditProfile = navController::navigateToEditProfilePage,
            onNavManageMyInfo = navController::navigateToMyInfoPage,
            onNavMyActivity = navController::navigateToMyActivity,
            onNavMyFavoriteComment = navController::navigateToMyFavoriteCommentPage,
            onNavMyPost = navController::navigateToMyPostPage,
            onNavMyComment = navController::navigateToMyCommentPage,
            onNavMyBirth = navController::navigateToMyBirth,
            onNavMyGender = navController::navigateToMyGenderPage
        )

        /** community 모듈 */
        this.nestedCommunityGraph(
            onNavBack = navController::navigateToBack,
            onNavCommunityPost = navController::navigateToCommunityPostRoute,
            onNavCommunityEdit = navController::navigateToCommunityEditRoute,
            onNavCommunityDescription = navController::navigateToCommunityDescriptionRoute,
            onNavCommunityPage = navController::navigateToCommunityPage,
            onNavHome = { /** Home Navigation */},
            onNavHPedia = navController::navigateToCommunityRoute,
            onNavLike = { /** Like Navigation */ },
            onNavMyPage = { /** Setting Navigation */ }
        )

        /** perfume 모듈 */
        perfumeScreen(
            onBackClick = navController::navigateToBack,
            onHomeClick = navController::navigateToHome,
            onCommentAddClick = {},
            onBrandClick = {},
            onViewCommentAllClick = { perfumeId -> navController.navigateToPerfumeComment(perfumeId) },
            onSimilarPerfumeClick = {},
            onSpecificCommentClick = { commentId, isEditable -> }
        )
        perfumeComment(
            onBackClick = navController::navigateToBack,
            onAddCommentClick = {},
            onSpecificCommentClick = { commentId, isEditable -> }
        )
    }
}