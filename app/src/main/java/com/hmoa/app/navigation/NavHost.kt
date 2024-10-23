package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hmoa.feature_authentication.navigation.*
import com.hmoa.feature_brand.navigation.brandScreen
import com.hmoa.feature_brand.navigation.brandSearchScreen
import com.hmoa.feature_brand.navigation.navigateToBrand
import com.hmoa.feature_community.Navigation.*
import com.hmoa.feature_fcm.alarmRoute
import com.hmoa.feature_hbti.navigation.*
import com.hmoa.feature_home.navigation.*
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaDescRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaSearchRoute
import com.hmoa.feature_hpedia.Navigation.nestedHPediaGraph
import com.hmoa.feature_magazine.Navigation.magazineDesc
import com.hmoa.feature_magazine.Navigation.magazineMain
import com.hmoa.feature_magazine.Navigation.navigateToMagazineDesc
import com.hmoa.feature_perfume.navigation.*
import com.hmoa.feature_userinfo.navigation.*

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
        homeScreen(
            onPerfumeClick = { perfumeId ->
                navController.navigateToPerfume(perfumeId)
            },
            onAllPerfumeClick = { navController.navigateToAllPerfume(it) },
            onHbtiClick = { navController.navigateToHbti() })
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

        /** user info 모듈 */
        this.nestedUserInfoGraph(
            navHome = navController::navigateToHome,
            navLogin = navController::navigateToLogin,
            navBack = navController::navigateToBack,
            navCommunity = navController::navigateToCommunityDescriptionRoute,
            navEditPost = navController::navigateToCommunityEditRoute,
            navEditProfile = navController::navigateToEditProfilePage,
            navManageMyInfo = navController::navigateToMyInfoPage,
            navMyActivity = navController::navigateToMyActivity,
            navMyFavoriteComment = navController::navigateToMyFavoriteCommentPage,
            navMyPost = navController::navigateToMyPostPage,
            navMyComment = navController::navigateToMyCommentPage,
            navMyBirth = navController::navigateToMyBirth,
            navMyGender = navController::navigateToMyGenderPage,
            navMyPerfume = navController::navigateToMyFavoritePerfume,
            navPerfume = navController::navigateToPerfume,
            navOrderRecord = navController::navigateToOrderRecord,
            navRefund = navController::navigateToRefund,
            navRefundRecord = navController::navigateToRefundRecord
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
            navHPedia = navController::navigateToHPedia
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
            navBack = navController::navigateToBack,
            navLogin = navController::navigateToLogin,
            navDesc = navController::navigateToMagazineDesc
        )

        /** hbti 모듈 */
        hbtiScreen(
            onHbtiSurveyClick = navController::navigateToHbtiSurvey,
            onAfterOrderClick = navController::navigateToPerfumeRecommendation,
            onBackClick = navController::navigateToBack
        )
        hbtiSurveyScreen(
            onBackClick = navController::navigateToBack,
            onErrorHandleLoginAgain = navController::navigateToLogin,
            onNextClick = navController::navigateToHbtiSurveyLoading
        )
        hbtiSurveyResultScreen(
            onErrorHandleLoginAgain = navController::navigateToLogin,
            onBackClick = navController::navigateToBack,
            onHbtiProcessClick = navController::navigateToHbtiProcess
        )
        hbtiSurveyLoadingScreen(
            onNextScreen = navController::navigateToHbtiSurveyResult,
            onBackClick = navController::navigateToBack,
        )
        hbtiProcessScreen(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToNotePick
        )
        notePickScreen(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToNotePickResult,
            onErrorHandleLoginAgain = navController::navigateToLogin,
            onBackToHbtiScreen = navController::navigateToHbti
        )
        notePickResult(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToOrder,
            onBackToHbtiScreen = navController::navigateToHbti
        )
        order(
            navBack = navController::navigateToBack,
            navAddAddress = navController::navigateToAddAddress,
            navOrderResult = navController::navigateToOrderResult
        )
        addAddress(
            navOrder = navController::navigateToOrder
        )
        orderResult(
            navBack = navController::navigateToBack,
            navHome = navController::navigateToHome
        )
        //order()
        perfumeRecommendationRoute(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToPerfumeRecommendationResult
        )
        perfumeRecommendationResultRoute(
            onBackClick = navController::navigateToBack,
            onNavPerfumeDescription = { id -> navController.navigateToPerfume(id) },
            onNavHome = navController::navigateToHome
        )
    }
}