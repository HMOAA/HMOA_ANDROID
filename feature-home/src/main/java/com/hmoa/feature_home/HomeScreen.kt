package com.hmoa.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.ImageSizeReactableView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.PerfumeItemView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto

@Composable
fun HomeRoute(
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    HomeScreen(onPerfumeClick = { onPerfumeClick(it) })
}

@Composable
private fun HomeScreen(onPerfumeClick: (perfumeId: Int) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.firstMenuWithBannerState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is HomeViewModel.BannerWithFirstMenuState.Loading -> {
                Text("로딩 중 입니다", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            is HomeViewModel.BannerWithFirstMenuState.HomeData -> {
                HomeContent(
                    onPerfumeClick = { onPerfumeClick(it) },
                    bannerImgUrl = (uiState as HomeViewModel.BannerWithFirstMenuState.HomeData).bannerImg,
                    bannerTitle = (uiState as HomeViewModel.BannerWithFirstMenuState.HomeData).bannerTitle,
                    firstMenu = (uiState as HomeViewModel.BannerWithFirstMenuState.HomeData).firstMenu,
                    secondMenu = (uiState as HomeViewModel.BannerWithFirstMenuState.HomeData).secondMenu,
                    thirdMenu = (uiState as HomeViewModel.BannerWithFirstMenuState.HomeData).thirdMenu
                )
            }

            is HomeViewModel.BannerWithFirstMenuState.Error -> {}
        }
    }
}

@Composable
private fun HomeContent(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    bannerImgUrl: String?,
    bannerTitle: String?,
    firstMenu: HomeMenuDefaultResponseDto,
    secondMenu: HomeMenuDefaultResponseDto,
    thirdMenu: HomeMenuDefaultResponseDto,
) {
    val fullWidth = LocalConfiguration.current.screenWidthDp
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(verticalScrollState).background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        ImageView(imageUrl = bannerImgUrl, width = fullWidth, height = 107, backgroundColor = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth().background(CustomColor.gray7).padding(horizontal = 16.dp)
                .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                bannerTitle ?: "글씨가 없습니다",
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        FirstMenuView(firstMenu)
        SecondMenuView(secondMenu)
        ThirdMenuView(thirdMenu)
    }
}

@Composable
fun FirstMenuView(firstMenu: HomeMenuDefaultResponseDto) {
    val totalHeight = LocalConfiguration.current.screenWidthDp - 32
    Text(
        firstMenu?.title ?: "글씨가 없습니다",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 12.dp)
    )
    Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(totalHeight.dp)) {
        Column(modifier = Modifier.padding(end = 8.dp)) {
            ImageWithTitleView(
                perfumeId = firstMenu.perfumeList[0].perfumeId,
                title = firstMenu.perfumeList[0].perfumeName,
                onItemClick = {},
                imageUrl = firstMenu.perfumeList[0].imgUrl,
                width = 0.4f,
                height = 0.6f
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            ImageWithTitleView(
                perfumeId = firstMenu.perfumeList[1].perfumeId,
                title = firstMenu.perfumeList[1].perfumeName,
                onItemClick = {},
                imageUrl = firstMenu.perfumeList[1].imgUrl,
                width = 0.4f,
                height = 1f
            )
        }
        Column {
            Row {
                ImageWithTitleView(
                    perfumeId = firstMenu.perfumeList[2].perfumeId,
                    title = firstMenu.perfumeList[2].perfumeName,
                    onItemClick = {},
                    imageUrl = firstMenu.perfumeList[2].imgUrl,
                    width = 0.4f,
                    height = 0.3f
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                ImageWithTitleView(
                    perfumeId = firstMenu.perfumeList[3].perfumeId,
                    title = firstMenu.perfumeList[3].perfumeName,
                    onItemClick = {},
                    imageUrl = firstMenu.perfumeList[3].imgUrl,
                    width = 1f,
                    height = 0.3f
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            ImageWithTitleView(
                perfumeId = firstMenu.perfumeList[4].perfumeId,
                title = firstMenu.perfumeList[4].perfumeName,
                onItemClick = {},
                imageUrl = firstMenu.perfumeList[4].imgUrl,
                width = 1f,
                height = 1f
            )
        }
    }
}

@Composable
fun SecondMenuView(secondMenu: HomeMenuDefaultResponseDto?) {
    Text(
        secondMenu?.title ?: "글씨가 없습니다",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 12.dp)
    )
    LazyRow(modifier = Modifier.padding(start = 16.dp)) {
        items(secondMenu!!.perfumeList) {
            PerfumeItemView(it.imgUrl, it.perfumeName, it.brandName, 126, 126, CustomColor.gray1)
        }
    }
}

@Composable
fun ThirdMenuView(thirdMenu: HomeMenuDefaultResponseDto?) {
    Text(
        thirdMenu?.title ?: "글씨가 없습니다",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 12.dp)
    )
    LazyRow(modifier = Modifier.padding(start = 16.dp)) {
        items(thirdMenu!!.perfumeList) {
            PerfumeItemView(it.imgUrl, it.perfumeName, it.brandName, 126, 126, CustomColor.gray1)
        }
    }
}

@Composable
fun ImageWithTitleView(
    perfumeId: Int,
    title: String,
    onItemClick: (perfumeId: Int) -> Unit,
    imageUrl: String?,
    width: Float,
    height: Float
) {
    Box(modifier = Modifier.clickable { onItemClick(perfumeId) }, contentAlignment = Alignment.BottomStart) {
        ImageSizeReactableView(
            imageUrl,
            width = width,
            height = height,
            backgroundColor = CustomColor.gray8
        )
        Text(text = title, fontWeight = FontWeight.Medium, fontSize = 10.sp, modifier = Modifier.padding(8.dp))
    }
}

@Composable
@Preview
private fun HomePreview() {
    HomeContent(
        {}, "", "시향지 체험단 모집 ~09.18", HomeMenuDefaultResponseDto(
            listOf(
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
            ), "겨울 이 향수 어떠세요?"
        ), HomeMenuDefaultResponseDto(
            listOf(
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
            ), "이 제품 어떠세요? 향모아가 추천하는"
        ), HomeMenuDefaultResponseDto(
            listOf(
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
            ), "변함없이 사랑받는, 스테디 셀러"
        )
    )
}