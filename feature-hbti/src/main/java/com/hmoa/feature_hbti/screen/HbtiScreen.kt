package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.ReviewItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_model.response.Photo
import com.hmoa.core_model.response.ReviewResponseDto
import com.hmoa.feature_hbti.viewmodel.HbtiHomeUiState
import com.hmoa.feature_hbti.viewmodel.HbtiHomeViewModel

@Composable
fun HbtiRoute(
    navHome: () -> Unit,
    navReview: () -> Unit,
    navBack: () -> Unit,
    onHbtiSurveyClick: () -> Unit,
    onAfterOrderClick: () -> Unit,
    viewModel: HbtiHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    HbtiScreen(
        onHbtiSurveyClick = { onHbtiSurveyClick() },
        onAfterOrderClick = { onAfterOrderClick() },
        navHome = navHome,
        navReview = navReview,
        navBack = navBack,
        uiState = uiState,
        errState = errState,
    )
}

@Composable
fun HbtiScreen(
    onHbtiSurveyClick: () -> Unit,
    onAfterOrderClick: () -> Unit,
    navBack: () -> Unit,
    navHome: () -> Unit,
    navReview: () -> Unit,
    uiState: HbtiHomeUiState,
    errState: ErrorUiState,
) {
    var isOpen by remember{mutableStateOf(true)}
    when(uiState){
        HbtiHomeUiState.Loading -> AppLoadingScreen()
        HbtiHomeUiState.Error -> {
            ErrorUiSetView(
                isOpen = isOpen,
                onConfirmClick = navHome,
                errorUiState = errState,
                onCloseClick = navHome
            )
        }
        is HbtiHomeUiState.Success -> {
            HbtiHomeContent(
                reviews = uiState.reviews,
                onHbtiSurveyClick = onHbtiSurveyClick,
                onAfterOrderClick = onAfterOrderClick,
                onReviewItemClick = navReview,
                onBackClick = navBack
            )
        }
    }
}

@Composable
private fun HbtiHomeContent(
    reviews: List<ReviewResponseDto>,
    onHbtiSurveyClick: () -> Unit,
    onAfterOrderClick: () -> Unit,
    onReviewItemClick: () -> Unit,
    onBackClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopBar(
            title = "향BTI",
            titleColor = Color.White,
            color = Color.Black,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onBackClick
        )
        Text(
            "당신의 향BTI는 무엇일까요?",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = pretendard,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
        )
        Text(
            "검사를 통해 좋아하는 향료와\n향수까지 알아보세요!",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                fontSize = 14.sp
            )
        )

        Row(modifier = Modifier.padding(top = 20.dp, bottom = 32.dp)) {
            Box(
                Modifier
                    .padding(end = 15.dp)
                    .fillMaxWidth(0.5f)
                    .height(107.dp)
                    .clickable { onHbtiSurveyClick() }
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))) {
                ImageView(
                    imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/122bc5b1-1cc1-44b3-a468-1b56f9998994",
                    width = 1f,
                    height = 1f,
                    backgroundColor = Color.Transparent,
                    contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(107.dp)) {
                    Text(
                        "향BTI\n검사하러 가기",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(107.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
                    .clickable { onAfterOrderClick() }
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
            ) {
                ImageView(
                    imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/4bb30703-d77d-49ac-8a01-2aee48bf04c3",
                    width = 1f,
                    height = 1f,
                    backgroundColor = Color.Transparent,
                    contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(107.dp)) {
                    Text(
                        "향료 입력하기\n(주문 후)",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(30.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp))
        ) {
            ImageView(
                imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/eb5499d5-25e4-4141-af66-353daa76f2a2",
                width = 0.1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillHeight
            )
            Spacer(Modifier.width(9.dp))
            Text(
                "향BTI 후기",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 20.sp
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.End
        ){
            TextButton(
                onClick = onReviewItemClick
            ){
                Text(
                    "전체보기",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pretendard,
                        fontSize = 12.sp
                    ),
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(reviews){ review ->
                ReviewItem(
                    isItemClickable = true,
                    profileImg = review.profileImgUrl,
                    nickname = review.author,
                    writtenAt = review.createdAt,
                    isLiked = review.isLiked,
                    heartNumber = review.heartCount,
                    content = review.content,
                    images = review.hbtiPhotos.map{it.photoUrl},
                    category = review.orderTitle,
                    onHeartClick = onReviewItemClick,
                    onMenuClick = onReviewItemClick,
                    onItemClick = onReviewItemClick
                )
            }
        }
    }
}

@Preview
@Composable
fun HbtiScreenPreview() {
    HbtiScreen({}, {}, errState = ErrorUiState.Loading, uiState = HbtiHomeUiState.Success(listOf(
        ReviewResponseDto(
            hbtiReviewId = 0,
            profileImgUrl = "",
            author = "향수 러버",
            content = "향수를 1회도 구매하지 않은 사람인데 향모아에서 인생향수 찾았어요!",
            imagesCount = 4,
            hbtiPhotos = listOf(
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
            ),
            createdAt = "10일 전",
            isWrited = false,
            heartCount = 12,
            isLiked = false,
            orderTitle = "시트러스"
        ),
        ReviewResponseDto(
            hbtiReviewId = 0,
            profileImgUrl = "",
            author = "향수 러버",
            content = "평소에 선호하는 향이 있었는데 그 향의 이름을 몰랐는데 향료 배송받고 시향해본 통카 빈? 이더라구요 제가 좋아했던 향수들은 다 통카 빈이 들어가있네요 ㅎ 저 같은 분들에게 추천해요",
            imagesCount = 4,
            hbtiPhotos = listOf(
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
                Photo(
                    photoUrl = "",
                    photoId = 0
                ),
            ),
            createdAt = "10일 전",
            isWrited = false,
            heartCount = 12,
            isLiked = true,
            orderTitle = "시트러스"
        ),
    )),
        navBack = {},
        navHome = {},
        navReview = {}
    )
}
