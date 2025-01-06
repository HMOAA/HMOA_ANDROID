package com.hmoa.feature_userinfo

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
import androidx.compose.runtime.remember
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
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.viewModel.FavoriteCommentUiState
import com.hmoa.feature_userinfo.viewModel.FavoriteCommentViewModel

@Composable
fun MyFavoriteCommentRoute(
    navBack: () -> Unit,
    navCommunity: (befRoute: CommunityRoute, communityId: Int) -> Unit,
    navPerfume: (perfumeId: Int) -> Unit,
    viewModel: FavoriteCommentViewModel = hiltViewModel()
) {
    //comment list
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val type by viewModel.type.collectAsStateWithLifecycle()
    val onCommentClick = remember<(Int) -> Unit>{{navCommunity(CommunityRoute.CommunityHomeRoute, it)}}

    MyFavoriteCommentPage(
        uiState = uiState.value,
        errState = errState.value,
        commentType = type,
        onTypeChanged = viewModel::changeType,
        navBack = navBack,
        navPerfume = navPerfume,
        navCommunity = onCommentClick,
    )
}

@Composable
fun MyFavoriteCommentPage(
    navBack: () -> Unit,
    navPerfume: (perfumeId: Int) -> Unit,
    navCommunity: (communityId: Int) -> Unit,
    uiState: FavoriteCommentUiState,
    errState: ErrorUiState,
    commentType: MyPageCategory,
    onTypeChanged: (type: MyPageCategory) -> Unit,
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
                navBack = navBack,
                navPerfume = navPerfume,
                navCommunity = navCommunity
            )
        }

        FavoriteCommentUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navBack,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
fun FavoriteCommentContent(
    type: MyPageCategory,
    onTypeChanged: (type: MyPageCategory) -> Unit,
    comments: ItemSnapshotList<CommunityCommentDefaultResponseDto>,
    navBack: () -> Unit,
    navPerfume: (perfumeId: Int) -> Unit,
    navCommunity: (communityId: Int) -> Unit
) {
    val commentCount = comments.size
    val navParent: (id: Int) -> Unit = {
        if (type == MyPageCategory.향수) { navPerfume(it) }
        else { navCommunity(it) }
    }

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
                                onOpenBottomDialog = { /** 여기도 Bottom Dialog 사용하려면 사용합시다 */ },
                                isSelected = comment.liked,
                                onHeartClick = {}
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
            type = MyPageCategory.향수.name,
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == MyPageCategory.향수
        )
        Spacer(Modifier.width(8.dp))
        TypeBadge(
            onClickItem = { onTypeChanged(MyPageCategory.게시글) },
            roundedCorner = 20.dp,
            type = MyPageCategory.게시글.name,
            fontSize = 12.sp,
            fontColor = Color.White,
            selected = type == MyPageCategory.게시글
        )
    }
}
