package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.feature_userinfo.viewModel.CommentUiState
import com.hmoa.feature_userinfo.viewModel.CommentViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.NoDataPage

@Composable
fun MyCommentRoute(
    onNavBack : () -> Unit,
    onNavCommunity : (Int) -> Unit,
    viewModel : CommentViewModel = hiltViewModel()
){
    //comment list
    val commentUiState= viewModel.uiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    MyCommentPage(
        uiState = commentUiState.value,
        type = type.value,
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity,
        onTypeChanged = {
            viewModel.changeType(it)
        }
    )
}

@Composable
fun MyCommentPage(
    uiState : CommentUiState,
    type : String,
    onNavBack : () -> Unit,
    onNavCommunity : (Int) -> Unit,
    onTypeChanged : (String) -> Unit
){
    when(uiState){
        CommentUiState.Loading -> {
            AppLoadingScreen()
        }
        is CommentUiState.Comments -> {
            val comments = uiState.comments.collectAsLazyPagingItems().itemSnapshotList
            MyCommentContent(
                comments = comments,
                type = type,
                onTypeChanged = onTypeChanged,
                onNavBack = onNavBack,
                onNavCommunity = onNavCommunity
            )
        }
        CommentUiState.Error -> {

        }
    }
}

@Composable
private fun MyCommentContent(
    comments : ItemSnapshotList<CommunityCommentDefaultResponseDto>,
    type : String,
    onTypeChanged : (String) -> Unit,
    onNavBack : () -> Unit,
    onNavCommunity : (Int) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            title = "작성한 댓글",
            onNavClick = onNavBack
        )
        TypeRow(type = type,onTypeChanged = onTypeChanged)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ){
            if (comments.isNotEmpty()){
                LazyColumn {
                    items(comments) { comment ->
                        if (comment != null){
                            Comment(
                                profile = comment.profileImg,
                                nickname = comment.nickname,
                                dateDiff = comment.createAt,
                                comment = comment.content,
                                isFirst = false,
                                heartCount = comment.heartCount,
                                onNavCommunity = { onNavCommunity(comment.parentId) },
                                onOpenBottomDialog = { /** Bottom Dialog 띄울 거면 사용 */ },
                                isSelected = comment.liked,
                                onChangeSelect = {

                                }
                            )
                        }
                    }
                }
            } else {
                NoDataPage(
                    mainMsg = "작성한 댓글이\n없습니다",
                    subMsg = "좋아하는 함수에 댓글을 작성해주세요"
                )
            }
        }
    }
}

@Composable
private fun TypeRow(
    type : String,
    onTypeChanged : (type : String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        TypeBadge(
            onClickItem = { onTypeChanged("향수") },
            roundedCorner = 20.dp,
            type = "향수",
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == "향수"
        )
        Spacer(Modifier.width(8.dp))
        TypeBadge(
            onClickItem = { onTypeChanged("게시글") },
            roundedCorner = 20.dp,
            type = "게시글",
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == "게시글"
        )
    }
}