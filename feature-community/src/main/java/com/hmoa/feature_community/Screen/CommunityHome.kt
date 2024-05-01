package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.PostListItem
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityHomeUiState
import com.hmoa.feature_community.ViewModel.CommunityHomeViewModel

@Composable
fun CommunityHomeRoute(
    onNavCommunityGraph: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    viewModel: CommunityHomeViewModel = hiltViewModel(),
) {

    //ui state를 전달 >> 여기에 community list를 가지고 이를 통해 LazyColumn 이용
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CommunityHome(
        uiState = uiState.value,
        onNavCommunityGraph = onNavCommunityGraph,
        onNavCommunityDescription = onNavCommunityDescription
    )
}

@Composable
fun CommunityHome(
    uiState: CommunityHomeUiState, //이거 uiState로 이전해서 uiState에서 데이터 가져오는 방식으로
    onNavCommunityGraph: () -> Unit, //카테고리 별 Community 화면으로 이동
    onNavCommunityDescription: (Int) -> Unit, //해당 Community Id를 가진 Description 화면으로 이동
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize()) {
        CommunityTitleBar(onNavCommunityByCategory = onNavCommunityGraph)
        when (uiState) {
            is CommunityHomeUiState.Loading -> {
                
            }

            is CommunityHomeUiState.Community -> {
                CommunityHomeContent(
                    communities = uiState.communities,
                    onNavCommunityDescription = onNavCommunityDescription
                )
            }

            is CommunityHomeUiState.Error -> {

            }
        }
    }
}

@Composable
fun CommunityTitleBar(
    onNavCommunityByCategory: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Community",
            fontSize = 16.sp,
            color = Color.Black
        )

        Text(
            modifier = Modifier.clickable {
                onNavCommunityByCategory()
            },
            text = "전체보기",
            fontSize = 12.sp,
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
        modifier = Modifier.background(color = Color.White)
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