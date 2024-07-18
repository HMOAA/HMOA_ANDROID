package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationResultRoute
import com.hmoa.feature_hbti.screen.PerfumeRecommendationRoute
import com.hmoa.feature_hbti.screen.SelectSpiceRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")
fun NavController.navigateToPerfumeRecommendation() = navigate(HbtiRoute.PerfumeRecommendationRoute.name)
fun NavController.navigateToPerfumeRecommendationResult() = navigate(HbtiRoute.PerfumeRecommendationResultRoute.name)
fun NavController.navigateToSelectSpice() = navigate(HbtiRoute.SelectSpiceRoute.name)

fun NavGraphBuilder.hbtiScreen(onHbtiSurveyClick: () -> Unit) {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(onAfterOrderClick = {}, onHbtiSurveyClick = { onHbtiSurveyClick() })
    }
}

fun NavGraphBuilder.hbtiSurveyScreen(onErrorHandleLoginAgain: () -> Unit, onBackClick: () -> Unit) {
    composable(route = "${HbtiRoute.HbtiSurvey}") {
        HbtiSurveyRoute(onBackClick = { onBackClick() }, onErrorHandleLoginAgain = { onErrorHandleLoginAgain() })
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