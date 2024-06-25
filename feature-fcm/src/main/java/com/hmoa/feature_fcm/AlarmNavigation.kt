package com.hmoa.feature_fcm

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ALARM_ROUTE = "ALARM_ROUTE"

fun NavController.navigateToAlarmScreen() = navigate(ALARM_ROUTE)

fun NavGraphBuilder.alarmRoute(
    onNavBack : () -> Unit
){
    composable(route = ALARM_ROUTE){
        AlarmScreenRoute(onNavBack = onNavBack)
    }
}