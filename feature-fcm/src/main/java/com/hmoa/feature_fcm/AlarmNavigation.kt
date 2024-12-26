package com.hmoa.feature_fcm

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hmoa.core_domain.entity.navigation.CommunityRoute

const val ALARM_ROUTE = "ALARM_ROUTE"

fun NavController.navigateToAlarmScreen() = navigate(ALARM_ROUTE)

fun NavGraphBuilder.alarmRoute(
    onNavBack : () -> Unit,
    onNavCommunityDesc : (befRoute: CommunityRoute, communityId: Int) -> Unit,
    onNavPerfumeComment : (Int) -> Unit,
){
    composable(route = ALARM_ROUTE){
        AlarmScreenRoute(
            onNavBack = onNavBack,
            onNavCommunityDesc = onNavCommunityDesc,
            onNavPerfumeComment = onNavPerfumeComment
        )
    }
}