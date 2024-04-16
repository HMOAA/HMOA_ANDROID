package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.feature_userinfo.*
import com.hmoa.feature_authentication.navigation.*
import com.hmoa.feature_brand.navigation.brandScreen
import com.hmoa.feature_brand.navigation.brandSearchScreen
import com.hmoa.feature_brand.navigation.navigateToBrand
import com.hmoa.feature_community.Navigation.*
import com.hmoa.feature_home.navigation.homeScreen
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_perfume.navigation.*

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /** home 모듈 */
        homeScreen(onPerfumeClick = { perfumeId -> navController.navigateToPerfume(perfumeId) }, onAllPerfumeClick = {})

        /** authentication 모듈 */
        loginScreen(onSignupClick = navController::navigateToSignup, onHomeClick = navController::navigateToHome)
        signupScreen(onPickNicknameClick = navController::navigateToPickNickname)
        pickNicknameScreen(
            onPickPersonalInfoClick = navController::navigateToPickPersonalInfo,
            onSignupClick = navController::navigateToSignup
        )
        pickPersonalInfoScreen(
            onHomeClick = navController::navigateToHome,
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
            onNavHome = { /** Home Navigation */ },
            onNavHPedia = navController::navigateToCommunityRoute,
            onNavLike = { /** Like Navigation */ },
            onNavMyPage = { /** Setting Navigation */ }
        )

        /** perfume 모듈 */
        perfumeScreen(
            onBackClick = navController::navigateToBack,
            onHomeClick = navController::navigateToHome,
            onCommentAddClick = { perfumeId -> navController.navigateToCreateNewperfumeComment(perfumeId) },
            onBrandClick = { brandId -> navController.navigateToBrand(brandId) },
            onViewCommentAllClick = { perfumeId -> navController.navigateToPerfumeComment(perfumeId) },
            onSimilarPerfumeClick = { perfumeId -> navController.navigateToPerfume(perfumeId) },
            onSpecificCommentClick = { commentId, isEditable ->
                navController.navigateToSpecificPerfumeComment(
                    commentId.toInt(),
                    isEditable
                )
            }
        )
        perfumeComment(
            onBackClick = navController::navigateToBack,
            onAddCommentClick = { perfumeId -> navController.navigateToCreateNewperfumeComment(perfumeId) },
            onSpecificCommentClick = { commentId, isEditable ->
                navController.navigateToSpecificPerfumeComment(
                    commentId.toInt(),
                    isEditable
                )
            }
        )
        specificComment(onBackClick = navController::navigateToBack)
        editMyPerfumeComment(onBackClick = navController::navigateToBack)
        createNewPerfumeComment(onBackClick = navController::navigateToBack)

        /**brand 모듈*/
        brandScreen(
            onBackClck = navController::navigateToBack,
            onHomeClick = { navController.navigateToHome() },
            onPerfumeClick = { navController.navigateToPerfume(it) }
        )
        brandSearchScreen(
            onBackClick = navController::navigateToBack,
            onBrandClick = { navController.navigateToBrand(it.toString()) }
        )
    }
}