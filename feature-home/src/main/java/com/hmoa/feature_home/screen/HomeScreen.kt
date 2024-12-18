package com.hmoa.feature_home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.PerfumeItemView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_designsystem.theme.pretendard
import com.hmoa.core_domain.entity.data.AllPerfumeScreenId
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import com.hmoa.feature_home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit,
    onHbtiClick: () -> Unit
) {
    HomeScreen(
        onPerfumeClick = { onPerfumeClick(it) },
        onAllPerfumeClick = { onAllPerfumeClick(it) },
        onHbtiClick = { onHbtiClick() })
}

@Composable
private fun HomeScreen(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit,
    onHbtiClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val firstMenuWithBannerState by viewModel.firstMenuWithBannerState.collectAsStateWithLifecycle()
    val bottomMenuState by viewModel.bottomMenuState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    LaunchedEffect(true) {
        listState.animateScrollToItem(index = 0)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {

        itemsIndexed(
            listOf("TopMenu", "BottomMenu")
        ) { idx, item ->
            when (idx) {
                0 -> TopMenu(firstMenuWithBannerState, onPerfumeClick, onHbtiClick)
                1 -> BottomMenu(bottomMenuState, onPerfumeClick, onAllPerfumeClick)
            }
        }
    }

}

@Composable
fun TopMenu(
    firstMenuWithBannerState: HomeViewModel.BannerWithFirstMenuState, onPerfumeClick: (perfumeId: Int) -> Unit,
    onHbtiClick: () -> Unit,
) {
    when (firstMenuWithBannerState) {
        is HomeViewModel.BannerWithFirstMenuState.Loading -> {
            AppLoadingScreen()
        }

        is HomeViewModel.BannerWithFirstMenuState.Data -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp).padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FirstMenuWithBannerContent(
                    onHbtiClick = { onHbtiClick() },
                    bannerImgUrl = (firstMenuWithBannerState as HomeViewModel.BannerWithFirstMenuState.Data).bannerImg,
                )
            }
            FirstMenuView(
                (firstMenuWithBannerState as HomeViewModel.BannerWithFirstMenuState.Data).firstMenu
                    ?: HomeMenuDefaultResponseDto(
                        perfumeList = emptyList<HomeMenuPerfumeResponseDto>().toImmutableList(),
                        title = ""
                    ),
                { onPerfumeClick(it) })
        }

        is HomeViewModel.BannerWithFirstMenuState.Error -> {

        }
    }
}

@Composable
fun BottomMenu(
    bottomMenuState: HomeViewModel.BottomMenuState, onPerfumeClick: (perfumeId: Int) -> Unit,
    onAllPerfumeClick: (screenId: AllPerfumeScreenId) -> Unit,
) {
    when (bottomMenuState) {
        is HomeViewModel.BottomMenuState.Loading -> {
            AppLoadingScreen()
        }

        is HomeViewModel.BottomMenuState.Data -> {
            BottomMenuContent(
                onPerfumeClick = { onPerfumeClick(it) },
                onAllPerfumeClick = { onAllPerfumeClick(it) },
                (bottomMenuState as HomeViewModel.BottomMenuState.Data).bottomMenu
            )
            HmoaCompanyMetaData()
        }

        is HomeViewModel.BottomMenuState.Error -> {

        }
    }
}

@Composable
private fun HmoaCompanyMetaData() {
    Column(
        modifier = Modifier.background(color = Color.Black).fillMaxWidth().padding(top = 32.dp, bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "사업자 번호: 554-20-01858",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = CustomFont.regular,
            color = Color.White,
            lineHeight = 16.sp
        )
        Text(
            text = "향모아 / 대표자 : 박태성",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = CustomFont.regular,
            color = Color.White,
            lineHeight = 16.sp
        )
        Text(
            text = "개인정보보호책임자 : 이종현",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = pretendard,
            color = Color.White,
            lineHeight = 16.sp
        )
        Text(
            text = "통신판매업 : 제 2028-화성동탄-0976호",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = pretendard,
            color = Color.White,
            lineHeight = 16.sp
        )
        Text(
            text = "주소 : 화성시 동탄지성로11, 714-B03호",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = pretendard,
            color = Color.White,
            lineHeight = 16.sp
        )
        Text(
            text = "고객센터 : 070-8080-3309",
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = pretendard,
            color = Color.White,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun FirstMenuWithBannerContent(
    onHbtiClick: () -> Unit,
    bannerImgUrl: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(
            color = Color.Black,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            )
        ).border(
            width = 1.dp, color = Color.Black, shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            )
        ).padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "무료 향BTI 검사 후",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = CustomFont.regular,
                color = Color.White
            )
            Text(
                text = "당신만의 향을 찾아보세요",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = CustomFont.regular,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 10.dp, top = 28.dp).fillMaxWidth(0.8f)
                .background(Color.Black)
        ) {
            ImageView(
                imageUrl = bannerImgUrl,
                width = 2f,
                height = 1f,
                backgroundColor = Color.White,
                ContentScale.FillWidth
            )
        }
        Button(
            isEnabled = true,
            btnText = "# 향bti 검사하기",
            onClick = { onHbtiClick() },
            buttonModifier = Modifier.background(color = CustomColor.gray4).fillMaxWidth(0.9f)
                .height(47.dp),
            textColor = Color.White,
            textSize = 14,
            radious = 8
        )
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
            .padding(horizontal = 16.dp).fillMaxWidth()
            .padding(vertical = 12.dp),
        fontFamily = CustomFont.regular,
        textAlign = TextAlign.Start,
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
            fontFamily = CustomFont.regular
        )
        Text(
            "전체보기",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAllPerfumeClick() },
            fontFamily = CustomFont.regular
        )
    }
    LazyRow() {
        items(data?.perfumeList ?: emptyList()) {
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
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            modifier = Modifier.padding(8.dp),
            fontFamily = CustomFont.regular
        )
    }
}

@Composable
@Preview
private fun HomePreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            FirstMenuWithBannerContent({}, "")
            Button(
                isEnabled = true,
                btnText = "향bti 검사하기",
                onClick = {},
                buttonModifier = Modifier.background(color = Color.Black).fillMaxWidth(0.9f).height(47.dp)
                    .padding(horizontal = 20.dp),
                textColor = Color.White,
                textSize = 14,
                radious = 8
            )
        }
    }
    BottomMenuContent(
        {}, {}, listOf(
            HomeMenuDefaultResponseDto(
                title = "이 제품 어떠세요? 향모아가 추천하는", perfumeList = listOf(
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
                ).toImmutableList()
            ),
            HomeMenuDefaultResponseDto(
                title = "변함없이 사랑받는, 스테디 셀러", perfumeList = listOf(
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml"),
                    HomeMenuPerfumeResponseDto("딥디크", "", 1, "오 로즈 오 드 뚜왈렛 50ml")
                ).toImmutableList()
            )
        ).toImmutableList()
    )
}
