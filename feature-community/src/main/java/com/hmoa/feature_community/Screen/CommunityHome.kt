package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.PostListItem
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityHomeUiState
import com.hmoa.feature_community.ViewModel.CommunityHomeViewModel

@Composable
fun CommunityHomeRoute(
    navCommunityGraph: () -> Unit,
    navCommunityDescription: (Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    navHome: () -> Unit,
    viewModel: CommunityHomeViewModel = hiltViewModel(),
) {

    //ui state를 전달 >> 여기에 community list를 가지고 이를 통해 LazyColumn 이용
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()

    CommunityHome(
        errorUiState = errorUiState,
        uiState = uiState,
        navCommunityGraph = navCommunityGraph,
        navCommunityDescription = navCommunityDescription,
        onErrorHandleLoginAgain = {
            if (viewModel.hasToken()) {
                navHome()
            } else {
                onErrorHandleLoginAgain()
            }
        },
    )
}

@Composable
fun CommunityHome(
    errorUiState: ErrorUiState,
    uiState: CommunityHomeUiState,
    navCommunityGraph: () -> Unit,
    navCommunityDescription: (Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
) {
    var isOpen by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        CommunityTitleBar(onNavCommunityByCategory = navCommunityGraph)

        when (uiState) {
            is CommunityHomeUiState.Loading -> AppLoadingScreen()
            is CommunityHomeUiState.Community -> {
                CommunityHomeContent(
                    communities = uiState.communities,
                    navCommunityDescription = navCommunityDescription
                )
            }

            is CommunityHomeUiState.Error -> {
                ErrorUiSetView(
                    isOpen = isOpen,
                    onConfirmClick = onErrorHandleLoginAgain,
                    errorUiState = errorUiState,
                    onCloseClick = { isOpen = false }
                )
            }
        }
    }
}

@Composable
fun CommunityTitleBar(
    onNavCommunityByCategory: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Community",
            fontSize = 16.sp,
            fontFamily = CustomFont.regular,
            color = Color.Black
        )

        Text(
            modifier = Modifier.clickable { onNavCommunityByCategory() },
            text = "전체보기",
            fontSize = 12.sp,
            fontFamily = CustomFont.regular,
            color = Color.Black
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun CommunityHomeContent(
    communities: List<CommunityByCategoryResponseDto>,
    navCommunityDescription: (Int) -> Unit,
) {
    PostList(
        communities = communities,
        navCommunityDescription = navCommunityDescription
    )
}

@Composable
fun PostList(
    communities: List<CommunityByCategoryResponseDto>,
    navCommunityDescription: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(communities) { community ->
            PostListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(
                        width = 1.dp,
                        color = CustomColor.gray2,
                        shape = RoundedCornerShape(10.dp)
                    ),
                onPostClick = {
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