package com.hyangmoa.feature_userinfo

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
import com.example.feature_userinfo.viewModel.FavoriteCommentUiState
import com.example.feature_userinfo.viewModel.FavoriteCommentViewModel
import com.hyangmoa.component.TopBar
import com.hyangmoa.core_common.ErrorUiState
import com.hyangmoa.core_designsystem.component.AppLoadingScreen
import com.hyangmoa.core_designsystem.component.Comment
import com.hyangmoa.core_designsystem.component.ErrorUiSetView
import com.hyangmoa.core_designsystem.component.TypeBadge
import com.hyangmoa.core_designsystem.theme.CustomColor
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto

@Composable
fun MyFavoriteCommentRoute(
    onNavBack: () -> Unit,
    onNavCommunity: (Int) -> Unit,
    onNavPerfume: (Int) -> Unit,
    viewModel: FavoriteCommentViewModel = hiltViewModel()
) {
    //comment list
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    MyFavoriteCommentPage(
        uiState = uiState.value,
        errState = errState.value,
        commentType = type.value,
        onTypeChanged = { viewModel.changeType(it) },
        onNavBack = onNavBack,
        onNavParent = {
            if (type.value == MyPageCategory.향수.name) {
                onNavPerfume(it)
            } else {
                onNavCommunity(it)
            }
        }
    )
}

@Composable
fun MyFavoriteCommentPage(
    onNavBack: () -> Unit,
    onNavParent: (Int) -> Unit,
    uiState: FavoriteCommentUiState,
    errState: ErrorUiState,
    commentType: String,
    onTypeChanged: (String) -> Unit,
) {
    when (uiState) {
        FavoriteCommentUiState.Loading -> {
            AppLoadingScreen()
        }

        is FavoriteCommentUiState.Comments -> {
            val comments = uiState.comments.collectAsLazyPagingItems()
            FavoriteCommentContent(
                type = commentType,
                onTypeChanged = onTypeChanged,
                comments = comments.itemSnapshotList,
                onNavBack = onNavBack,
                onNavParent = onNavParent
            )
        }

        FavoriteCommentUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = onNavBack,
                errorUiState = errState,
                onCloseClick = onNavBack
            )
        }

        else -> {}
    }
}

@Composable
fun FavoriteCommentContent(
    type: String,
    onTypeChanged: (String) -> Unit,
    comments: ItemSnapshotList<CommunityCommentDefaultResponseDto>,
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
            navIcon = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_back),
            title = "좋아요 누른 댓글",
            onNavClick = onNavBack //뒤로 가기
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
                                onNavCommunity = { onNavParent(comment.parentId) },
                                onOpenBottomDialog = { /** 여기도 Bottom Dialog 사용하려면 사용합시다 */ },
                                isSelected = comment.liked,
                                onChangeSelect = {}
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
                NoDataPage(mainMsg = "좋아요 한 댓글이\n없습니다", subMsg = "댓글에 좋아요를 눌러주세요")
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