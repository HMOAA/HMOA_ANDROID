package com.hmoa.feature_hbti.screen

import com.hmoa.feature_hbti.viewmodel.HbtiSurveyUiState
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ProgressBar
import com.hmoa.core_designsystem.component.SurveyOptionList
import com.hmoa.core_designsystem.component.loadProgress
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_model.response.SurveyOptionResponseDto
import kotlinx.coroutines.launch

@Composable
fun HbtiSurveyRoute(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
) {
    HbtiSurveyScreen(onErrorHandleLoginAgain = { onErrorHandleLoginAgain() }, onBackClick = { onBackClick })
}

@Composable
fun HbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: HbtiSurveyViewmodel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.getSurveyQuestions()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()

    ErrorUiSetView(
        onConfirmClick = { onErrorHandleLoginAgain() },
        errorUiState = errorUiState,
        onCloseClick = { onBackClick() })

    when (uiState) {
        HbtiSurveyUiState.Loading -> AppLoadingScreen()
        is HbtiSurveyUiState.HbtiData -> {
            HbtiSurveyContent(
                questions = (uiState as HbtiSurveyUiState.HbtiData).questions,
                optionsContents = (uiState as HbtiSurveyUiState.HbtiData).optionsContent,
                options = (uiState as HbtiSurveyUiState.HbtiData).options,
                onClickOption = { optionId -> },
                onClickPreviousQuestionClick = {}
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HbtiSurveyContent(
    questions: List<String>?,
    optionsContents: List<List<String>>?,
    options: List<List<SurveyOptionResponseDto>>?,
    onClickOption: (optionId: Int) -> Unit,
    onClickPreviousQuestionClick: () -> Unit
) {
    var currentProgress by remember { mutableStateOf(0f) }
    var targetProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val additionalProgress = 0.1f
    val pagerState = rememberPagerState(pageCount = { questions?.size ?: 0 })
    val seasons = listOf("싱그럽고 활기찬 '봄'", "화창하고 에너지 넘치는 '여름'", "우아하고 고요한 분위기의 '가을'", "차가움과 아늑함이 공존하는 '겨울'")

    fun addProgress() {
        targetProgress += additionalProgress
        scope.launch {
            loadProgress { progress ->
                if (currentProgress <= targetProgress) {
                    currentProgress += progress
                }
            }
        }
    }

    fun subtractProgress() {
        targetProgress -= additionalProgress
        scope.launch {
            loadProgress { progress ->
                if (currentProgress >= targetProgress) {
                    currentProgress -= progress
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        TopBar(
            title = "향BTI",
            titleColor = Color.Black,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = {
                subtractProgress()
                onClickPreviousQuestionClick()
            }
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 40.dp).fillMaxHeight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().background(color = Color.White).padding(top = 12.dp)
            ) {
                ProgressBar(percentage = currentProgress)
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Q. ${questions?.get(page)}",
                                modifier = Modifier.padding(bottom = 32.dp, top = 36.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = pretendard
                                )
                            )
                            SurveyOptionList(
                                initValue = null,
                                surveyOptions = optionsContents?.get(page)!!,
                                onButtonClick = { optionIndex ->
                                    onClickOption(
                                        options?.get(page)?.get(optionIndex)?.optionId!!
                                    )
                                }
                            )
                        }
                        Button(
                            isEnabled = true,
                            btnText = "다음",
                            onClick = {
                                addProgress()
                                scope.launch { pagerState.scrollToPage(page - 1) }
                            },
                            buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(color = Color.Black),
                            textSize = 18,
                            textColor = Color.White,
                            radious = 5
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HbtiSurveyScreenPreview() {
    HbtiSurveyContent(questions = listOf("사과는 무슨 색인가요?", "바나나는 무슨 색인가요?", "오렌지는 무슨 색인가요?"), optionsContents = listOf(
        listOf("주황", "노랑", "빨강", "파랑"), listOf("주황", "노랑", "빨강", "파랑"), listOf("주황", "노랑", "빨강", "파랑")
    ), options = null, onClickOption = {}, onClickPreviousQuestionClick = {})
}