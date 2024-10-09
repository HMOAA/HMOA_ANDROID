package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.component.ReviewItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_model.response.Photo
import com.hmoa.core_model.response.ReviewResponseDto

@Composable
fun ReviewRoute(){

}

@Composable
fun ReviewScreen(
    reviews: List<ReviewResponseDto>,
    onBackClick: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            TopBar(
                color = Color.Black,
                title = "향BTI 후기",
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = onBackClick
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                items(reviews){review ->
                    ReviewItem(
                        profileImg = review.profileImgUrl,
                        nickname = review.author,
                        writtenAt = review.createdAt,
                        isLiked = review.isLiked,
                        heartNumber = review.heartCount,
                        content = review.content,
                        images = review.hbtiPhotos.map{it.photoUrl},
                        category = review.orderTitle,
                        onHeartClick = { /*TODO*/ },
                        onMenuClick = { /*TODO*/ },
                        onItemClick = {}
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 24.dp, bottom = 18.dp),
            contentAlignment = Alignment.BottomEnd
        ){

        }
    }
}

@Preview
@Composable
private fun ReviewUiTest(){
    ReviewScreen(
        reviews = listOf(
            ReviewResponseDto(
                hbtiReviewId = 0,
                profileImgUrl = "",
                author = "향수 러버",
                content = "평소에 선호하는 향이 있었는데 그 향의 이름을 몰랐는데 향료 배송받고 시향해보니 통카 빈?이더라구요 제가 좋아했던 향수들은 다 통카 빈이 들어가있네요 ㅎ 저같은 분들한테 추천해요",
                imagesCount = 3,
                hbtiPhotos = listOf(
                    Photo(
                        photoId = 0,
                        photoUrl = ""
                    ),
                    Photo(
                        photoId = 0,
                        photoUrl = ""
                    ),
                    Photo(
                        photoId = 0,
                        photoUrl = ""
                    )
                ),
                createdAt = "10일 전",
                isWrited = false,
                heartCount = 12,
                isLiked = true,
                orderTitle = "시트러스"
            ),
            ReviewResponseDto(
                hbtiReviewId = 0,
                profileImgUrl = "",
                author = "향수 러버",
                content = "향수를 1회도 구매하지 않은 사람인데 향모아에서 인생향을 찾아어요..!",
                imagesCount = 0,
                hbtiPhotos = listOf(),
                createdAt = "10일 전",
                isWrited = false,
                heartCount = 12,
                isLiked = false,
                orderTitle = "플로럴"
            ),
        ),
        onBackClick = {}
    )
}