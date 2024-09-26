package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_designsystem.component.EmptyDataPage
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.MyPageCategory
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.viewModel.FavoriteCommentUiState
import com.hmoa.feature_userinfo.viewModel.FavoriteCommentViewModel

@Composable
fun MyFavoriteCommentRoute(
    navBack: () -> Unit,
    navCommunity: (Int) -> Unit,
    navPerfume : (Int) -> Unit,
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
        navBack = navBack,
        onNavParent = {
            if (type.value == MyPageCategory.향수.name){
                navPerfume(it)
            } else {
                navCommunity(it)
            }
        }
    )
}

@Composable
fun MyFavoriteCommentPage(
    navBack: () -> Unit,
    onNavParent: (Int) -> Unit,
    uiState: FavoriteCommentUiState,
    errState : ErrorUiState,
    commentType: String,
    onTypeChanged: (String) -> Unit,
) {
    var isOpen by remember { mutableStateOf(true) }

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
                navBack = navBack,
                onNavParent = onNavParent
            )
        }
        FavoriteCommentUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
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
    navBack: () -> Unit,
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
            title = "좋아요 누른 댓글",
            onNavClick = navBack //뒤로 가기
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
                EmptyDataPage(mainText = "좋아요 한 댓글이\n없습니다")
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