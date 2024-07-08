package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ProgressBar
import com.hmoa.core_designsystem.component.SurveyOptionList
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun HbtiSurveyRoute() {
    HbtiSurveyScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HbtiSurveyScreen() {
    var currentProgress by remember { mutableStateOf(0f) }
    var targetProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val additionalProgress = 0.1f
    val pagerState = rememberPagerState(pageCount = { 10 })
    val seasons = listOf("싱그럽고 활기찬 '봄'", "화창하고 에너지 넘치는 '여름'", "우아하고 고요한 분위기의 '가을'", "차가움과 아늑함이 공존하는 '겨울'")


    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        TopBar(title = "향BTI", titleColor = Color.Black, navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back))
        Column(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 40.dp).fillMaxHeight(1f), verticalArrangement = Arrangement.SpaceBetween) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().background(color = Color.White).padding(top = 12.dp)
            ) {
                ProgressBar(percentage = currentProgress)
                HorizontalPager(modifier = Modifier.fillMaxWidth().padding(), state = pagerState, verticalAlignment = Alignment.Top){page ->
                    Column {
                        Text("Q. 좋아하는 계절이 있으신가요?", modifier = Modifier.padding(bottom = 32.dp, top = 36.dp),style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, fontFamily = pretendard))
                        SurveyOptionList(initValue = null, surveyOptions = seasons, onButtonClick = {})
                    }
                }
            }
            Button(isEnabled = true, btnText = "다음", onClick = {}, buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(color = Color.Black), textSize = 18, textColor = Color.White, radious = 5)
        }
    }
}

@Preview
@Composable
fun HbtiSurveyScreenPreview() {
    HbtiSurveyScreen()
}