package com.hmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.feature_userinfo.viewModel.FavoriteCommentUiState
import com.example.feature_userinfo.viewModel.FavoriteCommentViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppDefaultDialog
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Comment
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto

@Composable
fun MyFavoriteCommentRoute(
    onNavBack: () -> Unit,
    onNavCommunity: (Int) -> Unit,
    viewModel: FavoriteCommentViewModel = hiltViewModel()
) {

    //comment list
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    MyFavoriteCommentPage(
        uiState = uiState.value,
        commentType = type.value,
        onTypeChanged = {viewModel.changeType(it)},
        onNavBack = onNavBack,
        onNavCommunity = onNavCommunity
    )
}

@Composable
fun MyFavoriteCommentPage(
    onNavBack: () -> Unit,
    onNavCommunity: (Int) -> Unit,
    uiState: FavoriteCommentUiState,
    commentType: String,
    onTypeChanged: (String) -> Unit,
) {
    when(uiState){
        FavoriteCommentUiState.Loading -> {
            AppLoadingScreen()
        }
        is FavoriteCommentUiState.Comments -> {
            val comments = uiState.comments.collectAsLazyPagingItems()
            if (comments.itemSnapshotList.isNotEmpty()){
                FavoriteCommentContent(
                    type = commentType,
                    onTypeChanged = onTypeChanged,
                    comments = comments.itemSnapshotList,
                    onNavBack = onNavBack,
                    onNavCommunity = onNavCommunity
                )
            } else {
                NoDataPage(
                    mainMsg = "작성한 댓글이\n없습니다",
                    subMsg = "좋아하는 함수에 댓글을 작성해주세요"
                )
            }
        }
        FavoriteCommentUiState.Error -> {

        }
        else ->{}
    }
}
@Composable
fun FavoriteCommentContent(
    type : String,
    onTypeChanged: (String) -> Unit,
    comments : ItemSnapshotList<CommunityCommentDefaultResponseDto>,
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
            title = "좋아요 누른 댓글",
            onNavClick = onNavBack //뒤로 가기
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TypeBadge(
                onClickItem = { onTypeChanged("향수") },
                roundedCorner = 20.dp,
                type = "향수",
                fontSize = 12.sp,
                fontColor = Color.Black,
                selected = type == "향수"
            )
            Spacer(Modifier.width(8.dp))
            TypeBadge(
                onClickItem = { onTypeChanged("게시글") },
                roundedCorner = 20.dp,
                type = "게시글",
                fontSize = 12.sp,
                fontColor = Color.Black,
                selected = type == "게시글"
            )
        }
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
                        onOpenBottomDialog = {/** 여기도 Bottom Dialog 사용하려면 사용합시다 */},
                        isSelected = comment.liked,
                        onChangeSelect = {}
                    )
                }
            }
        }
    }
}