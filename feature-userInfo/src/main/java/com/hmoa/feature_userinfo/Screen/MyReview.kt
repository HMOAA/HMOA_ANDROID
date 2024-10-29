package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ReviewItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_domain.entity.navigation.HbtiRoute
import com.hmoa.core_model.response.ReviewResponseDto
import com.hmoa.feature_userinfo.viewModel.MyReviewUiState
import com.hmoa.feature_userinfo.viewModel.MyReviewViewModel

@Composable
fun MyReviewRoute(
    navReview: (befRoute: HbtiRoute) -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit,
    viewModel: MyReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val reviews = viewModel.reviewPagingSource().collectAsLazyPagingItems()
    MyReviewScreen(
        uiState = uiState,
        errState = errState,
        reviews = reviews,
        navReview = { navReview(HbtiRoute.Hbti) },
        navBack = navBack,
        navLogin = navLogin
    )
}

@Composable
fun MyReviewScreen(
    uiState: MyReviewUiState,
    errState: ErrorUiState,
    reviews: LazyPagingItems<ReviewResponseDto>,
    navReview: () -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit
) {
    when (uiState) {
        MyReviewUiState.Loading -> AppLoadingScreen()
        MyReviewUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errState,
                onCloseClick = navBack
            )
        }

        MyReviewUiState.Success -> {
            MyReviewContent(
                reviews = reviews.itemSnapshotList,
                onBackClick = navBack,
                onItemClick = navReview
            )
        }
    }
}

@Composable
private fun MyReviewContent(
    reviews: ItemSnapshotList<ReviewResponseDto>,
    onBackClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "작성한 후기",
            onNavClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reviews) { review ->
                if (review != null) {
                    val photos = remember(review) { review.hbtiPhotos.map { it.photoUrl } }
                    ReviewItem(
                        isItemClickable = true,
                        reviewId = review.hbtiReviewId,
                        profileImg = review.profileImgUrl,
                        nickname = review.author,
                        writtenAt = review.createdAt,
                        isLiked = review.isLiked,
                        heartNumber = review.heartCount,
                        content = review.content,
                        images = photos,
                        category = review.orderTitle,
                        onHeartClick = { a, b -> },
                        onMenuClick = { /*TODO*/ },
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyReviewUiTest() {
    MyReviewContent(
        onBackClick = {},
        onItemClick = {},
        reviews = ItemSnapshotList(
            placeholdersAfter = 0,
            placeholdersBefore = 0,
            items = emptyList()
        )
    )
}