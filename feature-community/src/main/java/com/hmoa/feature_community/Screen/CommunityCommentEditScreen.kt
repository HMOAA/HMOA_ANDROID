package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.feature_community.ViewModel.CommunityCommentEditUiState
import com.hmoa.feature_community.ViewModel.CommunityCommentEditViewModel

@Composable
fun CommunityCommentEditRoute(
    _commentId : Int?,
    navBack : () -> Unit,
    viewModel : CommunityCommentEditViewModel = hiltViewModel()
){
    viewModel.setId(_commentId)

    val comment = viewModel.comment.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CommunityCommentEditScreen(
        uiState = uiState.value,
        comment = comment.value,
        onCommentChange = {
            viewModel.updateComment(it)
        },
        onEditDone = {
            viewModel.editComment()
            navBack()
        },
        navBack = navBack
    )
}

@Composable
fun CommunityCommentEditScreen(
    uiState : CommunityCommentEditUiState,
    comment : String,
    onCommentChange : (String) -> Unit,
    onEditDone : () -> Unit,
    navBack : () -> Unit,
){
    when(uiState){
        CommunityCommentEditUiState.Loading -> {
            AppLoadingScreen()
        }
        CommunityCommentEditUiState.Comment -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ){
                TopBarWithEvent(
                    onCancelClick = navBack,
                    onConfirmClick = onEditDone,
                    title = "댓글"
                )

                Spacer(Modifier.height(20.dp))

                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    value = comment,
                    onValueChange = {
                        onCommentChange(it)
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular))
                    )
                )
            }
        }
        CommunityCommentEditUiState.Error -> {
            Text("오류")
        }
    }
}