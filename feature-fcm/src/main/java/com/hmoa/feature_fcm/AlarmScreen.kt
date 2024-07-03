package com.hmoa.feature_fcm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AlarmItem
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_model.response.AlarmResponse

@Composable
fun AlarmScreenRoute(
    onNavBack : () -> Unit,
    viewModel : AlarmViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorUiState.collectAsStateWithLifecycle()
    AlarmScreen(
        uiState = uiState.value,
        errState = errorState.value,
        onNavBack = onNavBack
    )
}

@Composable
fun AlarmScreen(
    uiState: AlarmUiState,
    errState: ErrorUiState,
    onNavBack: () -> Unit
){
    when(uiState){
        AlarmUiState.Loading -> AppLoadingScreen()
        AlarmUiState.Error -> {
            ErrorUiSetView(
                onConfirmClick = onNavBack,
                errorUiState = errState,
                onCloseClick = onNavBack
            )
        }
        is AlarmUiState.Success -> {
            AlarmContent(
                alarms = uiState.alarms,
                onNavBack = onNavBack,
            )
        }
    }
}

@Composable
private fun AlarmContent(
    alarms : List<AlarmResponse>,
    onNavBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        //top bar
        TopBar(
            title = "H M O A",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
        )
        if (alarms.isNotEmpty()){
            LazyColumn{
                items(alarms){ alarm ->
                    AlarmItem(
                        height = 94.dp,
                        isRead = alarm.read,
                        category = alarm.title,
                        content = alarm.content,
                        time = alarm.createdAt
                    )
                }
            }
        } else {
            EmptyScreen()
        }
    }
}

@Composable
private fun EmptyScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier.size(110.dp),
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_app_default_1),
            contentDescription = "App Icon"
        )
        Spacer(Modifier.height(34.dp))
        Text(
            text = "HMOA",
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold)),
            letterSpacing = 8.sp,
        )
        Spacer(Modifier.height(22.dp))
        Text(
            text = "알림이 없습니다",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAlarmScreen(){
    AlarmScreen(
        onNavBack = {},
        uiState = AlarmUiState.Success(listOf(
            AlarmResponse(
                content = "지금 향모아만의 초특가 할인 상품을 만나보세요",
                createdAt = "10.04 14:30",
                deeplink = "",
                id = 0,
                read = true,
                title = "Event",
                senderProfileImg = null
            )
        )),
        errState = ErrorUiState.Loading
    )
}