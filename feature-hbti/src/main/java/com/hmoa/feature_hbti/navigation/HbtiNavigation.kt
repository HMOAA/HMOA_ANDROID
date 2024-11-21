package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.hmoa.core_domain.entity.navigation.HbtiRoute
import com.hmoa.core_domain.entity.navigation.HomeRoute
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.feature_hbti.screen.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}") {
    popUpTo(HomeRoute.Home.name){inclusive = false}
    launchSingleTop = true
}
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}") { launchSingleTop = true }
fun NavController.navigateToHbtiSurveyResult() =
    navigate("${HbtiRoute.HbtiSurveyResult}") { launchSingleTop = true }

fun NavController.navigateToHbtiSurveyLoading() =
    navigate("${HbtiRoute.HbtiSurveyLoading}")

fun NavController.navigateToHbtiProcess() = navigate("${HbtiRoute.HbtiProcess}")

fun NavController.navigateToNotePick() =
    navigate("${HbtiRoute.NotePick}")

fun NavController.navigateToPerfumeRecommendation() = navigate(HbtiRoute.PerfumeRecommendationRoute.name)
fun NavController.navigateToPerfumeRecommendationResult() = navigate(HbtiRoute.PerfumeRecommendationResultRoute.name)
fun NavController.navigateToNotePickResult(productIdsToJson: String) =
    navigate("${HbtiRoute.NotePickResultRoute.name}/${productIdsToJson}") { launchSingleTop = true }

fun NavController.navigateToOrder(fromRoute: String, productIdsToJson: String) =
    if (fromRoute == HbtiRoute.NotePickResultRoute.name) {
        navigate("${HbtiRoute.OrderRoute.name}/${productIdsToJson}") { launchSingleTop = true }
    } else {
        navigate("${HbtiRoute.OrderRoute.name}/${productIdsToJson}") {
            popUpTo("${HbtiRoute.AddAddressRoute.name}/{addressJson}/{productIds}") { inclusive = true }
            launchSingleTop = true
        }
    }

fun NavController.navigateToAddAddress(addressJson: String, productIds: String) =
    navigate("${HbtiRoute.AddAddressRoute.name}/${addressJson}/${productIds}") {
        popUpTo("${HbtiRoute.OrderRoute.name}/{productIdsToJson}") { inclusive = true }
        launchSingleTop = true
    }

fun NavController.navigateToOrderResult() = navigate(HbtiRoute.OrderResultRoute.name) { launchSingleTop = true }

//주문 리뷰 작성
fun NavController.navigateToWriteReview(orderId: Int) =
    navigate("${HbtiRoute.WriteReviewRoute.name}/${orderId}") { launchSingleTop = true }

//리뷰 모음 화면
fun NavController.navigateToReview(befRoute: HbtiRoute) = navigate(HbtiRoute.ReviewRoute.name) {
    if (befRoute == HbtiRoute.WriteReviewRoute) {
        popUpTo("${HbtiRoute.WriteReviewRoute.name}/{orderId}") { inclusive = true }
    } else if (befRoute == HbtiRoute.EditReviewRoute) {
        popUpTo("${HbtiRoute.EditReviewRoute.name}/{reviewId}") { inclusive = true }
    }
    launchSingleTop = true
}

//리뷰 수정 화면
fun NavController.navigateToEditReview(reviewId: Int) =
    navigate("${HbtiRoute.EditReviewRoute.name}/${reviewId}") { launchSingleTop = true }

fun NavGraphBuilder.hbtiScreen(
    onHbtiSurveyClick: () -> Unit,
    onAfterOrderClick: () -> Unit,
    navBack: () -> Unit,
    navHome: () -> Unit,
    navLogin: () -> Unit,
    navReview: (befRoute: HbtiRoute) -> Unit
) {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(
            onAfterOrderClick = onAfterOrderClick,
            onHbtiSurveyClick = onHbtiSurveyClick,
            navBack = navBack,
            navHome = navHome,
            navReview = navReview,
            navEditReview = {},
            navLogin = navLogin,
        )
    }
}

fun NavGraphBuilder.hbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    composable(route = "${HbtiRoute.HbtiSurvey}") {
        HbtiSurveyRoute(
            onBackClick = { onBackClick() },
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onClickHbtiSurveyResultScreen = { onNextClick() }
        )
    }
}

fun NavGraphBuilder.hbtiSurveyLoadingScreen(onNextScreen: () -> Unit, onBackClick: () -> Unit) {
    composable(
        route = "${HbtiRoute.HbtiSurveyLoading}",
    ) {
        HbtiSurveyResultLoading(onNextScreen = { onNextScreen() }, onBackClick = onBackClick)
    }
}

fun NavGraphBuilder.hbtiSurveyResultScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiProcessClick: () -> Unit,
) {
    composable(
        route = "${HbtiRoute.HbtiSurveyResult}",
    ) {
        HbtiSurveyResultRoute(
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onBackClick = { onBackClick() },
            onHbtiProcessClick = { onHbtiProcessClick() }
        )
    }
}

