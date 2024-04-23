package com.hmoa.feature_perfume.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.viewmodel.EditMyPerfumeCommentViewmodel

@Composable
fun EditMyPerfumeCommentRoute(onBackClick: () -> Unit, commentId: Int?) {
    if (commentId != null) {
        EditMyPerfumeCommentScreen(onBackClick = { onBackClick() }, commentId = commentId)
    }
}

@Composable
fun EditMyPerfumeCommentScreen(
    onBackClick: () -> Unit,
    commentId: Int,
    viewModel: EditMyPerfumeCommentViewmodel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isNewPerfumeCommentSubmitFinished = viewModel.isNewPerfumeCommentSubmitedState

    LaunchedEffect(true) {
        viewModel.initializePerfumeComment(commentId = commentId)
    }

    LaunchedEffect(isNewPerfumeCommentSubmitFinished) {
        if (isNewPerfumeCommentSubmitFinished.value) {
            onBackClick()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        when (uiState) {
            is EditMyPerfumeCommentViewmodel.SpecificCommentUiState.Loading -> {}
            is EditMyPerfumeCommentViewmodel.SpecificCommentUiState.CommentData -> {
                EditMyPerfumeCommentContent(
                    onBackClick = { onBackClick() },
                    onConfirmClick = { viewModel.onSubmitPerfumeComment(commentId, text = it) },
                    data = (uiState as EditMyPerfumeCommentViewmodel.SpecificCommentUiState.CommentData).comment,
                    onContentChanged = { viewModel.onChangePerfumceComment(it) }
                )
            }

            is EditMyPerfumeCommentViewmodel.SpecificCommentUiState.Error -> {}
        }
    }
}

@Composable
fun EditMyPerfumeCommentContent(
    onBackClick: () -> Unit,
    onConfirmClick: (text: String) -> Unit,
    data: PerfumeCommentResponseDto?,
    onContentChanged: (text: String) -> Unit
) {
    var text = remember { mutableStateOf(data?.content ?: "") }

    LaunchedEffect(data) {
        text.value = data?.content ?: ""
        Log.d("EditMyPerfumeCommentScreen", "${data}")
    }

    Column {
        TopBarWithEvent(
            onCancelClick = { onBackClick() },
            onConfirmClick = { onConfirmClick(text.value) },
            title = "댓글"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 20.dp)
        ) {

            BasicTextField(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                value = text.value,
                onValueChange = {
                    text.value = it
                    onContentChanged(it)
                },
                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Black),
                cursorBrush = SolidColor(Color.Black)
            ) {
                Text(
                    text = text.value,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Black),
                )
            }
        }
    }
}
