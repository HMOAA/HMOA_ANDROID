package com.hmoa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
import com.hmoa.feature_community.Navigation.nestedCommunityGraph
import com.hmoa.feature_fcm.alarmRoute
import com.hmoa.feature_hbti.navigation.addAddress
import com.hmoa.feature_hbti.navigation.hbtiProcessScreen
import com.hmoa.feature_hbti.navigation.hbtiScreen
import com.hmoa.feature_hbti.navigation.hbtiSurveyLoadingScreen
import com.hmoa.feature_hbti.navigation.hbtiSurveyResultScreen
import com.hmoa.feature_hbti.navigation.hbtiSurveyScreen
import com.hmoa.feature_hbti.navigation.navigateToAddAddress
import com.hmoa.feature_hbti.navigation.navigateToHbti
import com.hmoa.feature_hbti.navigation.navigateToHbtiProcess
import com.hmoa.feature_hbti.navigation.navigateToHbtiSurvey
import com.hmoa.feature_hbti.navigation.navigateToHbtiSurveyLoading
import com.hmoa.feature_hbti.navigation.navigateToHbtiSurveyResult
import com.hmoa.feature_hbti.navigation.navigateToNoteOrderQuantityPick
import com.hmoa.feature_hbti.navigation.navigateToNotePick
import com.hmoa.feature_hbti.navigation.navigateToNotePickResult
import com.hmoa.feature_hbti.navigation.navigateToOrder
import com.hmoa.feature_hbti.navigation.navigateToOrderResult
import com.hmoa.feature_hbti.navigation.navigateToPerfumeRecommendation
import com.hmoa.feature_hbti.navigation.navigateToPerfumeRecommendationResult
import com.hmoa.feature_hbti.navigation.navigateToReview
import com.hmoa.feature_hbti.navigation.navigateToWriteReview
import com.hmoa.feature_hbti.navigation.noteOrderQuantityPickScreen
import com.hmoa.feature_hbti.navigation.notePickResult
import com.hmoa.feature_hbti.navigation.notePickScreen
import com.hmoa.feature_hbti.navigation.order
import com.hmoa.feature_hbti.navigation.orderResult
import com.hmoa.feature_hbti.navigation.perfumeRecommendationResultRoute
import com.hmoa.feature_hbti.navigation.perfumeRecommendationRoute
import com.hmoa.feature_hbti.navigation.review
import com.hmoa.feature_hbti.navigation.writeReview
import com.hmoa.feature_home.navigation.allPerfumeScreen
import com.hmoa.feature_home.navigation.homeScreen
import com.hmoa.feature_home.navigation.navigateToAllPerfume
import com.hmoa.feature_home.navigation.navigateToHome
import com.hmoa.feature_home.navigation.perfumeSearchScreen
import com.hmoa.feature_hpedia.Navigation.navigateToHPedia
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaDescRoute
import com.hmoa.feature_hpedia.Navigation.navigateToHPediaSearchRoute
import com.hmoa.feature_hpedia.Navigation.nestedHPediaGraph
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
import com.hmoa.feature_userinfo.navigation.navigateToBack
import com.hmoa.feature_userinfo.navigation.navigateToEditProfilePage
import com.hmoa.feature_userinfo.navigation.navigateToMyActivity
import com.hmoa.feature_userinfo.navigation.navigateToMyBirth
import com.hmoa.feature_userinfo.navigation.navigateToMyCommentPage
import com.hmoa.feature_userinfo.navigation.navigateToMyFavoriteCommentPage
import com.hmoa.feature_userinfo.navigation.navigateToMyFavoritePerfume
import com.hmoa.feature_userinfo.navigation.navigateToMyGenderPage
import com.hmoa.feature_userinfo.navigation.navigateToMyInfoPage
import com.hmoa.feature_userinfo.navigation.navigateToMyPostPage
import com.hmoa.feature_userinfo.navigation.navigateToOrderRecord
import com.hmoa.feature_userinfo.navigation.navigateToRefund
import com.hmoa.feature_userinfo.navigation.navigateToRefundRecord
import com.hmoa.feature_userinfo.navigation.nestedUserInfoGraph

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
            navBack = navController::navigateToBack,
            navHome = navController::navigateToHome,
            navReview = navController::navigateToReview,
            onHbtiSurveyClick = navController::navigateToHbtiSurvey,
            onAfterOrderClick = navController::navigateToPerfumeRecommendation
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
            onNextScreen = navController::navigateToHbtiSurveyResult
        )
        hbtiProcessScreen(
            onBackClick = navController::navigateToBack,
            onNoteOrderQuantityPickClick = navController::navigateToNoteOrderQuantityPick
        )
        noteOrderQuantityPickScreen(
            onBackClick = navController::navigateToBack,
            onNextClick = { noteOrderQuantity -> navController.navigateToNotePick(noteOrderQuantity) }
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
        perfumeRecommendationRoute(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToPerfumeRecommendationResult
        )
        perfumeRecommendationResultRoute(
            onBackClick = navController::navigateToBack,
            navPerfumeDescription = { id -> navController.navigateToPerfume(id) },
            navHome = navController::navigateToHome
        )
        writeReview(navBack = navController::navigateToBack)
        review(navBack = navController::navigateToBack, navWriteReview = navController::navigateToWriteReview)
    }
}