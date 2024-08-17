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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyResultUiState
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyResultViewmodel
import kotlinx.coroutines.delay

@Composable
fun HbtiSurveyResultRoute(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiProcessClick: () -> Unit
) {
    HbtiSurveyResultScreen(
        onErrorHandleLoginAgain = { onErrorHandleLoginAgain() },
        onBackClick = { onBackClick() },
        onHbtiProcessClick = { onHbtiProcessClick() })
}

@Composable
private fun HbtiSurveyResultScreen(
    onErrorHandleLoginAgain: () -> Unit,
    onBackClick: () -> Unit,
    onHbtiProcessClick: () -> Unit,
    viewmodel: HbtiSurveyResultViewmodel = hiltViewModel()
) {
    var showLoading by remember { mutableStateOf(true) }
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewmodel.errorUiState.collectAsStateWithLifecycle()

    ErrorUiSetView(
        onConfirmClick = { onErrorHandleLoginAgain() },
        errorUiState = errorUiState,
        onCloseClick = { onBackClick() }
    )

    LaunchedEffect(Unit) {
        delay(2000) // 2초 지연
        showLoading = false
    }

    when (uiState) {
        is HbtiSurveyResultUiState.HbtiSurveyResultData -> {
            if (showLoading) {
                HbtiSurveyResultLoading((uiState as HbtiSurveyResultUiState.HbtiSurveyResultData).userName)
            } else {
                HbtiSurveyResultContent(
                    surveyResult = (uiState as HbtiSurveyResultUiState.HbtiSurveyResultData).surveyResult,
                    onHbtiProcessClick = { onHbtiProcessClick() },
                    userName = (uiState as HbtiSurveyResultUiState.HbtiSurveyResultData).userName
                )
            }
        }

        HbtiSurveyResultUiState.Loading -> HbtiSurveyResultLoading("   ")
    }
}

@Composable
private fun HbtiSurveyResultLoading(userName: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "향BTI", titleColor = Color.Black)
        Column(
            modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HbtiSurveyResultContent(
    surveyResult: List<NoteResponseDto>,
    onHbtiProcessClick: () -> Unit,
    userName: String
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { surveyResult.size })
    if (surveyResult.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Color.White).padding(bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TopBar(
                    title = "향BTI",
                    titleColor = Color.Black,
                    navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        "${userName}님에게 딱 맞는 향료는\n'${surveyResult[0].noteName}'입니다",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = pretendard),
                        modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
                    )
                    Text(
                        "2위 : ${surveyResult[1].noteName}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = pretendard,
                            color = CustomColor.gray3
                        ),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        "3위 : ${surveyResult[2].noteName}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = pretendard,
                            color = CustomColor.gray3
                        )
                    )
                    HorizontalPager(
                        verticalAlignment = Alignment.Bottom,
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth().padding(top = 29.dp),
                        contentPadding = PaddingValues(end = 80.dp)
                    ) { page ->
                        Column(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.8f).padding(end = 15.dp)) {
                            Column(
                                modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.6f)
                            ) {
                                ImageView(
                                    imageUrl = surveyResult[page].notePhotoUrl,
                                    width = 1f,
                                    height = 1f,
                                    contentScale = ContentScale.Crop,
                                    backgroundColor = Color.White
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .background(color = Color.Black)
                            ) {
                                Text(
                                    "${surveyResult[page].noteName}",
                                    style = TextStyle(
                                        fontFamily = pretendard,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    ),
                                    modifier = Modifier.padding(bottom = 7.dp, top = 20.dp).padding(horizontal = 20.dp)
                                )
                                Text(
                                    "${surveyResult[page].content}",
                                    style = TextStyle(
                                        fontFamily = pretendard,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light,
                                        color = Color.White
                                    ),
                                    modifier = Modifier.padding(bottom = 20.dp).padding(horizontal = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(
                    isEnabled = true,
                    btnText = "다음",
                    onClick = {
                        onHbtiProcessClick()
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

@Preview
@Composable
private fun HbtiSurveyResultPreview() {
    val result = listOf(
        NoteResponseDto(
            noteId = 0,
            noteName = "시트러스",
            notePhotoUrl = "",
            content = "귤, 베르가못, 만다린이 들어간 상큼한 향료로 향수에서 가장 많이 사용되는 노트입니다."
        ),
        NoteResponseDto(
            noteId = 1,
            noteName = "플로럴",
            notePhotoUrl = "",
            content = "네롤리, 화이트 로즈, 핑크 로즈가 들어간 향료로 향수에서 가장 많이 사용되는 노트입니다."
        ),
        NoteResponseDto(
            noteId = 2,
            noteName = "스파이스",
            notePhotoUrl = "",
            content = "넛맥, 블랙페퍼가 들어간 향료로 제가 좋아하는 향료입니다"
        )
    )

    HbtiSurveyResultContent(result, {})
}