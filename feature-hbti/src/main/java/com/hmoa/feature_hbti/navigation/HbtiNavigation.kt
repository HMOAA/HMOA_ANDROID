package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.hmoa.core_domain.entity.data.NoteOrderQuantity
import com.hmoa.core_domain.entity.navigation.HbtiRoute
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.feature_hbti.screen.AddAddressRoute
import com.hmoa.feature_hbti.screen.EditReviewRoute
import com.hmoa.feature_hbti.screen.HbtiProcessRoute
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyResultLoading
import com.hmoa.feature_hbti.screen.HbtiSurveyResultRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute
import com.hmoa.feature_hbti.screen.NoteOrderQuantityPickRoute
import com.hmoa.feature_hbti.screen.NotePickResultRoute
import com.hmoa.feature_hbti.screen.NotePickRoute
import com.hmoa.feature_hbti.screen.OrderResultRoute
import com.hmoa.feature_hbti.screen.OrderRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationResultRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationRoute
import com.hmoa.feature_hbti.screen.ReviewRoute
import com.hmoa.feature_hbti.screen.WriteReviewRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")
fun NavController.navigateToHbtiSurveyResult() =
    navigate("${HbtiRoute.HbtiSurveyResult}")
fun NavController.navigateToHbtiSurveyLoading() =
    navigate("${HbtiRoute.HbtiSurveyLoading}")
fun NavController.navigateToHbtiProcess() = navigate("${HbtiRoute.HbtiProcess}")
fun NavController.navigateToNoteOrderQuantityPick() =
    navigate("${HbtiRoute.NoteOrderQuantityPick}")

fun NavController.navigateToNotePick(noteOrderQuantity: NoteOrderQuantity) =
    navigate("${HbtiRoute.NotePick}/${noteOrderQuantity.number}")
fun NavController.navigateToPerfumeRecommendation() = navigate(HbtiRoute.PerfumeRecommendationRoute.name)
fun NavController.navigateToPerfumeRecommendationResult() = navigate(HbtiRoute.PerfumeRecommendationResultRoute.name)
fun NavController.navigateToNotePickResult(productIdsToJson: String) =
    navigate("${HbtiRoute.NotePickResultRoute.name}/${productIdsToJson}")
fun NavController.navigateToOrder(fromRoute: String, productIdsToJson: String) =
    if (fromRoute == HbtiRoute.NotePickResultRoute.name){
        navigate("${HbtiRoute.OrderRoute.name}/${productIdsToJson}")
    } else{
        navigate("${HbtiRoute.OrderRoute.name}/${productIdsToJson}"){
            popUpTo("${HbtiRoute.AddAddressRoute.name}/{addressJson}/{productIds}"){inclusive = true}
        }
    }
