package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.hmoa.core_common.calculateProgressStepSize
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_model.data.NoteCategoryTag
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationUiState
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationViewModel
import kotlinx.coroutines.launch

@Composable
fun PerfumeRecommendationRoute(
    onNavNext: () -> Unit,
    onNavBack: () -> Unit,
    viewModel: PerfumeRecommendationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        PerfumeRecommendationUiState.Error -> {}
        PerfumeRecommendationUiState.Loading -> AppLoadingScreen()
        is PerfumeRecommendationUiState.PerfumeRecommendationData -> PerfumeRecommendationScreen(
            selectedOptionIds = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).selectedPriceOptionIds
                ?: emptyList(),
            onChangedOption = { optionIndex, isGoToSelectedState -> {} },
            surveyQuestionTitle = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionTitle
                ?: "",
            surveyOptions = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionOptions
                ?: emptyList(),
            surveyOptionIds = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionOptionIds
                ?: emptyList(),
            isEnabledBtn = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).isNextButtonAvailable
                ?: emptyList(),
            onClickNext = {
                viewModel.onClickNextButton()
                onNavNext()
            },
            onNavBack = onNavBack,
            isMultipleAnswerAvailable = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.isMultipleAnswerAvailable
                ?: false,
            noteQuestionTitle = (uiState.value as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.noteQuestionTitle
                ?: ""
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PerfumeRecommendationScreen(
    selectedOptionIds: List<Int>,
    onChangedOption: (optionIndex: Int, isGoToSelectedState: Boolean) -> Unit,
    surveyQuestionTitle: String,
    surveyOptions: List<String>,
    surveyOptionIds: List<Int>,
    noteQuestionTitle: String,
    isEnabledBtn: List<Boolean>,
    isMultipleAnswerAvailable: Boolean,
    onClickNext: () -> Unit,
    onNavBack: () -> Unit,
) {
    val questionTitles = listOf(surveyQuestionTitle, noteQuestionTitle)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { questionTitles.size })
    var currentProgress by remember { mutableStateOf(0f) }
    var targetProgress by remember { mutableStateOf(0f) }
    val additionalProgress = calculateProgressStepSize(questionTitles)
    val scope = rememberCoroutineScope() // Create a coroutine scope

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 40.dp)
    ) {
        TopBar(
            title = "향BTI",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = {
                if (pagerState.currentPage == 0) {
                    onNavBack()
                } else {
                    subtractProgress()
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                }
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(15.dp))
            ProgressBar(percentage = currentProgress)
            Spacer(Modifier.height(40.dp))
            HorizontalPager(
                userScrollEnabled = false,
                modifier = Modifier.fillMaxWidth().background(color = Color.White),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                if (page == 0) {
                    PriceScreen(
                        modifier = Modifier.weight(1f),
                        surveyQuestionTitle = surveyQuestionTitle,
                        surveyOptions = surveyOptions,
                        surveyOptionsId = surveyOptionIds,
                        isMultipleAnswerAvailable = isMultipleAnswerAvailable,
                        answerIds = selectedOptionIds,
                        onChangedValue = { optionIndex, isGoToSelectedState ->
                            onChangedOption(
                                optionIndex,
                                isGoToSelectedState
                            )
                        }
                    )
                }
                if (page == 1) {

                }
                Button(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    isEnabled = isEnabledBtn.get(page),
                    btnText = "다음",
                    onClick = {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            addProgress()
                        }
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                )

            }
        }
    }
}

@Composable
private fun PriceScreen(
    modifier: Modifier,
    surveyQuestionTitle: String,
    surveyOptions: List<String>,
    surveyOptionsId: List<Int>,
    isMultipleAnswerAvailable: Boolean,
    answerIds: List<Int>?,
    onChangedValue: (optionIndex: Int, isGoToSelectedState: Boolean) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Q. ${surveyQuestionTitle}",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_semi_bold))
        )
        Spacer(Modifier.height(32.dp))
        SurveyOptionList(
            isMutipleAnswerAvailable = isMultipleAnswerAvailable,
            surveyOptions = surveyOptions,
            onButtonClick = { optionIndex, isGoToSelectedState ->
                onChangedValue(optionIndex, isGoToSelectedState)
            },
            answerIds = answerIds ?: emptyList(),
            surveyOptionIds = surveyOptionsId,
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NoteScreen(
    noteQuestionTitle: String,
    noteCategoryTags: List<NoteCategoryTag>,
    onClickNote: (note: String, categoryIndex: Int, noteIndex: Int, isGotoState: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize(1f).verticalScroll(scrollState)) {
        Text(
            text = "Q. ${noteQuestionTitle}",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_semi_bold))
        )
        LazyColumn() {
            itemsIndexed(noteCategoryTags) { categoryIndex, item ->
                Text(
                    text = "Q. ${noteCategoryTags.get(categoryIndex)}",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semi_bold))
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    noteCategoryTags.get(categoryIndex).note.forEachIndexed { noteIndex, noteName ->
                        val isSelected = noteCategoryTags.get(categoryIndex).isSelected.get(noteIndex)
                        TagBadge(
                            tag = noteName,
                            isClickable = true,
                            isSelected = isSelected,
                            onClick = { onClickNote(it, categoryIndex, noteIndex, !isSelected) },
                            height = 32.dp
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}


@Preview
@Composable
private fun PreviewScreen() {
    val surveyOptions = listOf("5만원 이하", "5만원 이상", "10만원 이상", "가격 상관 없음")

//    PerfumeRecommendationScreen(
//        selectedOptionIds = listOf(0),
//        onChangedOption = { optionIndex, isGoToSelectedState -> },
//        surveyOptions = surveyOptions,
//        isEnabledBtn = listOf(true, true),
//        onNavBack = {},
//        onClickNext = {},
//        isMultipleAnswerAvailable = false,
//        surveyOptionIds = listOf(0, 1, 2, 3),
//        surveyQuestionTitle = "선호하시는 가격대를 설정해주세요",
//        noteQuestionTitle = "시향 후 마음에 드는 향료를 골라주세요"
//    )
    NoteScreen(
        "시향 후 마음에 드는 향료를 골라주세요",
        listOf(NoteCategoryTag(category = "스파이스", note = listOf("통카빈", "페퍼"), isSelected = listOf(false, false))),
        {note, categoryIndex, noteIndex, isGotoState ->  {}})
}