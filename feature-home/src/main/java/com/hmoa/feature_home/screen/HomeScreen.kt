package com.hmoa.feature_home.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.PerfumeItemView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import com.hmoa.feature_home.AllPerfumeScreenId
import com.hmoa.feature_home.viewmodel.HomeViewModel

@Composable
fun HomeRoute(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit
) {
    HomeScreen(onPerfumeClick = { onPerfumeClick(it) }, onAllPerfumeClick = { onAllPerfumeClick(it) })
}

@Composable
private fun HomeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val firstMenuWithBannerState by viewModel.firstMenuWithBannerState.collectAsStateWithLifecycle()
    val bottomMenuState by viewModel.bottomMenuState.collectAsStateWithLifecycle()
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = verticalScrollState, reverseScrolling = true)
            .background(Color.White),
    ) {
        when (firstMenuWithBannerState) {
            is HomeViewModel.BannerWithFirstMenuState.Loading -> {
                AppLoadingScreen()
            }

            is HomeViewModel.BannerWithFirstMenuState.Data -> {
                FirstMenuWithBannerContent(
                    onPerfumeClick = { onPerfumeClick(it) },
                    bannerImgUrl = (firstMenuWithBannerState as HomeViewModel.BannerWithFirstMenuState.Data).bannerImg,
                    bannerTitle = (firstMenuWithBannerState as HomeViewModel.BannerWithFirstMenuState.Data).bannerTitle,
                    firstMenu = (firstMenuWithBannerState as HomeViewModel.BannerWithFirstMenuState.Data).firstMenu!!,
                )
            }

            is HomeViewModel.BannerWithFirstMenuState.Error -> {

            }
        }

        when (bottomMenuState) {
            is HomeViewModel.BottomMenuState.Loading -> {
                AppLoadingScreen()
            }

            is HomeViewModel.BottomMenuState.Data -> {
                BottomMenuContent(
                    onPerfumeClick = { onPerfumeClick(it) },
                    onAllPerfumeClick = { onAllPerfumeClick(it) },
                    (bottomMenuState as HomeViewModel.BottomMenuState.Data).bottomMenu!!
                )
            }

            is HomeViewModel.BottomMenuState.Error -> {

            }
        }
    }
}

@Composable
private fun FirstMenuWithBannerContent(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    bannerImgUrl: String?,
    bannerTitle: String?,
    firstMenu: HomeMenuDefaultResponseDto,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        ImageView(
            imageUrl = bannerImgUrl,
            width = 2f,
            height = 1f,
            backgroundColor = Color.White,
            ContentScale.FillWidth
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomColor.gray7)
                .padding(vertical = 12.dp)
                .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                bannerTitle ?: "글씨가 없습니다",
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        FirstMenuView(firstMenu, { onPerfumeClick(it) })
    }
}

private fun mapIndexToAllPerfumeScreenId(index: Int): AllPerfumeScreenId {
    var result = AllPerfumeScreenId.First
    when (index) {
        0 -> result = AllPerfumeScreenId.First
        1 -> result = AllPerfumeScreenId.Second
        2 -> result = AllPerfumeScreenId.Third
    }
    return result
}

@Composable
private fun BottomMenuContent(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit,
    bottomMenu: List<HomeMenuDefaultResponseDto>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(bottom = 29.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        bottomMenu.forEachIndexed { index, item ->
            val perfumeMenuScreenId = mapIndexToAllPerfumeScreenId(index)
            BottomMenuView(
                item,
                onPerfumeClick = { onPerfumeClick(it) },
                onAllPerfumeClick = { onAllPerfumeClick(perfumeMenuScreenId) })
        }
    }
}

