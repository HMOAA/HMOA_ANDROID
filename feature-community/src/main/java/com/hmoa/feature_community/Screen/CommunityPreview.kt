package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.FloatingActionBtn
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityMainUiState
import com.hmoa.feature_community.ViewModel.CommunityPreviewViewModel

@Composable
fun CommunityPreviewRoute(
    onNavBack: () -> Unit,
    onNavSearch: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    onNavPost: (String) -> Unit,
    onNavLogin: () -> Unit,
    onNavHPedia : () -> Unit,
    viewModel: CommunityPreviewViewModel = hiltViewModel()
) {
    //view model의 ui state에서 type, list 를 받아서 사용하는 방식
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val type by viewModel.type.collectAsStateWithLifecycle()

    CommunityPage(
        uiState = uiState.value,
        errState = errState.value,
        communities = viewModel.communityPagingSource().collectAsLazyPagingItems(),
        type = type,
        onTypeChanged = {viewModel.updateCategory(it)},
        onNavBack = onNavBack,
        onNavSearch = onNavSearch,
        onNavCommunityDescription = onNavCommunityDescription,
        onNavPost = {
            if (viewModel.hasToken()){onNavPost(it)}
            else {viewModel.updateLoginError()}
        },
        onErrorHandleLoginAgain = {
            onNavLogin()
        }
    )
}

@Composable
fun CommunityPage(
    uiState: CommunityMainUiState,
    errState : ErrorUiState,
    communities: LazyPagingItems<CommunityByCategoryResponseDto>,
    type: Category,
    onTypeChanged: (Category) -> Unit,
    onNavBack: () -> Unit,
    onNavSearch: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    onNavPost: (String) -> Unit,
    onErrorHandleLoginAgain : () -> Unit,
) {
    when (uiState) {
        is CommunityMainUiState.Loading -> AppLoadingScreen()
        is CommunityMainUiState.Community -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopBar(
                        title = "Community",
                        navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                        onNavClick = onNavBack,
                        menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_search),
                        onMenuClick = onNavSearch
                    )
                    ContentDivider()
                    CommunityMainTypes(
                        type = type,
                        onTypeChanged = onTypeChanged,
                    )
                    ContentDivider()
                    CommunityPagePostList(
                        communities = communities.itemSnapshotList,
                        onNavCommunityDescription = onNavCommunityDescription
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.End
                ) {
                    FloatingActionBtn(
                        onNavRecommend = { onNavPost(Category.추천.name) },
                        onNavPresent = { onNavPost(Category.시향기.name) },
                        onNavFree = { onNavPost(Category.자유.name) },
                        isAvailable = !uiState.enableLoginErrorDialog,
                    )
                }
            }
        }
        is CommunityMainUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = onErrorHandleLoginAgain,
                errorUiState = errState,
                onCloseClick = onErrorHandleLoginAgain
            )
        }
    }
}

@Composable
fun CommunityMainTypes(
    type: Category,
    onTypeChanged: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(start = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TypeBadge(
            onClickItem = {onTypeChanged(Category.추천)},
            roundedCorner = 20.dp,
            type = Category.추천.name,
            fontSize = 14.sp,
            fontColor = Color.White,
            selected = type == Category.추천
        )

        Spacer(Modifier.width(8.dp))

        TypeBadge(
            onClickItem = {
                onTypeChanged(Category.시향기)
            },
            roundedCorner = 20.dp,
            type = Category.시향기.name,
            fontSize = 14.sp,
            fontColor = Color.White,
            selected = type == Category.시향기
        )

        Spacer(Modifier.width(8.dp))

        TypeBadge(
            onClickItem = {onTypeChanged(Category.자유)},
            roundedCorner = 20.dp,
            type = Category.자유.name,
            fontSize = 14.sp,
            fontColor = Color.White,
            selected = type == Category.자유
        )
    }
}

@Composable
fun CommunityPagePostList(
    communities: ItemSnapshotList<CommunityByCategoryResponseDto>,
    onNavCommunityDescription: (Int) -> Unit
) {
    LazyColumn {
        items(communities) { community ->
            if (community != null) {
                PostListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .border(width = 1.dp, color = CustomColor.gray2),
                    onPostClick = {
                        // 여기서 Description으로 이동
                        onNavCommunityDescription(community.communityId)
                    },
                    postType = community.category,
                    postTitle = community.title,
                    heartCount = community.heartCount,
                    commentCount = community.commentCount
                )
            }
        }
    }
}

@Composable
fun ContentDivider() {
    HorizontalDivider(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = CustomColor.gray2)
    )
}