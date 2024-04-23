package com.hmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.feature_userinfo.viewModel.PostUiState
import com.example.feature_userinfo.viewModel.PostViewModel
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityByCategoryResponseDto

@Composable
fun MyPostRoute(
    onNavBack: () -> Unit,
    onNavEditPost: (Int) -> Unit,
    viewModel : PostViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyPostPage(
        uiState = uiState.value,
        onNavBack = onNavBack,
        onNavEditPost = onNavEditPost,
    )
}

@Composable
fun MyPostPage(
    uiState : PostUiState,
    onNavBack: () -> Unit,
    onNavEditPost: (Int) -> Unit
) {
    when(uiState) {
        PostUiState.Loading -> {
            AppLoadingScreen()
        }
        is PostUiState.Posts -> {
            val posts = uiState.posts.collectAsLazyPagingItems().itemSnapshotList
            if (posts.isNotEmpty()) {
                MyPostContent(
                    posts = posts,
                    onNavBack = onNavBack,
                    onNavEditPost = onNavEditPost
                )
            } else {
                NoDataPage(
                    mainMsg = "작성한 게시글이\n없습니다.",
                    subMsg = "게시글을 올려주세요"
                )
            }
        }
        PostUiState.Error -> {

        }
        else -> {}
    }
}

@Composable
private fun MyPostContent(
    posts : ItemSnapshotList<CommunityByCategoryResponseDto>,
    onNavBack: () -> Unit,
    onNavEditPost: (communityId : Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            title = "작성한 게시글",
            onNavClick = onNavBack
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(posts) { post ->
                if (post != null){
                    PostListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(
                                width = 1.dp,
                                color = CustomColor.gray2,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        onPostClick = { onNavEditPost(post.communityId) },
                        postType = post.category,
                        postTitle = post.title,
                        heartCount = post.heartCount,
                        commentCount = post.commentCount
                    )
                }
            }
        }
    }
}