@Composable
private fun FirstMenuView(firstMenu: HomeMenuDefaultResponseDto, onPerfumeClick: (perfumeId: Int) -> Unit) {
    val totalHeight = LocalConfiguration.current.screenWidthDp - 32
    Text(
        firstMenu?.title ?: "글씨가 없습니다",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 12.dp)
    )
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(totalHeight.dp)
    ) {
        if (!firstMenu.perfumeList.isEmpty()) {
            Column(modifier = Modifier.padding(end = 8.dp)) {
                ImageWithTitleView(
                    title = firstMenu.perfumeList[0].brandName,
                    onItemClick = { onPerfumeClick(firstMenu.perfumeList[0].perfumeId) },
                    imageUrl = firstMenu.perfumeList[0].imgUrl,
                    containerWidth = 0.4f,
                    containerHeight = 0.6f,
                    width = 1f,
                    height = 1f
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                ImageWithTitleView(
                    title = firstMenu.perfumeList[1].brandName,
                    onItemClick = { onPerfumeClick(firstMenu.perfumeList[1].perfumeId) },
                    imageUrl = firstMenu.perfumeList[1].imgUrl,
                    containerWidth = 0.4f,
                    containerHeight = 1f,
                    width = 1f,
                    height = 1f
                )
            }
            Column {
                Row {
                    ImageWithTitleView(
                        title = firstMenu.perfumeList[2].brandName,
                        onItemClick = { onPerfumeClick(firstMenu.perfumeList[2].perfumeId) },
                        imageUrl = firstMenu.perfumeList[2].imgUrl,
                        containerWidth = 0.45f,
                        containerHeight = 0.4f,
                        width = 1f,
                        height = 1f
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    ImageWithTitleView(
                        title = firstMenu.perfumeList[3].brandName,
                        onItemClick = { onPerfumeClick(firstMenu.perfumeList[3].perfumeId) },
                        imageUrl = firstMenu.perfumeList[3].imgUrl,
                        containerWidth = 1f,
                        containerHeight = 0.4f,
                        width = 1f,
                        height = 1f
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                ImageWithTitleView(
                    title = firstMenu.perfumeList[4].brandName,
                    onItemClick = { onPerfumeClick(firstMenu.perfumeList[4].perfumeId) },
                    imageUrl = firstMenu.perfumeList[4].imgUrl,
                    containerWidth = 1f,
                    containerHeight = 1f,
                    width = 1f,
                    height = 1f
                )
            }
        }
    }
}

@Composable
fun BottomMenuView(
    data: HomeMenuDefaultResponseDto?,
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 29.dp)
            .padding(bottom = 12.dp)
            .padding(end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            data?.title ?: "글씨가 없습니다",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
        Text(
            "전체보기",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAllPerfumeClick() }
        )
    }
    LazyRow() {
        items(data!!.perfumeList) {
            Column(modifier = Modifier.clickable { onPerfumeClick(it.perfumeId) }) {
                PerfumeItemView(
                    it.imgUrl, it.perfumeName, it.brandName, 126, 126, 0.9f, 0.9f, CustomColor.gray1,
                    BorderStroke(width = 0.dp, color = Color.Transparent)
                )
            }
        }
    }
}


@Composable
fun ImageWithTitleView(
    title: String,
    onItemClick: () -> Unit,
    imageUrl: String?,
    containerWidth: Float,
    containerHeight: Float,
    width: Float,
    height: Float
) {
    Box(
        modifier = Modifier
            .clickable { onItemClick() }
            .fillMaxWidth(containerWidth)
            .fillMaxHeight(containerHeight), contentAlignment = Alignment.BottomStart
    ) {
        ImageView(
            imageUrl,
            width = width,
            height = height,
            backgroundColor = CustomColor.gray8,
            contentScale = ContentScale.Fit
        )
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 10.sp, modifier = Modifier.padding(8.dp))
    }
}

@Composable
@Preview
private fun HomePreview() {
    FirstMenuWithBannerContent(
        {}, "", "시향지 체험단 모집 ~09.18", HomeMenuDefaultResponseDto(
            listOf(
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
            ), "겨울 이 향수 어떠세요?"
        )
    )
    BottomMenuContent(
        {}, {}, listOf(
            HomeMenuDefaultResponseDto(
                title = "이 제품 어떠세요? 향모아가 추천하는", perfumeList = listOf(
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
                )
            ),
            HomeMenuDefaultResponseDto(
                title = "변함없이 사랑받는, 스테디 셀러", perfumeList = listOf(
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
                )
            )
        )
    )
}