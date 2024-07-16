package com.hmoa.feature_hbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.feature_hbti.screen.HbtiProcessRoute
import com.hmoa.feature_hbti.screen.HbtiRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyResultRoute
import com.hmoa.feature_hbti.screen.HbtiSurveyRoute

fun NavController.navigateToHbti() = navigate("${HbtiRoute.Hbti}")
fun NavController.navigateToHbtiSurvey() = navigate("${HbtiRoute.HbtiSurvey}")
fun NavController.navigateToHbtiSurveyResult() =
    navigate("${HbtiRoute.HbtiSurveyResult}")

fun NavController.navigateToHbtiProcess() = navigate("${HbtiRoute.HbtiProcess}")


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

fun NavGraphBuilder.hbtiProcessScreen(onNotePickClick: () -> Unit) {
    composable(route = "${HbtiRoute.HbtiProcess}") {
        HbtiProcessRoute(onNotePickClick = { onNotePickClick() })
    }
}