fun NavGraphBuilder.hbtiProcessScreen(navLogin: () -> Unit, onBackClick: () -> Unit, onNextClick: () -> Unit) {
    composable(route = "${HbtiRoute.HbtiProcess}") {
        HbtiProcessRoute(
            navLogin = navLogin,
            onBackClick = { onBackClick() },
            onNextClick = { onNextClick() })
    }
}

fun NavGraphBuilder.notePickScreen(
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    onBackToHbtiScreen: () -> Unit,
) {
    composable(route = "${HbtiRoute.NotePick}") {
        NotePickRoute(
            onBackClick = { onBackClick() },
            onNextClick = onNextClick,
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onBackToHbtiScreen = onBackToHbtiScreen
        )
    }
}

fun NavGraphBuilder.notePickResult(
    onBackClick: () -> Unit,
    onNextClick: (String, String) -> Unit,
    onBackToHbtiScreen: () -> Unit,
) {
    composable(route = "${HbtiRoute.NotePickResultRoute.name}/{productIdsToJson}") {
        val productIdsToJson = it.arguments?.getString("productIdsToJson")
        val gson = GsonBuilder().create()
        val productIds = gson.fromJson(productIdsToJson, NoteProductIds::class.java)
        NotePickResultRoute(
            productIds = productIds.productIds,
            onBackClick = onBackClick,
            onNextClick = onNextClick,
            onBackToHbtiScreen = onBackToHbtiScreen
        )
    }
}

fun NavGraphBuilder.perfumeRecommendationRoute(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    composable(route = HbtiRoute.PerfumeRecommendationRoute.name) {
        PerfumeRecommendationRoute(
            /** navigation event 추후 추가 **/
            navNext = { onNextClick() },
            navBack = { onBackClick() }
        )
    }
}

fun NavGraphBuilder.perfumeRecommendationResultRoute(
    onBackClick: () -> Unit,
    navPerfumeDescription: (id: Int) -> Unit,
    navHome: () -> Unit,
    navLogin: () -> Unit
) {
    composable(route = HbtiRoute.PerfumeRecommendationResultRoute.name) {
        PerfumeRecommendationResultRoute(
            navBack = { onBackClick() },
            navPerfume = { id -> navPerfumeDescription(id) },
            navHome = { navHome() },
            navLogin = navLogin
        )
    }
}
fun NavGraphBuilder.order(
    navBack: () -> Unit,
    navAddAddress: (String, String) -> Unit,
    navOrderResult: () -> Unit,
    navLogin: () -> Unit
) {
    composable(route = "${HbtiRoute.OrderRoute.name}/{productIdsToJson}") {
        val productIdsToJson = it.arguments?.getString("productIdsToJson") ?: ""
        val productIds = Json.decodeFromString<NoteProductIds>(productIdsToJson)
        OrderRoute(
            productIds = productIds.productIds,
            navBack = navBack,
            navAddAddress = navAddAddress,
            navOrderResult = navOrderResult,
            navLogin = navLogin
        )
    }
}

fun NavGraphBuilder.addAddress(
    navOrder: (String, String) -> Unit,
    navLogin: () -> Unit
) {
    composable(
        route = "${HbtiRoute.AddAddressRoute.name}/{addressJson}/{productIds}",
        arguments = listOf(
            navArgument("addressJson") { type = NavType.StringType },
            navArgument("productIds") { type = NavType.StringType }
        )
    ) {
        val addressJson = it.arguments?.getString("addressJson")
        val productIds = it.arguments?.getString("productIds")
        AddAddressRoute(
            addressJson = addressJson,
            productIds = productIds,
            navOrder = navOrder,
            navLogin = navLogin
        )
    }
}

fun NavGraphBuilder.orderResult(navHbti: () -> Unit){
    composable(route = HbtiRoute.OrderResultRoute.name){
        OrderResultRoute(navHbti = navHbti)
    }
}

fun NavGraphBuilder.writeReview(
    navBack: () -> Unit,
    navReview: (befRoute: HbtiRoute) -> Unit
) {
    composable(
        route = "${HbtiRoute.WriteReviewRoute.name}/{orderId}",
        arguments = listOf(
            navArgument("orderId") { type = NavType.IntType }
        )
    ) {
        val orderId = it.arguments?.getInt("orderId")
        WriteReviewRoute(
            orderId = orderId,
            navBack = navBack,
            navReview = navReview
        )
    }
}

fun NavGraphBuilder.review(
    navBack: () -> Unit,
    navEditReview: (Int) -> Unit,
    navLogin: () -> Unit,
    navWriteReview: (reviewId: Int) -> Unit
) {
    composable(
        route = "${HbtiRoute.ReviewRoute.name}"
    ) {
        ReviewRoute(
            navBack = navBack,
            navEditReview = navEditReview,
            navWriteReview = navWriteReview,
            navLogin = navLogin,
        )
    }
}

fun NavGraphBuilder.editReview(navReview: (befRoute: HbtiRoute) -> Unit, navLogin: () -> Unit) {
    composable(
        route = "${HbtiRoute.EditReviewRoute.name}/{reviewId}",
        arguments = listOf(navArgument("reviewId") { type = NavType.IntType })
    ) {
        val reviewId = it.arguments?.getInt("reviewId")
        EditReviewRoute(
            reviewId = reviewId,
            navReview = navReview,
            navLogin = navLogin
        )
    }
}