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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.calculateHbtiProgressStepSize
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_domain.entity.data.HbtiQuestionItem
import com.hmoa.core_domain.entity.data.HbtiQuestionItems
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyUiState
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel
import kotlinx.coroutines.launch

@Composable
fun HbtiSurveyRoute(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onClickHbtiSurveyResultScreen: () -> Unit,
    viewModel: HbtiSurveyViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getSurveyQuestions()
    }

    HbtiSurveyScreen(
        onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
        onBackClick = { onBackClick() },
        onClickFinishSurvey = { onClickHbtiSurveyResultScreen() },
        viewModel = viewModel,
        errorUiState = errorUiState,
        uiState = uiState
    )
}

@Composable
fun HbtiSurveyScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onClickFinishSurvey: () -> Unit,
    viewModel: HbtiSurveyViewmodel,
    errorUiState: ErrorUiState,
    uiState: HbtiSurveyUiState
) {
    val scope = rememberCoroutineScope()

    ErrorUiSetView(
        onLoginClick = { onErrorHandleLoginAgain() },
        errorUiState = errorUiState,
        onCloseClick = onBackClick
    )

    when (uiState) {
        HbtiSurveyUiState.Loading -> AppLoadingScreen()
        is HbtiSurveyUiState.HbtiData -> {
            HbtiSurveyContent(
                hbtiQuestionItems = uiState.hbtiQuestionItems,
                hbtiAnswerIds = uiState.hbtiAnswerIds,
                isNextQuestionAvailable = uiState.isNextQuestionAvailable,
                onClickOption = { optionId, page, item, isGoToSelectedState ->
                    viewModel.modifyAnswersToOptionId(
                        optionId = optionId,
                        page = page,
                        currentHbtiQuestionItem = item,
                        isGoToSelectedState = isGoToSelectedState
                    )
                },
                onClickFinishSurvey = {
                    scope.launch {
                        launch { viewModel.finishSurvey() }.join()
                        onClickFinishSurvey()
                    }
                },
                onBackClick = { onBackClick() }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HbtiSurveyContent(
    hbtiQuestionItems: HbtiQuestionItems?,
    hbtiAnswerIds: List<List<Int>>?,
    isNextQuestionAvailable: List<Boolean>?,
    onClickOption: (optionId: Int, page: Int, item: HbtiQuestionItem, isGoToSelectedState: Boolean) -> Unit,
    onClickFinishSurvey: () -> Unit,
    onBackClick: () -> Unit
) {

    var currentProgress by remember { mutableStateOf(0f) }
    var targetProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { hbtiQuestionItems?.questionCounts ?: 0 })
    val additionalProgress = calculateHbtiProgressStepSize(hbtiQuestionItems?.questionCounts ?: 13)
    fun addProgress() {
        targetProgress += additionalProgress
        scope.launch {
            loadProgress(additionalProgress) { progress ->
                if (currentProgress <= targetProgress) {
                    currentProgress += progress
                }
            }
        }
    }

    fun subtractProgress() {
        targetProgress -= additionalProgress
        scope.launch {
            loadProgress(additionalProgress) { progress ->
                if (currentProgress >= targetProgress) {
                    currentProgress -= progress
                }
            }
        }
    }

    fun preventScrollOver2Pages(currentPage: Int, targetPage: Int) {
        if (kotlin.math.abs(targetPage - currentPage) > 1) {
            scope.launch { pagerState.animateScrollToPage(currentPage) }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.targetPage }
            .collect { targetPage ->
                val currentPage = pagerState.currentPage
                preventScrollOver2Pages(currentPage, targetPage)
                if (currentPage > targetPage) {
                    subtractProgress()
                } else if (currentPage < targetPage) {
                    addProgress()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        TopBar(
            title = "향BTI",
            titleColor = Color.Black,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onBackClick
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(1f).background(color = Color.White)
                .padding(top = 12.dp).padding(horizontal = 16.dp)
                .semantics { testTag = "HbtiSurveyForm" }
        ) {
            ProgressBar(percentage = currentProgress)
            HorizontalPager(
                userScrollEnabled = isNextQuestionAvailable?.get(pagerState.currentPage) ?: true,
                modifier = Modifier.fillMaxWidth().background(color = Color.White).fillMaxHeight(0.85f),
                state = pagerState,
                verticalAlignment = Alignment.Top,
            ) { page ->
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Q. ${hbtiQuestionItems?.hbtiQuestions?.get(page)?.questionContent}",
                        modifier = Modifier.padding(bottom = 32.dp, top = 36.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = CustomFont.regular
                        )
                    )
                    SurveyOptionList(
                        isMutipleAnswerAvailable = hbtiQuestionItems?.hbtiQuestions?.get(page)?.isMultipleChoice!!,
                        answerIds = hbtiAnswerIds?.get(page) ?: emptyList(),
                        surveyOptions = hbtiQuestionItems?.hbtiQuestions?.get(page)?.optionContents
                            ?: listOf(),
                        surveyOptionIds = hbtiQuestionItems?.hbtiQuestions?.get(page)?.optionIds
                            ?: listOf(),
                        onButtonClick = { optionIndex, isGoToSelectedState ->
                            onClickOption(
                                hbtiQuestionItems?.hbtiQuestions?.get(page)!!.optionIds[optionIndex],
                                page,
                                hbtiQuestionItems?.hbtiQuestions?.get(page)!!,
                                isGoToSelectedState
                            )
                        }
                    )
                }
            }
            Column(modifier = Modifier.padding(bottom = 40.dp)) {
                if (pagerState.currentPage < pagerState.pageCount - 1) {
                    Button(
                        isEnabled = isNextQuestionAvailable?.get(pagerState.currentPage) ?: true,
                        btnText = "다음",
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(
                            color = if (isNextQuestionAvailable?.get(pagerState.currentPage)
                                    ?: true
                            ) Color.Black else CustomColor.gray3
                        ),
                        textSize = 18,
                        textColor = Color.White,
                        radious = 5
                    )
                } else {
                    Button(
                        isEnabled = isNextQuestionAvailable?.get(pagerState.currentPage) ?: true,
                        btnText = "다음",
                        onClick = {
                            onClickFinishSurvey()
                        },
                        buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(
                            color = if (isNextQuestionAvailable?.get(pagerState.currentPage)
                                    ?: true
                            ) Color.Black else CustomColor.gray3
                        )
                            .semantics { testTag = "NextButton" },
                        textSize = 18,
                        textColor = Color.White,
                        radious = 5
                    )
                }
            }
        }
    }
}
