package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.MyPageCategory
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.viewModel.CommentUiState
import com.hmoa.feature_userinfo.viewModel.CommentViewModel

@Composable
fun MyCommentRoute(
    navBack: () -> Unit,
    navCommunity: (Int) -> Unit,
    navPerfume: (Int) -> Unit,
    viewModel: CommentViewModel = hiltViewModel()
) {
    //comment list
    val commentUiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    MyCommentPage(
        uiState = commentUiState.value,
        errState = errState.value,
        type = type.value,
        navBack = navBack,
        onNavParent = {
            if (type.value == MyPageCategory.향수.name) {
                navPerfume(it)
            } else {
                navCommunity(it)
            }
        },
        onTypeChanged = {
            viewModel.changeType(it)
        }
    )
}

@Composable
fun MyCommentPage(
    uiState: CommentUiState,
    errState: ErrorUiState,
    type: String,
    navBack: () -> Unit,
    onNavParent: (Int) -> Unit,
    onTypeChanged: (String) -> Unit
) {
    when (uiState) {
        CommentUiState.Loading -> AppLoadingScreen()
        is CommentUiState.Comments -> {
            val comments = uiState.comments.collectAsLazyPagingItems().itemSnapshotList
            MyCommentContent(
                comments = comments,
                type = type,
                onTypeChanged = onTypeChanged,
                onNavBack = navBack,
                onNavParent = onNavParent
            )
        }

        CommentUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
private fun MyCommentContent(
    comments: ItemSnapshotList<CommunityCommentDefaultResponseDto>,
    type: String,
    onTypeChanged: (String) -> Unit,
    onNavBack: () -> Unit,
    onNavParent: (Int) -> Unit,
) {
    val commentCount = comments.size

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            TypeRow(type = type, onTypeChanged = onTypeChanged)
            if (comments.isNotEmpty()) {
                LazyColumn {
                    itemsIndexed(comments) { index, comment ->
                        if (comment != null) {
                            Comment(
                                isEditable = false,
                                profile = comment.profileImg,
                                nickname = comment.nickname,
                                dateDiff = comment.createAt,
                                comment = comment.content,
                                isFirst = false,
                                heartCount = comment.heartCount,
                                navCommunity = { onNavParent(comment.parentId) },
                                onOpenBottomDialog = { /** Bottom Dialog 띄울 거면 사용 */ },
                                isSelected = comment.liked,
                                onHeartClick = {

                                }
                            )
                            if (index < commentCount - 1) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(color = CustomColor.gray2)
                                )
                            }
                        }
                    }
                }
            } else {
                EmptyDataPage(mainText = "작성한 댓글이\n없습니다")
            }
        }
    }
}

@Composable
private fun TypeRow(
    type: String,
    onTypeChanged: (type: String) -> Unit,
) {
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