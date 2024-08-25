package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.feature_hbti.NoteOrderQuantity
import com.hmoa.feature_hbti.screen.AddAddressRoute
import com.hmoa.feature_hbti.screen.HbtiProcessRoute
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyResultRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute
import com.hmoa.feature_hbti.screen.NoteOrderQuantityPickRoute
import com.hmoa.feature_hbti.screen.NotePickResultRoute
import com.hmoa.feature_hbti.screen.NotePickRoute
import com.hmoa.feature_hbti.screen.OrderRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationResultRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationRoute
import com.hmoa.feature_hbti.screen.SelectSpiceRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")
fun NavController.navigateToHbtiSurveyResult() =
    navigate("${HbtiRoute.HbtiSurveyResult}")

fun NavController.navigateToHbtiProcess() = navigate("${HbtiRoute.HbtiProcess}")
fun NavController.navigateToNoteOrderQuantityPick() = navigate("${HbtiRoute.NoteOrderQuantityPick}")
fun NavController.navigateToNotePick(noteOrderQuantity: NoteOrderQuantity) =
    navigate("${HbtiRoute.NotePick}/${noteOrderQuantity.number}")

fun NavController.navigateToPerfumeRecommendation() = navigate(HbtiRoute.PerfumeRecommendationRoute.name)
fun NavController.navigateToPerfumeRecommendationResult() = navigate(HbtiRoute.PerfumeRecommendationResultRoute.name)
fun NavController.navigateToSelectSpice() = navigate(HbtiRoute.SelectSpiceRoute.name)
fun NavController.navigateToNotePickResult(productIdsToJson: String) = navigate("${HbtiRoute.NotePickResultRoute.name}/${productIdsToJson}")
fun NavController.navigateToOrder(productIdsToJson: String) = navigate("${HbtiRoute.OrderRoute.name}/${productIdsToJson}")
fun NavController.navigateToAddAddress() = navigate(HbtiRoute.AddAddressRoute.name)

fun NavGraphBuilder.hbtiScreen(onHbtiSurveyClick: () -> Unit) {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(onAfterOrderClick = {}, onHbtiSurveyClick = { onHbtiSurveyClick() })
    }
}

fun NavGraphBuilder.hbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiSurveyResultClick: () -> Unit
) {
    composable(route = "${HbtiRoute.HbtiSurvey}") {
        HbtiSurveyRoute(
            onBackClick = { onBackClick() },
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onClickHbtiSurveyResultScreen = { onHbtiSurveyResultClick() }
        )
    }
}

fun NavGraphBuilder.hbtiSurveyResultScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiProcessClick: () -> Unit
) {
    composable(
        route = "${HbtiRoute.HbtiSurveyResult}",
    ) {
        HbtiSurveyResultRoute(
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onBackClick = { onBackClick() },
            onHbtiProcessClick = { onHbtiProcessClick() })
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

fun NavGraphBuilder.notePickScreen(onBackClick: () -> Unit, onNextClick: (String) -> Unit, onErrorHandleLoginAgain:()->Unit,) {
    composable(
        route = "${HbtiRoute.NotePick}/{noteOrderQuantity}",
        arguments = listOf(navArgument("noteOrderQuantity") { type = NavType.IntType })
    ) {
        val noteOrderQuantity = it.arguments?.getInt("noteOrderQuantity")
        NotePickRoute(
            onBackClick = { onBackClick() },
            onNextClick = onNextClick,
            noteOrderQuantity = noteOrderQuantity,
            onErrorHandleLoginAgain = {onErrorHandleLoginAgain()}
        )
    }
}

fun NavGraphBuilder.notePickResult(
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit,
){
    composable(route = "${HbtiRoute.NotePickResultRoute.name}/{productIdsToJson}"){
        val productIdsToJson = it.arguments?.getString("productIdsToJson")
        val gson = GsonBuilder().create()
        val productIds = gson.fromJson(productIdsToJson, NoteProductIds::class.java)
        NotePickResultRoute(
            productIds = productIds.productIds,
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}
fun NavGraphBuilder.perfumeRecommendationRoute(){
    composable(route = HbtiRoute.PerfumeRecommendationRoute.name) {
        PerfumeRecommendationRoute(
            /** navigation event 추후 추가 **/
            onNavNext = { /*TODO*/ },
            onNavBack = { /*TODO*/ }
        )
    }
}
fun NavGraphBuilder.perfumeRecommendationResultRoute(){
    composable(route = HbtiRoute.PerfumeRecommendationResultRoute.name) {
        PerfumeRecommendationResultRoute(
            /** navigation event 추후 추가 **/
            onNavBack = { /*TODO*/ },
            onNavPerfumeDesc = { /*TODO*/ }
        )
    }
}
fun NavGraphBuilder.spiceSelectScreen(){
    composable(route = HbtiRoute.SelectSpiceRoute.name) {
        SelectSpiceRoute(
            /** navigation event 추후 추가 **/
            onNavNext = { /*TODO*/ },
            onNavBack = { /*TODO*/ }
        )
    }
}
fun NavGraphBuilder.order(){
    composable(route = "${HbtiRoute.OrderRoute.name}/{productIdsToJson}"){
        val gson = GsonBuilder().create()
        val productIdsToJson = it.arguments?.getString("productIdsToJson")
        val productIds = gson.fromJson(productIdsToJson, NoteProductIds::class.java)
        OrderRoute(
            productIds = productIds.productIds,
            onNavBack = {}
        )
    }
}

fun NavGraphBuilder.addAddress(){
    composable(route = HbtiRoute.AddAddressRoute.name){
        AddAddressRoute(
            onNavBack = {}
        )
    }
}