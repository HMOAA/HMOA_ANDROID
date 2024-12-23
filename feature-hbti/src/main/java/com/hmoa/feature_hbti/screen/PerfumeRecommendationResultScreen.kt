package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_model.response.PerfumeRecommendResponseDto
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationResultViewModel
import com.hmoa.feature_hbti.viewmodel.PerfumeResultUiState

@Composable
fun PerfumeRecommendationResultRoute(
    navBack: () -> Unit,
    navPerfume: (Int) -> Unit,
    navHome: () -> Unit,
    navLogin: () -> Unit,
    viewModel: PerfumeRecommendationResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()

    when (uiState) {

        PerfumeResultUiState.Loading -> AppLoadingScreen()
        is PerfumeResultUiState.Success -> {
            PerfumeCommentResultContent(
                perfumes = (uiState as PerfumeResultUiState.Success).perfumes ?: emptyList(),
                isPriceSortedSelected = (uiState as PerfumeResultUiState.Success).isPriceSortedSelected,
                isNoteSortedSelected = (uiState as PerfumeResultUiState.Success).isNoteSortedSelected,
                isEmptyPriceContentNeedState = (uiState as PerfumeResultUiState.Success).isEmptyPriceContentNeedState,
                navBack = navBack,
                navPerfume = navPerfume,
                onClickButton = navHome,
                onClickPriceSorted = { viewModel.insertPriceSortedPerfumes() },
                onClickNoteSorted = { viewModel.insertNoteSortedPerfumes() }
            )
        }

        PerfumeResultUiState.Error -> {
            /** Error 발생 시 어디로 가는 것이 좋을까? **/
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errorState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
fun PerfumeCommentResultContent(
    perfumes: List<PerfumeRecommendResponseDto>,
    isPriceSortedSelected: Boolean,
    isNoteSortedSelected: Boolean,
    isEmptyPriceContentNeedState: Boolean,
    onClickButton: () -> Unit,
    onClickPriceSorted: () -> Unit,
    onClickNoteSorted: () -> Unit,
    navBack: () -> Unit,
    navPerfume: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            title = "향수 추천",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "고객님에게 어울릴 향수는",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp).padding(top = 50.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "가격대 우선",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                        color = if (isPriceSortedSelected) Color.Black else CustomColor.gray3,
                        modifier = Modifier.clickable { onClickPriceSorted() }
                    )
                    Text(
                        text = "향료 우선",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                        color = if (isNoteSortedSelected) Color.Black else CustomColor.gray3,
                        modifier = Modifier.clickable { onClickNoteSorted() }.padding(start = 7.dp)
                    )
                }
                PerfumeResult(
                    perfumes = perfumes,
                    navPerfume = navPerfume,
                    isEmptyPriceContentNeedState = isEmptyPriceContentNeedState
                )
                Spacer(Modifier.height(30.dp))
            }
            Column {
                Button(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    isEnabled = true,
                    btnText = "홈으로 돌아가기",
                    onClick = { onClickButton() },
                    radious = 5
                )
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun EmptyPricePerfumeRecommendationScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(110.dp),
            painter = painterResource(R.drawable.ic_app_default_1),
            contentDescription = "App Logo"
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "설정하신 가격대 내에\n해당하는 향수가 없습니다.",
            fontSize = 20.sp,
            fontFamily = CustomFont.bold,
        )
        Spacer(Modifier.height(17.dp))
        Text(
            text = "가격대를 재설정 해주세요.",
            fontSize = 16.sp,
            fontFamily = CustomFont.regular,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PerfumeResult(
    perfumes: List<PerfumeRecommendResponseDto>,
    isEmptyPriceContentNeedState: Boolean,
    navPerfume: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { perfumes.size })
    Column(
        modifier = Modifier.padding(top = 20.dp).fillMaxHeight(0.7f).fillMaxWidth().background(Color.White),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        if (isEmptyPriceContentNeedState) {
            EmptyPricePerfumeRecommendationScreen()
        } else {
            HorizontalPager(
                modifier = Modifier
                    .padding(horizontal = 25.dp).fillMaxWidth(),
                state = pagerState,
                contentPadding = PaddingValues(end = 80.dp)
            ) { page ->
                val perfume = perfumes[page]
                Column(modifier = Modifier.padding(end = 15.dp)) {
                    LikeRowItem(
                        brand = perfume.brandname ?: "",
                        itemPicture = perfume.perfumeImageUrl ?: "",
                        price = perfume.price.toString(),
                        itemNameKo = perfume.perfumeName ?: "",
                        itemNameEng = perfume.perfumeEnglishName ?: "",
                        onClickClose = { /** 아무 이벤트도 실행하지 않음 */ },
                        navPerfume = { navPerfume(perfume.perfumeId ?: 0) },
                        isCloseButtonExist = false
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PerfumeRecommendationsResultPreview() {
    val perfumes = listOf(
        PerfumeRecommendResponseDto(
            brandname = "디올",
            perfumeId = 0,
            perfumeImageUrl = "dd",
            perfumeName = "디올 어쩌구",
            perfumeEnglishName = "diol",
            price = 100000
        )
    )
    PerfumeCommentResultContent(
        perfumes = perfumes,
        isPriceSortedSelected = true,
        isNoteSortedSelected = false,
        navBack = {},
        navPerfume = {},
        onClickButton = {},
        onClickPriceSorted = {},
        onClickNoteSorted = {},
        isEmptyPriceContentNeedState = false
    )
}
