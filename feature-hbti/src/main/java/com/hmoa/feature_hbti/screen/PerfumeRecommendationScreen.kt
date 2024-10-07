package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.calculateProgressStepSize
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ProgressBar
import com.hmoa.core_designsystem.component.SurveyOptionList
import com.hmoa.core_designsystem.component.TagBadge
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.loadProgress
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.data.NoteCategoryTag
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationUiState
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PerfumeRecommendationRoute(
    navNext: () -> Unit,
    navBack: () -> Unit,
    viewModel: PerfumeRecommendationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPostCompleted by viewModel.isPostCompleted.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.getSurveyResult()
    }

    LaunchedEffect(isPostCompleted) {
        if (isPostCompleted) {
            navNext()
        }
    }

    when (uiState) {
        PerfumeRecommendationUiState.Error -> {}
        PerfumeRecommendationUiState.Loading -> AppLoadingScreen()
        is PerfumeRecommendationUiState.PerfumeRecommendationData -> PerfumeRecommendationScreen(
            selectedOptionIds = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).selectedPriceOptionIds
                ?: listOf(),
            surveyQuestionTitle = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionTitle
                ?: "",
            surveyOptions = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionOptions
                ?: listOf(),
            surveyOptionIds = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.priceQuestionOptionIds
                ?: listOf(),
            noteQuestionTitle = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.noteQuestionTitle
                ?: "",
            noteCategoryTags = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).noteCategoryTags
                ?: listOf(),
            selectedNotes = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).selectedNoteTagsOption
                ?: listOf(),
            isEnabledBtn = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).isNextButtonAvailable
                ?: listOf(),
            onChangePriceOption = { optionIndex, isGoToSelectedState ->
                viewModel.handlePriceQuestionAnswer(
                    optionIndex,
                    isGoToSelectedState
                )
                viewModel.handleIsNextAvailableState()
            },
            onChangeNoteOption = { note, categoryIndex, noteIndex, isGotoState ->
                viewModel.handleNoteQuestionAnswer(
                    note,
                    categoryIndex,
                    noteIndex,
                    isGotoState
                )
                viewModel.handleIsNextAvailableState()
            },
            onDeleteTag = { tag ->
                viewModel.onDeleteNoteTagOption(tag)
            },
            onDeleteAll = { viewModel.deleteAllNoteTagOptions() },
            onNavBack = navBack,
            onClickNext = {
                viewModel.postSurveyResult()
            },
            isMultipleAnswerAvailable = (uiState as PerfumeRecommendationUiState.PerfumeRecommendationData).contents?.isPriceMultipleChoice
                ?: false,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PerfumeRecommendationScreen(
    selectedOptionIds: List<Int>,
    surveyQuestionTitle: String,
    surveyOptions: List<String>,
    surveyOptionIds: List<Int>,
    noteQuestionTitle: String,
    noteCategoryTags: List<NoteCategoryTag>,
    selectedNotes: List<String>,
    isEnabledBtn: List<Boolean>,
    isMultipleAnswerAvailable: Boolean,
    onChangePriceOption: (optionIndex: Int, isGoToSelectedState: Boolean) -> Unit,
    onChangeNoteOption: (note: String, categoryIndex: Int, noteIndex: Int, isGotoState: Boolean) -> Unit,
    onDeleteTag: (tag: String) -> Unit,
    onDeleteAll: () -> Unit,
    onNavBack: () -> Unit,
    onClickNext: () -> Unit
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
            Spacer(Modifier.height(24.dp))
            HorizontalPager(
                userScrollEnabled = false,
                modifier = Modifier.background(color = Color.White),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                if (page == 0) {
                    Spacer(Modifier.height(16.dp))
                    PriceScreen(
                        surveyQuestionTitle = surveyQuestionTitle,
                        surveyOptions = surveyOptions,
                        surveyOptionsId = surveyOptionIds,
                        isMultipleAnswerAvailable = isMultipleAnswerAvailable,
                        answerIds = selectedOptionIds,
                        onChangedValue = { optionIndex, isGoToSelectedState ->
                            onChangePriceOption(
                                optionIndex,
                                isGoToSelectedState
                            )
                        },
                        isEnabledBtn = isEnabledBtn[page],
                        onClickNext = {
                            addProgress()
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }
                    )
                }
                if (page == 1) {
                    NoteScreen(
                        noteQuestionTitle = noteQuestionTitle,
                        noteCategoryTags = noteCategoryTags,
                        onClickNote = { note, categoryIndex, noteIndex, isGotoState ->
                            onChangeNoteOption(
                                note,
                                categoryIndex,
                                noteIndex,
                                isGotoState
                            )
                        },
                        isEnabledBtn = isEnabledBtn[page],
                        selectedNotes = selectedNotes,
                        onDeleteAll = { onDeleteAll() },
                        onDeleteTag = { tag -> onDeleteTag(tag) },
                        onClickNext = { onClickNext() }
                    )
                }
            }
        }
    }
}

@Composable
private fun PriceScreen(
    surveyQuestionTitle: String,
    surveyOptions: List<String>,
    surveyOptionsId: List<Int>,
    isMultipleAnswerAvailable: Boolean,
    isEnabledBtn: Boolean,
    answerIds: List<Int>,
    onChangedValue: (optionIndex: Int, isGoToSelectedState: Boolean) -> Unit,
    onClickNext: () -> Unit
) {
    Column(
        modifier = Modifier.background(color = Color.White).fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
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
                answerIds = answerIds,
                surveyOptionIds = surveyOptionsId,
            )
        }
        Button(
            buttonModifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            radious = 5,
            isEnabled = isEnabledBtn,
            btnText = "다음",
            onClick = { onClickNext() }
        )
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
private fun NoteScreen(
    noteQuestionTitle: String,
    noteCategoryTags: List<NoteCategoryTag>,
    selectedNotes: List<String>,
    isEnabledBtn: Boolean,
    onClickNote: (note: String, categoryIndex: Int, noteIndex: Int, isGotoState: Boolean) -> Unit,
    onClickNext: () -> Unit,
    onDeleteTag: (tag: String) -> Unit,
    onDeleteAll: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val size = noteCategoryTags.size
        // 화면이 처음 로드되었을 때 맨 아래로 스크롤
        listState.scrollToItem(noteCategoryTags.lastIndex)
        // 잠시 대기
        delay(300) // 대기
        // 맨 위로 스크롤하는 애니메이션 실행
        listState.animateScrollToItem(size / 2)
        delay(400) // 대기
        listState.animateScrollToItem(0)
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        if (selectedNotes.isNotEmpty()) {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                DeletableTagBadgeScroller(
                    tags = selectedNotes,
                    onDeleteTag = { tag -> onDeleteTag(tag) },
                    onDeleteAll = { onDeleteAll() }
                )
            }
        }
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth().padding(bottom = 16.dp), state = listState) {
            item {
                Text(
                    text = "Q. ${noteQuestionTitle}",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray1)
                        .padding(bottom = 16.dp)
                )
            }
            itemsIndexed(noteCategoryTags) { categoryIndex, item ->
                Text(
                    text = "${noteCategoryTags.get(categoryIndex).category}",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
                    modifier = Modifier.padding(bottom = 16.dp).padding(top = 24.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Bottom),
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
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray1)
                        .padding(bottom = 12.dp)
                )
            }
            item() {
                Button(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    radious = 5,
                    isEnabled = isEnabledBtn,
                    btnText = "다음",
                    onClick = { onClickNext() }
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
private fun PreviewScreen() {
}