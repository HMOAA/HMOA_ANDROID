package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.hmoa.feature_authentication.navigation.loginScreen
import com.hmoa.feature_authentication.navigation.navigateToLogin
import com.hmoa.feature_authentication.navigation.navigateToPickNickname
import com.hmoa.feature_authentication.navigation.navigateToPickPersonalInfo
import com.hmoa.feature_authentication.navigation.navigateToSignup
import com.hmoa.feature_authentication.navigation.pickNicknameScreen
import com.hmoa.feature_authentication.navigation.pickPersonalInfoScreen
import com.hmoa.feature_authentication.navigation.signupScreen
import com.hmoa.feature_brand.navigation.brandScreen
import com.hmoa.feature_brand.navigation.brandSearchScreen
import com.hmoa.feature_brand.navigation.navigateToBrand
import com.hmoa.feature_community.Navigation.navigateToCommunityCommentEditRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityDescriptionRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityEditRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityPage
import com.hmoa.feature_community.Navigation.navigateToCommunityPostRoute
import com.hmoa.feature_community.Navigation.navigateToCommunityRoute
import com.hmoa.feature_community.Navigation.navigateToCommunitySearchRoute
import com.hmoa.feature_fcm.alarmRoute
import com.hmoa.feature_home.navigation.allPerfumeScreen
import com.hmoa.feature_home.navigation.homeScreen
import com.hmoa.feature_home.navigation.navigateToAllPerfume
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_home.navigation.perfumeSearchScreen
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
import com.hmoa.feature_perfume.navigation.createNewPerfumeComment
import com.hmoa.feature_perfume.navigation.editMyPerfumeComment
import com.hmoa.feature_perfume.navigation.navigateToCreateNewperfumeComment
import com.hmoa.feature_perfume.navigation.navigateToPerfume
import com.hmoa.feature_perfume.navigation.navigateToPerfumeComment
import com.hmoa.feature_perfume.navigation.navigateToSpecificPerfumeComment
import com.hmoa.feature_perfume.navigation.perfumeComment
import com.hmoa.feature_perfume.navigation.perfumeScreen
import com.hmoa.feature_perfume.navigation.specificComment

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
        homeScreen(onPerfumeClick = { perfumeId ->
            navController.navigateToPerfume(perfumeId)
        }, onAllPerfumeClick = { navController.navigateToAllPerfume(it) })
        perfumeSearchScreen(onBackClick = navController::navigateToBack)
        allPerfumeScreen(
            onNavLogin = navController::navigateToLogin,
            onNavBack = navController::navigateToBack,
            onPerfumeClick = { navController.navigateToPerfume(it) })

        /** fcm 모듈 */
        alarmRoute(onNavBack = navController::navigateToBack)

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
            onNavEditPost = navController::navigateToCommunityEditRoute,
            onNavEditProfile = navController::navigateToEditProfilePage,
            onNavManageMyInfo = navController::navigateToMyInfoPage,
            onNavMyActivity = navController::navigateToMyActivity,
            onNavMyFavoriteComment = navController::navigateToMyFavoriteCommentPage,
            onNavMyPost = navController::navigateToMyPostPage,
            onNavMyComment = navController::navigateToMyCommentPage,
            onNavMyBirth = navController::navigateToMyBirth,
            onNavMyGender = navController::navigateToMyGenderPage,
            onNavMyPerfume = navController::navigateToLike,
            onNavPerfume = navController::navigateToPerfume
        )

        /** HPedia 모듈 (내부에 Community 모듈 포함) */
        this.nestedHPediaGraph(
            onNavBack = navController::navigateToBack,
            onNavCommunityPost = navController::navigateToCommunityPostRoute,
            onNavCommunityEdit = navController::navigateToCommunityEditRoute,
            onNavCommunityDesc = navController::navigateToCommunityDescriptionRoute,
            onNavCommunityPage = navController::navigateToCommunityPage,
            onNavCommunityGraph = navController::navigateToCommunityRoute,
            onNavCommunityCommentEdit = navController::navigateToCommunityCommentEditRoute,
            onNavCommunitySearch = navController::navigateToCommunitySearchRoute,
            onNavHPediaDesc = navController::navigateToHPediaDescRoute,
            onNavHPediaSearch = navController::navigateToHPediaSearchRoute,
            onNavLogin = navController::navigateToLogin,
            onNavHome = navController::navigateToHome,
            onNavHPedia = navController::navigateToHPedia
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