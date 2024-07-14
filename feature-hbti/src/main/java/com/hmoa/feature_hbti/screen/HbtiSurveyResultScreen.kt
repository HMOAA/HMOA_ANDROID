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
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HbtiSurveyResultRoute(surveyResult: RecommendNotesResponseDto) {
    HbtiSurveyResultScreen(surveyResult)
}

@Composable
fun HbtiSurveyResultScreen(surveyResult: RecommendNotesResponseDto) {
    var showLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000) // 2초 지연
        showLoading = false
    }

    if (showLoading) {
        HbtiSurveyResultLoading()
    } else {
        HbtiSurveyResultContent(surveyResult = surveyResult)
    }
}

@Composable
fun HbtiSurveyResultLoading() {
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
                "땡땡님에게 딱 맞는 향료를\n추천하는 중입니다.",
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
fun HbtiSurveyResultContent(surveyResult: RecommendNotesResponseDto) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { surveyResult.recommendNotes.size })
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White).padding(start = 16.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar(
            title = "향BTI",
            titleColor = Color.Black,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back)
        )
        Column() {
            Text(
                "땡땡님에게 딱 맞는 향료는\n'${surveyResult.recommendNotes[0].noteName}'입니다",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = pretendard),
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
            )
            Text(
                "2위 : ${surveyResult.recommendNotes[1].noteName}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard,
                    color = CustomColor.gray3
                ),
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                "3위 : ${surveyResult.recommendNotes[1].noteName}",
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
                    Column(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.6f)) {
                        ImageView(
                            imageUrl = surveyResult.recommendNotes[page].notePhotoUrl,
                            width = 1f,
                            height = 1f,
                            contentScale = ContentScale.Crop,
                            backgroundColor = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.6f)
                            .background(color = Color.Black)
                    ) {
                        Text(
                            "${surveyResult.recommendNotes[page].noteName}",
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(bottom = 7.dp, top = 20.dp).padding(horizontal = 20.dp)
                        )
                        Text(
                            "${surveyResult.recommendNotes[page].content}",
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
            Column(modifier = Modifier.padding(end = 16.dp)) {
                Button(
                    isEnabled = true,
                    btnText = "다음",
                    onClick = {
                        scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
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
fun HbtiSurveyResultPreview() {
    val result = RecommendNotesResponseDto(
        recommendNotes = listOf(
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
    )
    HbtiSurveyResultContent(result)
}