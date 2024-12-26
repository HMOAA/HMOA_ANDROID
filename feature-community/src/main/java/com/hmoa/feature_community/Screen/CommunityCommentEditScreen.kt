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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.TopBarWithEvent
import com.hmoa.feature_community.ViewModel.CommunityCommentEditUiState
import com.hmoa.feature_community.ViewModel.CommunityCommentEditViewModel

@Composable
fun CommunityCommentEditRoute(
    _commentId : Int?,
    navBack : () -> Unit,
    navLogin: () -> Unit,
    viewModel : CommunityCommentEditViewModel = hiltViewModel()
){
    LaunchedEffect(Unit){ viewModel.setId(_commentId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()

    CommunityCommentEditScreen(
        uiState = uiState,
        errState = errState,
        onEditDone = viewModel::editComment,
        navBack = navBack,
        navLogin = navLogin
    )
}

@Composable
fun CommunityCommentEditScreen(
    uiState: CommunityCommentEditUiState,
    errState: ErrorUiState,
    onEditDone: (newComment: String, onSuccess: () -> Unit) -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit,
){
    when(uiState){
        CommunityCommentEditUiState.Loading -> {
            AppLoadingScreen()
        }
        is CommunityCommentEditUiState.Comment -> {
            CommunityCommentEditContent(
                initComment = uiState.comment,
                navBack = navBack,
                onEditDone = onEditDone
            )
        }
        CommunityCommentEditUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
fun CommunityCommentEditContent(
    initComment: String,
    navBack: () -> Unit,
    onEditDone: (newComment: String, onSuccess: () -> Unit) -> Unit,
){
    var comment by remember{mutableStateOf(initComment)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBarWithEvent(
            onCancelClick = navBack,
            onConfirmClick = {
                onEditDone(comment, navBack)
            },
            title = "댓글"
        )

        Spacer(Modifier.height(20.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            value = comment,
            onValueChange = {comment = it},
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular))
            )
        )
    }
}