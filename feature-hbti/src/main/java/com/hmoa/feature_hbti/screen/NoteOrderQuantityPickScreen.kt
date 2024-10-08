package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.SurveyOptionList
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.feature_hbti.NoteOrderQuantity
import com.hmoa.feature_hbti.viewmodel.NoteOrderQuantityPickUiState
import com.hmoa.feature_hbti.viewmodel.NoteOrderQuantityPickViewmodel

@Composable
fun NoteOrderQuantityPickRoute(onBackClick: () -> Unit, onNextClick: (noteOrderQuantity: NoteOrderQuantity) -> Unit) {
    NoteOrderQuantityPickContent(onBackClick = { onBackClick() }, onNextClick = { onNextClick(it) })
}

@Composable
fun NoteOrderQuantityPickContent(
    onBackClick: () -> Unit,
    onNextClick: (noteOrderQuantity: NoteOrderQuantity) -> Unit,
    viewModel: NoteOrderQuantityPickViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPickCompleted by viewModel.isPickCompleted.collectAsStateWithLifecycle()
    val noteOrderQuantityChoice by viewModel.noteOrderQuantityChoice.collectAsStateWithLifecycle()

    LaunchedEffect(isPickCompleted) {
        if (isPickCompleted) {
            onNextClick(noteOrderQuantityChoice)
        }
    }

    when (uiState) {
        is NoteOrderQuantityPickUiState.NoteOrderQuantityPickData -> {
            Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
                TopBar(
                    title = "향BTI",
                    titleColor = Color.Black,
                    navIcon = painterResource(R.drawable.ic_back),
                    onNavClick = { onBackClick() }
                )
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(
                        "추천받은 카테고리는 '${(uiState as NoteOrderQuantityPickUiState.NoteOrderQuantityPickData).topRecommendedNote}'입니다.\n원하는 카테고리 배송 수량을\n선택해주세요",
                        modifier = Modifier.padding(bottom = 32.dp, top = 36.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = pretendard
                        )
                    )
                    SurveyOptionList(
                        initValue = (uiState as NoteOrderQuantityPickUiState.NoteOrderQuantityPickData).choiceList[0],
                        surveyOptions = (uiState as NoteOrderQuantityPickUiState.NoteOrderQuantityPickData).choiceList,
                        onButtonClick = { optionIndex ->
                            viewModel.saveNoteOrderQuantityChoice(optionIndex)
                        }
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Button(
                        isEnabled = true,
                        btnText = "다음",
                        onClick = { viewModel.changePickState(true) },
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