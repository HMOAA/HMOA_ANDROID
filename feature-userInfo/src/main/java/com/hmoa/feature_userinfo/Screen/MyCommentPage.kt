package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.MyPageCategory
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.viewModel.CommentUiState
import com.hmoa.feature_userinfo.viewModel.CommentViewModel

@Composable
fun MyCommentRoute(
    navBack: () -> Unit,
    navCommunity: (communityId: Int) -> Unit,
    navPerfume : (perfumeId: Int) -> Unit,
    navLogin: () -> Unit,
    viewModel: CommentViewModel = hiltViewModel()
) {
    //comment list
    val commentUiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()

    MyCommentPage(
        uiState = commentUiState.value,
        errState = errState.value,
        navBack = navBack,
        navPerfume = navPerfume,
        navCommunity = navCommunity,
        onTypeChanged = viewModel::changeType,
        navLogin = navLogin
    )
}

@Composable
fun MyCommentPage(
    uiState: CommentUiState,
    errState : ErrorUiState,
    navBack: () -> Unit,
    navPerfume: (perfumeId: Int) -> Unit,
    navCommunity: (communityId: Int) -> Unit,
    navLogin: () -> Unit,
    onTypeChanged: (type: MyPageCategory) -> Unit
) {
    when (uiState) {
        CommentUiState.Loading -> AppLoadingScreen()
        is CommentUiState.Comments -> {
            val comments = uiState.comments.collectAsLazyPagingItems().itemSnapshotList
            MyCommentContent(
                comments = comments,
                onTypeChanged = onTypeChanged,
                navBack = navBack,
                navPerfume = navPerfume,
                navCommunity = navCommunity,
            )
        }
        CommentUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
private fun MyCommentContent(
    comments: ItemSnapshotList<CommunityCommentDefaultResponseDto>,
    onTypeChanged: (type: MyPageCategory) -> Unit,
    navBack: () -> Unit,
    navPerfume: (perfumeId: Int) -> Unit,
    navCommunity: (communityId: Int) -> Unit
) {
    var type by remember{mutableStateOf(MyPageCategory.향수)}
    val navParent: (parentId: Int) -> Unit = { if (type == MyPageCategory.향수) navPerfume(it) else navCommunity(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            title = "작성한 댓글",
            onNavClick = navBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            TypeRow(
                type = type,
                onTypeChanged = {
                    onTypeChanged(it)
                    type = it
                }
            )
            if (comments.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    itemsIndexed(
                        items = comments,
                        key = {_, contact -> contact?.id!!}
                    ) { index, comment ->
                        if (comment != null) {
                            Comment(
                                isEditable = false,
                                profile = comment.profileImg,
                                nickname = comment.nickname,
                                dateDiff = comment.createAt,
                                comment = comment.content,
                                isFirst = false,
                                heartCount = comment.heartCount,
                                navCommunity = { navParent(comment.parentId) },
                                onOpenBottomDialog = { /** Bottom Dialog 띄울 거면 사용 */ },
                                isSelected = comment.liked,
                                onHeartClick = {

                                }
                            )
                            if (index < comments.size - 1) {
                                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = CustomColor.gray2)
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
    type: MyPageCategory,
    onTypeChanged: (type: MyPageCategory) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        TypeBadge(
            onClickItem = { onTypeChanged(MyPageCategory.향수) },
            roundedCorner = 20.dp,
            type = "향수",
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == MyPageCategory.향수
        )
        Spacer(Modifier.width(8.dp))
        TypeBadge(
            onClickItem = { onTypeChanged(MyPageCategory.게시글) },
            roundedCorner = 20.dp,
            type = "게시글",
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == MyPageCategory.게시글
        )
    }
}
