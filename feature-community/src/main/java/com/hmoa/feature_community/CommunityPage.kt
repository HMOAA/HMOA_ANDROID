package com.hmoa.feature_community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.PostListItem
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.BottomScreen
import com.hmoa.core_designsystem.component.FloatingActionBtn
import com.hmoa.core_designsystem.component.MainBottomBar
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunityMainUiState
import com.hmoa.feature_community.ViewModel.CommunityMainViewModel

@Composable
fun CommunityPage(
    uiState: CommunityMainUiState,
    type: Category,
    onTypeChanged: (Category) -> Unit,
    onNavBack: () -> Unit,
    onNavCommunityDescription: (Int) -> Unit,
    onNavPost : (String) -> Unit,
    onNavHome : () -> Unit,
    onNavHPedia : () -> Unit,
    onNavLike : () -> Unit,
    onNavMyPage : () -> Unit,
){
    when(uiState) {
        is CommunityMainUiState.Loading -> {

        }

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
                    )

                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = CustomColor.gray2)
                    )

                    /** search bar (여기서는 Search 모듈을 사용해야 하지 않을까?) */
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )

                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = CustomColor.gray2)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(start = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TypeBadge(
                            onClickItem = {
                                onTypeChanged(Category.추천)
                            },
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
                            onClickItem = {
                                onTypeChanged(Category.자유)
                            },
                            roundedCorner = 20.dp,
                            type = Category.자유.name,
                            fontSize = 14.sp,
                            fontColor = Color.White,
                            selected = type == Category.자유
                        )
                    }

                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = CustomColor.gray2)
                    )

                    LazyColumn {
                        items(uiState.communities) { community ->
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
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.End
                ){
                    FloatingActionBtn(
                        onNavRecommend = { onNavPost(Category.추천.name) },
                        onNavPresent = { onNavPost(Category.시향기.name) },
                        onNavFree = { onNavPost(Category.자유.name) },
                    )
                }
            }
        }

        is CommunityMainUiState.Error -> {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCommunity() {
    var type = Category.시향기
    val testList = listOf(
        CommunityByCategoryResponseDto(
            category = "시향기",
            commentCount = 0,
            communityId = 0,
            heartCount = 10,
            liked = true,
            title = "여자친구한테 선물할 향수 뭐가 좋을까요?"
        ),
        CommunityByCategoryResponseDto(
            category = "시향기",
            commentCount = 0,
            communityId = 0,
            heartCount = 10,
            liked = true,
            title = "여자친구한테 선물할 향수 뭐가 좋을까요?"
        )
    )
    CommunityPage(
        uiState = CommunityMainUiState.Loading,
        type = Category.추천,
        onTypeChanged = {
            type = it
        },
        onNavBack = {},
        onNavCommunityDescription = {},
        onNavPost = {},
        onNavHome = { },
        onNavHPedia = {  },
        onNavLike = {  },
        onNavMyPage = {  },
    )
}

@Composable
fun CommunityPageRoute(
    onNavBack : () -> Unit,
    onNavCommunityDescription : (Int) -> Unit,
    onNavPost : (String) -> Unit,
    onNavHome : () -> Unit,
    onNavHPedia : () -> Unit,
    onNavLike : () -> Unit,
    onNavMyPage : () -> Unit,
    viewModel : CommunityMainViewModel = hiltViewModel()
){
    //view model의 ui state에서 type, list 를 받아서 사용하는 방식
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()

    CommunityPage(
        uiState = uiState.value,
        type = type.value,
        onTypeChanged = {
            viewModel.updateCategory(it)
        },
        onNavBack = onNavBack,
        onNavCommunityDescription = onNavCommunityDescription,
        onNavPost = onNavPost,
        onNavHome = onNavHome,
        onNavHPedia = onNavHPedia,
        onNavLike = onNavLike,
        onNavMyPage = onNavMyPage,
    )
}
