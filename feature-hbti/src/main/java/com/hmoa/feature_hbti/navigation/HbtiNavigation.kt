package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.hmoa.core_model.data.NoteProductIds
import com.hmoa.core_domain.entity.data.NoteOrderQuantity
import com.hmoa.feature_hbti.screen.AddAddressRoute
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
import com.hmoa.feature_hbti.screen.SelectSpiceRoute
import kotlinx.serialization.json.Json

fun NavController.navigateToHbti() = navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurvey}")
fun NavController.navigateToHbtiSurveyResult() =
    navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurveyResult}")
fun NavController.navigateToHbtiSurveyLoading() =
    navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurveyLoading}")
fun NavController.navigateToHbtiProcess() = navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiProcess}")
fun NavController.navigateToNoteOrderQuantityPick() = navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.NoteOrderQuantityPick}")
fun NavController.navigateToNotePick(noteOrderQuantity: NoteOrderQuantity) =
    navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.NotePick}/${noteOrderQuantity.number}")
fun NavController.navigateToPerfumeRecommendation() = navigate(com.hmoa.core_domain.entity.navigation.HbtiRoute.PerfumeRecommendationRoute.name)
fun NavController.navigateToPerfumeRecommendationResult() = navigate(com.hmoa.core_domain.entity.navigation.HbtiRoute.PerfumeRecommendationResultRoute.name)
fun NavController.navigateToSelectSpice() = navigate(com.hmoa.core_domain.entity.navigation.HbtiRoute.SelectSpiceRoute.name)
fun NavController.navigateToNotePickResult(productIdsToJson: String) =
    navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.NotePickResultRoute.name}/${productIdsToJson}")
fun NavController.navigateToOrder(fromRoute: String, productIdsToJson: String) =
    if (fromRoute == com.hmoa.core_domain.entity.navigation.HbtiRoute.NotePickResultRoute.name){
        navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderRoute.name}/${productIdsToJson}")
    } else{
        navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderRoute.name}/${productIdsToJson}"){
            popUpTo("${com.hmoa.core_domain.entity.navigation.HbtiRoute.AddAddressRoute.name}/{addressJson}/{productIds}"){inclusive = true}
        }
    }
fun NavController.navigateToAddAddress(addressJson: String, productIds: String) = navigate("${com.hmoa.core_domain.entity.navigation.HbtiRoute.AddAddressRoute.name}/${addressJson}/${productIds}"){
    popUpTo("${com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderRoute.name}/{productIdsToJson}"){inclusive = true}
    launchSingleTop = true
}
fun NavController.navigateToOrderResult() = navigate(com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderResultRoute.name)

fun NavGraphBuilder.hbtiScreen(onNextClick: () -> Unit) {
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.Hbti}") {
        HbtiRoute(onAfterOrderClick = {}, onHbtiSurveyClick = { onNextClick() })
    }
}

fun NavGraphBuilder.hbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurvey}") {
        HbtiSurveyRoute(
            onBackClick = { onBackClick() },
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onClickHbtiSurveyResultScreen = { onNextClick() }
        )
    }
}

fun NavGraphBuilder.hbtiSurveyLoadingScreen(onNextScreen: () -> Unit) {
    composable(
        route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurveyLoading}",
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
        route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiSurveyResult}",
    ) {
        HbtiSurveyResultRoute(
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onBackClick = { onBackClick() },
            onHbtiProcessClick = { onHbtiProcessClick() }
        )
    }
}

fun NavGraphBuilder.hbtiProcessScreen(onBackClick: () -> Unit, onNoteOrderQuantityPickClick: () -> Unit) {
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.HbtiProcess}") {
        HbtiProcessRoute(
            onBackClick = { onBackClick() },
            onNoteOrderQuantityPickClick = { onNoteOrderQuantityPickClick() })
    }
}

fun NavGraphBuilder.noteOrderQuantityPickScreen(
    onBackClick: () -> Unit,
    onNextClick: (noteOrderQuantity: NoteOrderQuantity) -> Unit
) {
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.NoteOrderQuantityPick}") {
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
        route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.NotePick}/{noteOrderQuantity}",
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
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.NotePickResultRoute.name}/{productIdsToJson}") {
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

fun NavGraphBuilder.perfumeRecommendationRoute() {
    composable(route = com.hmoa.core_domain.entity.navigation.HbtiRoute.PerfumeRecommendationRoute.name) {
        PerfumeRecommendationRoute(
            /** navigation event 추후 추가 **/
            onNavNext = { /*TODO*/ },
            onNavBack = { /*TODO*/ }
        )
    }
}

fun NavGraphBuilder.perfumeRecommendationResultRoute() {
    composable(route = com.hmoa.core_domain.entity.navigation.HbtiRoute.PerfumeRecommendationResultRoute.name) {
        PerfumeRecommendationResultRoute(
            /** navigation event 추후 추가 **/
            onNavBack = { /*TODO*/ },
            onNavPerfumeDesc = { /*TODO*/ }
        )
    }
}

fun NavGraphBuilder.spiceSelectScreen() {
    composable(route = com.hmoa.core_domain.entity.navigation.HbtiRoute.SelectSpiceRoute.name) {
        SelectSpiceRoute(
            /** navigation event 추후 추가 **/
            onNavNext = { /*TODO*/ },
            onNavBack = { /*TODO*/ }
        )
    }
}

fun NavGraphBuilder.order(
    navBack: () -> Unit,
    navAddAddress: (String, String) -> Unit,
    navOrderResult: () -> Unit,
) {
    composable(route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderRoute.name}/{productIdsToJson}") {
        val productIdsToJson = it.arguments?.getString("productIdsToJson")
        val productIds = Json.decodeFromString<NoteProductIds>(productIdsToJson ?: "")
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
        route = "${com.hmoa.core_domain.entity.navigation.HbtiRoute.AddAddressRoute.name}/{addressJson}/{productIds}",
        arguments = listOf(
            navArgument("addressJson"){type = NavType.StringType},
            navArgument("productIds"){type = NavType.StringType}
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
        route = com.hmoa.core_domain.entity.navigation.HbtiRoute.OrderResultRoute.name
    ){
        OrderResultRoute(
            navBack = navBack,
            navHome = navHome
        )
    }
}