package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyResultRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")

fun NavController.navigateToHbtiSurveyResult(surveyResult: RecommendNotesResponseDto) =
    navigate("${HbtiRoute.HbtiSurveyResult}")

fun NavGraphBuilder.hbtiScreen(onHbtiSurveyClick: () -> Unit) {
    composable(route = "${HbtiRoute.Hbti}") {
        HbtiRoute(onAfterOrderClick = {}, onHbtiSurveyClick = { onHbtiSurveyClick() })
    }
}

fun NavGraphBuilder.hbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiSurveyResultClick: (surveyResult: String) -> Unit
) {
    composable(route = "${HbtiRoute.HbtiSurvey}") {
        HbtiSurveyRoute(
            onBackClick = { onBackClick() },
            onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
            onClickHbtiSurveyResultScreen = {
            })
    }
}

fun NavGraphBuilder.hbtiSurveyResultScreen() {
    composable(
        route = "${HbtiRoute.HbtiSurveyResult}",
    ) {
        HbtiSurveyResultRoute()
    }
}