package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
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
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.FloatingActionBtn
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityMainUiState
import com.hmoa.feature_community.ViewModel.CommunityMainViewModel


@Composable
fun CommunityPreviewRoute(
    navBack: () -> Unit,
    navSearch: () -> Unit,
    navCommunityDescription: (Int) -> Unit,
    navPost: (String) -> Unit,
    navLogin: () -> Unit,
    navHPedia: () -> Unit,
    viewModel: CommunityMainViewModel = hiltViewModel()
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
        onTypeChanged = { viewModel.updateCategory(it) },
        navBack = navBack,
        navSearch = navSearch,
        navCommunityDescription = navCommunityDescription,
        navPost = {
            if (viewModel.hasToken()) {
                navPost(it)
            } else {
                viewModel.updateLoginError()
            }
        },
        onErrorHandleLoginAgain = {
            if (viewModel.hasToken()) {
                navHPedia()
            } else {
                navLogin()
            }
        }
    )
}

@Composable
fun CommunityPage(
    uiState: CommunityMainUiState,
    errState: ErrorUiState,
    communities: LazyPagingItems<CommunityByCategoryResponseDto>,
    type: Category,
    onTypeChanged: (Category) -> Unit,
    navBack: () -> Unit,
    navSearch: () -> Unit,
    navCommunityDescription: (Int) -> Unit,
    navPost: (String) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
) {
    var isOpen by remember { mutableStateOf(true) }

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
                        onNavClick = navBack,
                        menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_search),
                        onMenuClick = navSearch
                    )
                    ContentDivider()
                    CommunityMainTypes(
                        type = type,
                        onTypeChanged = onTypeChanged,
                    )
                    ContentDivider()
                    CommunityPagePostList(
                        communities = communities.itemSnapshotList,
                        navCommunityDescription = navCommunityDescription
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.End
                ) {
                    FloatingActionBtn(
                        onNavRecommend = { navPost(Category.추천.name) },
                        onNavHbtiReview = { navPost(Category.시향기.name) },
                        onNavFree = { navPost(Category.자유.name) },
                        isAvailable = !uiState.enableLoginErrorDialog,
                    )
                }
            }
        }

        is CommunityMainUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = onErrorHandleLoginAgain,
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
            onClickItem = { onTypeChanged(Category.추천) },
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
            onClickItem = { onTypeChanged(Category.자유) },
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
    navCommunityDescription: (Int) -> Unit
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
                        navCommunityDescription(community.communityId)
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