fun NavController.navigateToAddAddress(addressJson: String, productIds: String) = navigate("${HbtiRoute.AddAddressRoute.name}/${addressJson}/${productIds}"){
    popUpTo("${HbtiRoute.OrderRoute.name}/{productIdsToJson}"){inclusive = true}
    launchSingleTop = true
}
fun NavController.navigateToOrderResult() = navigate(HbtiRoute.OrderResultRoute.name)
//주문 리뷰 작성
fun NavController.navigateToWriteReview(orderId: Int) = navigate("${HbtiRoute.WriteReviewRoute.name}/${orderId}")
//리뷰 모음 화면
fun NavController.navigateToReview(befRoute: HbtiRoute) = navigate(HbtiRoute.ReviewRoute.name){
    if(befRoute == HbtiRoute.WriteReviewRoute){
        popUpTo("${HbtiRoute.WriteReviewRoute.name}/{orderId}"){inclusive = true}
    }
}
//리뷰 수정 화면
fun NavController.navigateToEditReview(reviewId: Int) = navigate("${HbtiRoute.EditReviewRoute.name}/${reviewId}")
fun NavGraphBuilder.hbtiScreen(
    onHbtiSurveyClick: () -> Unit,
    onAfterOrderClick: () -> Unit,
    navBack: () -> Unit,
    navHome: () -> Unit,
    navReview: (befRoute: HbtiRoute) -> Unit
) {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(
            onAfterOrderClick = onAfterOrderClick,
            onHbtiSurveyClick = onHbtiSurveyClick,
            navBack = navBack,
            navHome = navHome,
            navReview = navReview,
            navEditReview = {}
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

fun NavGraphBuilder.hbtiSurveyLoadingScreen(onNextScreen: () -> Unit) {
    composable(
        route = "${HbtiRoute.HbtiSurveyLoading}",
    ) {
        HbtiSurveyResultLoading(onNextScreen = { onNextScreen() })
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

fun NavGraphBuilder.hbtiProcessScreen(onBackClick: () -> Unit, onNoteOrderQuantityPickClick: () -> Unit) {
    composable(route = "${HbtiRoute.HbtiProcess}") {
        HbtiProcessRoute(
            onBackClick = { onBackClick() },
            onNoteOrderQuantityPickClick = { onNoteOrderQuantityPickClick() })
    }
}

fun NavGraphBuilder.noteOrderQuantityPickScreen(
    onBackClick: () -> Unit,
    onNextClick: (noteOrderQuantity: NoteOrderQuantity) -> Unit
) {
    composable(route = "${HbtiRoute.NoteOrderQuantityPick}") {
        NoteOrderQuantityPickRoute(onBackClick = { onBackClick() }, onNextClick = { onNextClick(it) })
    }
}

fun NavGraphBuilder.notePickScreen(
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    onBackToHbtiScreen: () -> Unit,
) {
    composable(
        route = "${HbtiRoute.NotePick}/{noteOrderQuantity}",
        arguments = listOf(navArgument("noteOrderQuantity") { type = NavType.IntType })
    ) {
        val noteOrderQuantity = it.arguments?.getInt("noteOrderQuantity")
        NotePickRoute(
            onBackClick = { onBackClick() },
            onNextClick = onNextClick,
            noteOrderQuantity = noteOrderQuantity,
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
    navHome: () -> Unit
) {
    composable(route = HbtiRoute.PerfumeRecommendationResultRoute.name) {
        PerfumeRecommendationResultRoute(
            navBack = { onBackClick() },
            navPerfume = { id -> navPerfumeDescription(id) },
            navHome = { navHome() }
        )
    }
}
fun NavGraphBuilder.order(
    navBack: () -> Unit,
    navAddAddress: (String, String) -> Unit,
    navOrderResult: () -> Unit,
) {
    composable(route = "${HbtiRoute.OrderRoute.name}/{productIdsToJson}") {
        val productIdsToJson = it.arguments?.getString("productIdsToJson") ?: ""
        val productIds = Json.decodeFromString<NoteProductIds>(productIdsToJson)
        OrderRoute(
            productIds = productIds.productIds,
            onNavBack = navBack,
            navAddAddress = navAddAddress,
            navOrderResult = navOrderResult
        )
    }
}

fun NavGraphBuilder.addAddress(
    navOrder: (String, String) -> Unit
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
            navOrder = navOrder
        )
    }
}

fun NavGraphBuilder.orderResult(
    navBack: () -> Unit,
    navHome: () -> Unit
){
    composable(
        route = HbtiRoute.OrderResultRoute.name
    ){
        OrderResultRoute(
            navBack = navBack,
            navHome = navHome
        )
    }
}

fun NavGraphBuilder.writeReview(
    navBack: () -> Unit
){
    composable(
        route = "${HbtiRoute.WriteReviewRoute.name}/{orderId}",
        arguments = listOf(
            navArgument("orderId"){type = NavType.IntType}
        )
    ){
        val orderId = it.arguments?.getInt("orderId")
        WriteReviewRoute(
            orderId = orderId,
            navBack = navBack
        )
    }
}

fun NavGraphBuilder.review(
    navBack: () -> Unit,
    navWriteReview: (reviewId: Int) -> Unit
){
    composable(
        route = "${HbtiRoute.ReviewRoute.name}"
    ){
        ReviewRoute(
            navBack = navBack,
            navEditReview = {},
            navWriteReview = navWriteReview
        )
    }
}

fun NavGraphBuilder.editReview(navBack: () -> Unit){
    composable(
        route = "${HbtiRoute.EditReviewRoute.name}/{reviewId}",
        arguments = listOf(navArgument("reviewId"){type = NavType.IntType})
    ){
        val reviewId = it.arguments?.getInt("reviewId")
        EditReviewRoute(
            reviewId = reviewId,
            navBack = navBack
        )
    }
}