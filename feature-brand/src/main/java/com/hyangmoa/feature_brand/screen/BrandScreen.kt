package com.hyangmoa.feature_brand.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyangmoa.component.TopBar
import com.hyangmoa.core_designsystem.component.ImageView
import com.hyangmoa.core_designsystem.component.PerfumeWithCountItemView
import com.hyangmoa.core_designsystem.theme.CustomColor
import com.hyangmoa.core_model.data.SortType
import com.hyangmoa.core_model.response.BrandDefaultResponseDto
import com.hyangmoa.core_model.response.BrandPerfumeBriefResponseDto
import com.hyangmoa.feature_brand.viewmodel.BrandViewmodel

@Composable
fun BrandRoute(
    brandId: Int?,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    if (brandId != null) {
        BrandScreen(
            brandId,
            onBackClick = { onBackClick() },
            onHomeClick = { onHomeClick() },
            onPerfumeClick = { onPerfumeClick(it) })
    }
}

@Composable
fun BrandScreen(
    brandId: Int,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    viewModel: BrandViewmodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val latestPerfumes = viewModel.getPagingLatestPerfumes(brandId)?.collectAsLazyPagingItems()
    val likePerfumes = viewModel.getPagingLikePerfumes(brandId)?.collectAsLazyPagingItems()

    LaunchedEffect(brandId) {
        viewModel.intializeBrandPerfumes(brandId)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (uiState) {
            is BrandViewmodel.BrandUiState.Loading -> {}
            is BrandViewmodel.BrandUiState.Data -> {
                BrandContent(
                    latestPerfumes = latestPerfumes,
                    likePerfumes = likePerfumes,
                    brandData = (uiState as BrandViewmodel.BrandUiState.Data).brand,
                    onBackClick = { onBackClick() },
                    onHomeClick = { onHomeClick() },
                    onSortLikeClick = { viewModel.onClickSortLike() },
                    onSortLatestClick = { viewModel.onClickSortLatest() },
                    sortType = (uiState as BrandViewmodel.BrandUiState.Data).sortType,
                    onPerfumeClick = { onPerfumeClick(it) }
                )
            }

            is BrandViewmodel.BrandUiState.Error -> {}
        }
    }
}

@Composable
fun BrandContent(
    latestPerfumes: LazyPagingItems<BrandPerfumeBriefResponseDto>?,
    likePerfumes: LazyPagingItems<BrandPerfumeBriefResponseDto>?,
    brandData: BrandDefaultResponseDto?,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSortLikeClick: () -> Unit,
    onSortLatestClick: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    sortType: SortType
) {
    TopBar(
        title = brandData?.brandName ?: "",
        iconSize = 25.dp,
        navIcon = painterResource(R.drawable.ic_back),
        onNavClick = { onBackClick() },
        menuIcon = painterResource(R.drawable.ic_home),
        onMenuClick = { onHomeClick() }
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        when (sortType) {
            SortType.LATEST -> PerfumeGridView(
                perfumes = latestPerfumes,
                onSortLikeClick = { onSortLikeClick() },
                onSortLatestClick = { onSortLatestClick() },
                sortType = sortType,
                brandData = brandData,
                onPerfumeClick = { onPerfumeClick(it) }
            )

            SortType.LIKE -> PerfumeGridView(
                perfumes = likePerfumes,
                onSortLikeClick = { onSortLikeClick() },
                onSortLatestClick = { onSortLatestClick() },
                sortType = sortType,
                brandData = brandData,
                onPerfumeClick = { onPerfumeClick(it) }
            )
        }
    }
}

@Composable
fun BrandView(koreanTitle: String, englishTitle: String, imageUrl: String) {
    Column(
        modifier = Modifier.height(200.dp).background(Color.Black).padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = englishTitle,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.White)
            )
            Text(
                text = koreanTitle,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.White)
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxWidth().background(Color.Black)
        ) {
            Column(
                modifier = Modifier.border(BorderStroke(width = 2.dp, color = CustomColor.gray3))
                    .width(100.dp).height(100.dp).background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                ImageView(
                    imageUrl,
                    width = 0.9f,
                    height = 1f,
                    backgroundColor = Color.White,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

@Composable
fun PerfumeGridView(
    perfumes: LazyPagingItems<BrandPerfumeBriefResponseDto>?,
    onSortLikeClick: () -> Unit,
    onSortLatestClick: () -> Unit,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    sortType: SortType,
    brandData: BrandDefaultResponseDto?,
) {
    val likeColor = if (sortType == SortType.LIKE) Color.Black else CustomColor.gray2
    val latestColor = if (sortType == SortType.LATEST) Color.Black else CustomColor.gray2

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        item(span = { GridItemSpan(2) }, content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                BrandView(
                    koreanTitle = brandData?.brandName ?: "",
                    englishTitle = brandData?.englishName ?: "",
                    imageUrl = brandData?.brandImageUrl ?: ""
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        "좋아요순",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light),
                        modifier = Modifier.padding(end = 4.dp).clickable { onSortLikeClick() },
                        color = likeColor
                    )
                    Text(
                        "최신순",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light),
                        modifier = Modifier.clickable { onSortLatestClick() },
                        color = latestColor
                    )
                }
            }
        })
        items(perfumes?.itemSnapshotList ?: emptyList()) {
            PerfumeWithCountItemView(
                imageUrl = it?.perfumeImgUrl ?: "",
                perfumeName = it?.perfumeName ?: "",
                brandName = it?.brandName ?: "",
                containerWidth = 160,
                containerHeight = 160,
                imageWidth = 1f,
                imageHeight = 1f,
                imageBackgroundColor = Color.White,
                heartCount = it?.heartCount ?: 0,
                isLikedPerfume = it?.liked ?: false,
                onPerfumeClick = { onPerfumeClick(it!!.perfumeId) }
            )
        }

    }
}

@Composable
@Preview
fun BrandContentPreview() {
    Column {
        BrandView("조말론 런던", "JO MALONE LONDON", "")
        Row {
            PerfumeWithCountItemView(
                imageUrl = "",
                perfumeName = "우드세이지 앤 씨쏠트",
                brandName = "조말론",
                containerWidth = 160,
                containerHeight = 160,
                imageWidth = 1f,
                imageHeight = 1f,
                imageBackgroundColor = Color.White,
                heartCount = 10,
                isLikedPerfume = false,
                onPerfumeClick = {}
            )
            PerfumeWithCountItemView(
                imageUrl = "",
                perfumeName = "우드세이지 앤 씨쏠트",
                brandName = "조말론",
                containerWidth = 160,
                containerHeight = 160,
                imageWidth = 1f,
                imageHeight = 1f,
                imageBackgroundColor = Color.White,
                heartCount = 10,
                isLikedPerfume = true,
                onPerfumeClick = {}
            )
        }
    }
}