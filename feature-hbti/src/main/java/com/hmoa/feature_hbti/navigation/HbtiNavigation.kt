package com.hmoa.feature_hbti.navigation

import androidx.navigation.*
import androidx.navigation.NavType.ParcelableType
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyResultRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")

fun NavController.navigateToHbtiSurveyResult(surveyResult: RecommendNotesResponseDto) =
    navigate("${HbtiRoute.HbtiSurveyResult}/${surveyResult}")

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
            onClickHbtiSurveyResultScreen = { surveyResult ->
                val surveyResultJson = Gson().toJson(surveyResult)
                onHbtiSurveyResultClick(surveyResultJson)
            })
    }
}

fun NavGraphBuilder.hbtiSurveyResultScreen() {
    composable(
        route = "${HbtiRoute.HbtiSurveyResult}/{surveyResult}",
        arguments = listOf(navArgument("surveyResult") { type =  NavType.StringType})
    ) {
        val surveyResultJson = it.arguments?.getString("surveyResult")
        val surveyResult = Gson().fromJson(surveyResultJson, RecommendNotesResponseDto::class.java)
        HbtiSurveyResultRoute(surveyResult = surveyResult)
    }
}