package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_perfume.viewmodel.CreateNewPerfumeViewmodel

@Composable
fun CreateNewPerfumeCommentRoute(onBackClick: () -> Unit, perfumeId: Int?) {
    if (perfumeId != null) {
        CreateNewPerfumeCommentScreen(perfumeId = perfumeId, onBackClick = { onBackClick() })
    }
}

@Composable
fun CreateNewPerfumeCommentScreen(
    perfumeId: Int,
    onBackClick: () -> Unit,
    viewModel: CreateNewPerfumeViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isNewPerfumeCommentSubmitFinished by viewModel.isNewPerfumeCommentSubmitedState.collectAsState()


    LaunchedEffect(isNewPerfumeCommentSubmitFinished) {
        if (isNewPerfumeCommentSubmitFinished) {
            onBackClick()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (uiState) {
            is CreateNewPerfumeViewmodel.NewPerfumeCommentUiState.Loading -> {}
            is CreateNewPerfumeViewmodel.NewPerfumeCommentUiState.CommentData -> {
                CreateNewPerfumeCommentContent(
                    onBackClick = { onBackClick() },
                    onConfirmClick = { viewModel.onSubmitPerfumeComment(perfumeId = perfumeId, text = it) },
                    data = (uiState as CreateNewPerfumeViewmodel.NewPerfumeCommentUiState.CommentData).comment,
                    onContentChanged = { viewModel.onChangePerfumceComment(it) }
                )
            }

            is CreateNewPerfumeViewmodel.NewPerfumeCommentUiState.Error -> {}
        }
    }
}

@Composable
fun CreateNewPerfumeCommentContent(
    onBackClick: () -> Unit,
    onConfirmClick: (text: String) -> Unit,
    data: String,
    onContentChanged: (text: String) -> Unit
) {
    Column {
        TopBarWithEvent(
            onCancelClick = { onBackClick() },
            onConfirmClick = { onConfirmClick(data) },
            title = "댓글"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 20.dp)
        ) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                value = data,
                onValueChange = {
                    onContentChanged(it)
                },
                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Black),
                cursorBrush = SolidColor(Color.Black)
            ) {
                if (data.length == 0) {
                    Text(
                        text = "해당 제품에 대한 의견을 남겨주세요",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = CustomColor.gray3),
                    )
                }
                Text(
                    text = data,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Black),
                )
            }
        }
    }
}