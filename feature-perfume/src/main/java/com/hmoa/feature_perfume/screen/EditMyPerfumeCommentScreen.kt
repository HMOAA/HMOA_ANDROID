package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.viewmodel.EditMyPerfumeCommentViewmodel
import com.hmoa.feature_perfume.viewmodel.SpecificCommentViewmodel

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
    val isNewPerfumeCommentSubmitFinished =
        (uiState as SpecificCommentViewmodel.SpecificCommentUiState.CommentData).isLikeComment
    LaunchedEffect(true) {
        viewModel.initializePerfumeComment(commentId = commentId)
    }

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
            is EditMyPerfumeCommentViewmodel.SpecificCommentUiState.Loading -> {}
            is EditMyPerfumeCommentViewmodel.SpecificCommentUiState.CommentData -> {
                EditMyPerfumeCommentContent(
                    onBackClick = { onBackClick() },
                    onConfirmClick = { viewModel.onSubmitPerfumeComment(commentId, text = it) },
                    data = (uiState as SpecificCommentViewmodel.SpecificCommentUiState.CommentData).comment,
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
    MyPerfumeCommentContent(
        data = data,
        onBackClick = { onBackClick() },
        onConfirmClick = { onConfirmClick(data!!.content) },
        onContentChanged = { onContentChanged(it) })
}

@Composable
fun ModifyButtonTopBar(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "취소",
            fontSize = 16.sp,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.clickable { onCancelClick() }
        )
        Text(
            text = "댓글",
            fontSize = 20.sp,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        Text(
            text = "확인",
            fontSize = 16.sp,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.clickable { onConfirmClick() }
        )
    }
}


@Composable
fun MyPerfumeCommentContent(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    data: PerfumeCommentResponseDto?,
    onContentChanged: (text: String) -> Unit
) {
    Column {
        ModifyButtonTopBar(onCancelClick = { onBackClick() }, onConfirmClick = { onConfirmClick() })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 20.dp)
        ) {
            //content input
            BasicTextField(
                value = data?.content ?: "",
                onValueChange = {
                    onContentChanged(it)
                },
                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
            ) {
                //placeholder
                if (data?.content?.length == 0) {
                    Text(
                        text = "내용을 입력해주세요",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light, color = CustomColor.gray3),
                    )
                }
            }
        }
    }
}