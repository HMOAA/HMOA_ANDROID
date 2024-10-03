package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_userinfo.*
import com.hmoa.feature_authentication.navigation.*
import com.hmoa.feature_brand.navigation.brandScreen
import com.hmoa.feature_brand.navigation.brandSearchScreen
import com.hmoa.feature_brand.navigation.navigateToBrand
import com.hmoa.feature_community.Navigation.*
import com.hmoa.feature_fcm.alarmRoute
import com.hmoa.feature_home.navigation.*
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaDescRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaSearchRoute
import com.hmoa.feature_hpedia.Navigation.nestedHPediaGraph
import com.hmoa.feature_like.Screen.LIKE_ROUTE
import com.hmoa.feature_like.Screen.LikeRoute
import com.hmoa.feature_like.Screen.navigateToLike
import com.hmoa.feature_magazine.Navigation.magazineDesc
import com.hmoa.feature_magazine.Navigation.magazineMain
import com.hmoa.feature_magazine.Navigation.navigateToMagazineDesc
import com.hmoa.feature_perfume.navigation.*

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
    appVersion: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /** home 모듈 */
        homeScreen(
            onPerfumeClick = { perfumeId ->
                navController.navigateToPerfume(perfumeId)
            },
            onAllPerfumeClick = { navController.navigateToAllPerfume(it) },
            onHbtiClick = { })
        perfumeSearchScreen(onBackClick = navController::navigateToBack)
        allPerfumeScreen(
            onNavLogin = navController::navigateToLogin,
            onNavBack = navController::navigateToBack,
            onPerfumeClick = { navController.navigateToPerfume(it) })

        /** fcm 모듈 */
        alarmRoute(
            onNavBack = navController::navigateToBack,
            onNavCommunityDesc = navController::navigateToCommunityDescriptionRoute,
            onNavPerfumeComment = navController::navigateToPerfumeComment
        )

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

        /** like 모듈 */
        composable(LIKE_ROUTE) {
            LikeRoute(
                onNavPerfumeDesc = navController::navigateToPerfume,
                onNavHome = navController::navigateToHome,
                onErrorHandleLoginAgain = navController::navigateToLogin
            )
        }

        /** user info 모듈 */
        this.nestedUserInfoGraph(
            onNavLogin = navController::navigateToLogin,
            onNavBack = navController::navigateToBack,
            onNavCommunity = navController::navigateToCommunityDescriptionRoute,
            onNavEditProfile = navController::navigateToEditProfilePage,
            onNavManageMyInfo = navController::navigateToMyInfoPage,
            onNavMyActivity = navController::navigateToMyActivity,
            onNavMyFavoriteComment = navController::navigateToMyFavoriteCommentPage,
            onNavMyPost = navController::navigateToMyPostPage,
            onNavMyComment = navController::navigateToMyCommentPage,
            onNavMyBirth = navController::navigateToMyBirth,
            onNavMyGender = navController::navigateToMyGenderPage,
            onNavMyPerfume = navController::navigateToLike,
            onNavPerfume = navController::navigateToPerfume,
            appVersion = appVersion
        )

        /** HPedia 모듈 (내부에 Community 모듈 포함) */
        this.nestedHPediaGraph(
            navBack = navController::navigateToBack,
            navCommunityDesc = navController::navigateToCommunityDescriptionRoute,
            navCommunityGraph = navController::navigateToCommunityRoute,
            navHPediaDesc = navController::navigateToHPediaDescRoute,
            navHPediaSearch = navController::navigateToHPediaSearchRoute,
            navLogin = navController::navigateToLogin,
            navHome = navController::navigateToHome,
        )
        this.nestedCommunityGraph(
            navBack = navController::navigateToBack,
            navCommunityPage = navController::navigateToCommunityPage,
            navCommunityPost = navController::navigateToCommunityPostRoute,
            navCommunityEdit = navController::navigateToCommunityEditRoute,
            navCommunityDescription = navController::navigateToCommunityDescriptionRoute,
            navCommunitySearch = navController::navigateToCommunitySearchRoute,
            navCommunityCommentEdit = navController::navigateToCommunityCommentEditRoute,
            onErrorHandleLoginAgain = navController::navigateToLogin,
            navLogin = navController::navigateToLogin,
            navHome = navController::navigateToHome,
            navHPedia = navController::navigateToHPedia,
            popStack = { navController.popBackStack() }
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
            },
            onErrorHandleLoginAgain = { navController.navigateToLogin() }
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

        /** magazine 모듈 */
        magazineMain(
            onNavHome = navController::navigateToHome,
            onNavPerfumeDesc = navController::navigateToPerfume,
            onNavCommunityDesc = navController::navigateToCommunityDescriptionRoute,
            onNavMagazineDesc = navController::navigateToMagazineDesc
        )
        magazineDesc(
            onNavBack = navController::navigateToBack,
            onNavLogin = navController::navigateToLogin,
            onNavDesc = navController::navigateToMagazineDesc
        )
    }
}