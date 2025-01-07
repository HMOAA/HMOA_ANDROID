package com.hmoa.feature_magazine.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.navigation.CommunityRoute
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDtoItem
import com.hmoa.core_model.response.RecentPerfumeResponseDtoItem
import com.hmoa.feature_magazine.ViewModel.MagazineMainUiState
import com.hmoa.feature_magazine.ViewModel.MagazineMainViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MagazineMainRoute(
    navHome: () -> Unit,
    navPerfumeDesc: (Int) -> Unit,
    navCommunityDesc: (befRoute: CommunityRoute, communityId: Int) -> Unit,
    navMagazineDesc: (Int) -> Unit,
    viewModel: MagazineMainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val magazineList = viewModel.magazinePagingSource().collectAsLazyPagingItems()
    val onCommunityClick = remember<(Int) -> Unit>{{navCommunityDesc(CommunityRoute.CommunityHomeRoute, it)}}

    MagazineMainScreen(
        uiState = uiState.value,
        errorState = errorState.value,
        magazineList = magazineList,
        navHome = navHome,
        onPerfumeClick = navPerfumeDesc,
        onCommunityClick = onCommunityClick,
        onMagazineClick = navMagazineDesc
    )
}

@Composable
fun MagazineMainScreen(
    uiState: MagazineMainUiState,
    errorState: ErrorUiState,
    magazineList: LazyPagingItems<MagazineSummaryResponseDto>,
    navHome: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onCommunityClick: (communityId: Int) -> Unit,
    onMagazineClick: (magazineId: Int) -> Unit
) {
    when (uiState) {
        MagazineMainUiState.Loading -> AppLoadingScreen()
        is MagazineMainUiState.MagazineMain -> {
            MagazineContent(
                magazineList = magazineList,
                perfumeList = uiState.perfumes,
                reviewList = uiState.reviews,
                onPerfumeClick = onPerfumeClick,
                onCommunityClick = onCommunityClick,
                onMagazineClick = onMagazineClick
            )
        }
        MagazineMainUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navHome,
                errorUiState = errorState,
                onCloseClick = navHome
            )
        }
    }
}

@Composable
private fun MagazineContent(
    magazineList: LazyPagingItems<MagazineSummaryResponseDto>,
    perfumeList: ImmutableList<RecentPerfumeResponseDtoItem>,
    reviewList: ImmutableList<MagazineTastingCommentResponseDtoItem>,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onCommunityClick: (communityId: Int) -> Unit,
    onMagazineClick: (magazineId: Int) -> Unit
) {
    if (magazineList.itemSnapshotList.isNotEmpty()) {
        val magazines = remember{
            if (magazineList.itemSnapshotList.size > 5) { magazineList.itemSnapshotList.subList(0, 5) }
            else { magazineList.itemSnapshotList }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(56.dp)
        ) {
            item {
                MagazineTitleBox(magazines = magazines.filterNotNull())
                Spacer(Modifier.height(32.dp))
                ReleasePerfumeList(
                    perfumeList = perfumeList,
                    onPerfumeClick = onPerfumeClick
                )
                Spacer(Modifier.height(52.dp))
                Top10Reviews(
                    reviews = reviewList,
                    onCommunityClick = onCommunityClick
                )
                Spacer(Modifier.height(52.dp))
                MagazineHeader()
                Spacer(Modifier.height(24.dp))
            }
            items(
                items = magazines,
                key = {it!!.magazineId}
            ) { magazine ->
                if (magazine != null) {
                    MagazineContent(
                        imageUrl = magazine.previewImgUrl,
                        title = magazine.title,
                        preview = magazine.preview,
                        onNavMagazineDesc = { onMagazineClick(magazine.magazineId) }
                    )
                }
            }
        }
    } else {
        AppLoadingScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MagazineTitleBox(
    magazines: List<MagazineSummaryResponseDto>,
) {
    val state = rememberPagerState(initialPage = 0, pageCount = { magazines.size })
    HorizontalPager(
        state = state
    ) {
        val imageUrl = magazines[it].previewImgUrl
        val title = magazines[it].title
        val preview = magazines[it].preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(513.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageView(
                imageUrl = imageUrl,
                width = 1f,
                height = 1f,
                backgroundColor = Color.Black,
                contentScale = ContentScale.FillBounds,
                alpha = 0.4f
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 22.dp)
                    .background(color = Color.Transparent)
            ) {
                TopBar(
                    color = Color.Transparent,
                    title = "Magazine",
                    titleColor = Color.White
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                ) {
                    ImageView(
                        imageUrl = imageUrl,
                        width = 1f,
                        height = 1f,
                        backgroundColor = Color.Transparent,
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 36.dp)
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            fontSize = 24.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = preview,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReleasePerfumeList(
    perfumeList: ImmutableList<RecentPerfumeResponseDtoItem>,
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "출시 향수",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = "새롭게 출시된 향수를 확인해보세요",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = perfumeList,
                key = {it.perfumeId}
            ) { perfume ->
                PerfumeDescItem(
                    imageUrl = perfume.perfumeImgUrl,
                    brandName = perfume.brandName,
                    perfumeName = perfume.perfumeName,
                    releaseDate = perfume.releaseDate,
                    onNavPerfumeDesc = { onPerfumeClick(perfume.perfumeId) }
                )
            }
        }
    }
}

@Composable
private fun Top10Reviews(
    reviews: ImmutableList<MagazineTastingCommentResponseDtoItem>,
    onCommunityClick: (communityId: Int) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "TOP 10 시향기",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "리뷰로 느껴보는 향수",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = reviews,
                key = {it.communityId}
            ) { review ->
                ReviewContent(
                    title = review.title,
                    profileImg = review.profileImg,
                    nickname = review.nickname,
                    content = review.content,
                    onNavCommunityDesc = { onCommunityClick(review.communityId) }
                )
            }
        }
    }
}

@Composable
private fun MagazineHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp)
    ) {
        Text(
            text = "HMOA\nNEWS / 매거진",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "향모아가 전하는 향수 트렌드 이슈",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
    }
}

@Composable
private fun MagazineContent(
    imageUrl: String,
    title: String,
    preview: String,
    onNavMagazineDesc: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavMagazineDesc() }
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            ImageView(
                imageUrl = imageUrl,
                width = 1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = preview,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
    }
}

@Composable
private fun PerfumeDescItem(
    imageUrl: String,
    brandName: String,
    perfumeName: String,
    releaseDate: String?,
    onNavPerfumeDesc: () -> Unit
) {
    Column(
        modifier = Modifier.clickable {onNavPerfumeDesc()}
    ) {
        Box(
            modifier = Modifier.size(155.dp)
        ) {
            ImageView(
                imageUrl = imageUrl,
                width = 1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = brandName,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = CustomColor.gray3
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = perfumeName,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = releaseDate ?: "",
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = CustomColor.gray3
        )
    }
}

@Composable
private fun ReviewContent(
    title: String,
    profileImg: String,
    nickname: String,
    content: String,
    onNavCommunityDesc: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(296.dp)
            .height(206.dp)
            .background(color = Color.White)
            .border(width = 1.dp, color = CustomColor.gray2, shape = RectangleShape)
            .clickable { onNavCommunityDesc() }
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp, top = 24.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImageView(
                imgUrl = profileImg,
                width = 20,
                height = 20,
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = nickname,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                color = CustomColor.gray3,
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = content,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            color = Color.Black,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
    }
}