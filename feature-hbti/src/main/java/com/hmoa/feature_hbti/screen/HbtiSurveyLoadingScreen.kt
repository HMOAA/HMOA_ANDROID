package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.HmoaLogoLoading
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyLoadingViewmodel
import kotlinx.coroutines.delay


@Composable
fun HbtiSurveyResultLoading(onNextScreen: () -> Unit, viewmodel: HbtiSurveyLoadingViewmodel = hiltViewModel()) {
    val userName by viewmodel.userNameState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.getUserName()
        delay(2000) // 2초 지연
        onNextScreen()
    }

    Column(modifier = Modifier.fillMaxSize().semantics { this.testTag = "HbtiSurveyResultLoading" }) {
        TopBar(title = "향BTI", titleColor = Color.Black)
        Column(
            modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HmoaLogoLoading()
            Text(
                "잠시만 기다려주세요...",
                style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            )
            Text(
                "${userName}님에게 딱 맞는 향료를\n추천하는 중입니다.",
                modifier = Modifier.padding(top = 15.dp),
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
