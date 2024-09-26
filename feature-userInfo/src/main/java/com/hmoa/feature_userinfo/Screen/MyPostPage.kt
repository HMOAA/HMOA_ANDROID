package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.hmoa.component.PostListItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_userinfo.screen.NoDataPage
import com.hmoa.feature_userinfo.viewModel.PostUiState
import com.hmoa.feature_userinfo.viewModel.PostViewModel

@Composable
fun MyPostRoute(
    navBack: () -> Unit,
    navEditPost: (Int) -> Unit,
    viewModel : PostViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyPostPage(
        uiState = uiState.value,
        navBack = navBack,
        navEditPost = navEditPost,
    )
}

@Composable
fun MyPostPage(
    uiState : PostUiState,
    navBack: () -> Unit,
    navEditPost: (Int) -> Unit
) {
    when(uiState) {
        PostUiState.Loading -> AppLoadingScreen()
        is PostUiState.Posts -> {
            val posts = uiState.posts.collectAsLazyPagingItems().itemSnapshotList
            MyPostContent(
                posts = posts,
                navBack = navBack,
                navEditPost = navEditPost
            )
        }
        PostUiState.Error -> {

        }
        else -> {}
    }
}

@Composable
private fun MyPostContent(
    posts : ItemSnapshotList<CommunityByCategoryResponseDto>,
    navBack: () -> Unit,
    navEditPost: (communityId : Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            title = "작성한 게시글",
            onNavClick = navBack
        )
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ){
            if (posts.isNotEmpty()) {
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
                                onPostClick = { navEditPost(post.communityId) },
                                postType = post.category,
                                postTitle = post.title,
                                heartCount = post.heartCount,
                                commentCount = post.commentCount
                            )
                        }
                    }
                }
            } else {
                NoDataPage(
                    mainMsg = "작성한 게시글이\n없습니다.",
                    subMsg = "게시글을 작성해주세요"
                )
            }
        }
    }
}