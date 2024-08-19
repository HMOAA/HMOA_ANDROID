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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.PostListItem
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityHomeUiState
import com.hmoa.feature_community.ViewModel.CommunityHomeViewModel

@Composable
fun CommunityHomeRoute(
    onNavCommunityGraph: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    onNavHome : () -> Unit,
    viewModel: CommunityHomeViewModel = hiltViewModel(),
) {

    //ui state를 전달 >> 여기에 community list를 가지고 이를 통해 LazyColumn 이용
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()

    CommunityHome(
        errorUiState = errorUiState,
        uiState = uiState,
        onNavCommunityGraph = onNavCommunityGraph,
        onNavCommunityDescription = onNavCommunityDescription,
        onErrorHandleLoginAgain = {
            if (viewModel.hasToken()){
                onNavHome()
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
    onNavCommunityGraph: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxSize()) {
        CommunityTitleBar(onNavCommunityByCategory = onNavCommunityGraph)

        when (uiState) {
            is CommunityHomeUiState.Loading -> AppLoadingScreen()
            is CommunityHomeUiState.Community -> {
                CommunityHomeContent(
                    communities = uiState.communities,
                    onNavCommunityDescription = onNavCommunityDescription
                )
            }
            is CommunityHomeUiState.Error -> {
                ErrorUiSetView(
                    onConfirmClick = onErrorHandleLoginAgain,
                    errorUiState = errorUiState,
                    onCloseClick = onErrorHandleLoginAgain
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Community",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )

        Text(
            modifier = Modifier.clickable {onNavCommunityByCategory()},
            text = "전체보기",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun CommunityHomeContent(
    communities: List<CommunityByCategoryResponseDto>,
    onNavCommunityDescription: (Int) -> Unit,
) {
    PostList(
        communities = communities,
        onNavCommunityDescription = onNavCommunityDescription
    )
}

@Composable
fun PostList(
    communities: List<CommunityByCategoryResponseDto>,
    onNavCommunityDescription: (Int) -> Unit
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