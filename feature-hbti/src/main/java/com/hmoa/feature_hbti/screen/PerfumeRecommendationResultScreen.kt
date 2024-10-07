package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.LikeRowItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeRecommendResponseDto
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationResultViewModel
import com.hmoa.feature_hbti.viewmodel.PerfumeResultUiState

@Composable
fun PerfumeRecommendationResultRoute(
    navBack: () -> Unit,
    navPerfume: (Int) -> Unit,
    navHome: () -> Unit,
    viewModel: PerfumeRecommendationResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()

    var isOpen by remember { mutableStateOf(true) }

    when (uiState) {

        PerfumeResultUiState.Loading -> AppLoadingScreen()
        is PerfumeResultUiState.Success -> {
            PerfumeCommentResultContent(
                perfumes = (uiState as PerfumeResultUiState.Success).perfumes ?: emptyList(),
                isPriceSortedSelected = (uiState as PerfumeResultUiState.Success).isPriceSortedSelected,
                isNoteSortedSelected = (uiState as PerfumeResultUiState.Success).isNoteSortedSelected,
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
                isOpen = isOpen,
                onConfirmClick = navBack,
                errorUiState = errorState,
                onCloseClick = navBack
            )
        }
    }
}

@Composable
private fun PerfumeCommentResultContent(
    perfumes: List<PerfumeRecommendResponseDto>,
    isPriceSortedSelected: Boolean,
    isNoteSortedSelected: Boolean,
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
                Row(modifier = Modifier.fillMaxWidth().padding(end = 16.dp).padding(top = 50.dp), horizontalArrangement = Arrangement.End) {
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
                /** 임시 더미 데이터 */
                PerfumeResult(
                    perfumes = perfumes,
                    navPerfume = navPerfume
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PerfumeResult(
    perfumes: List<PerfumeRecommendResponseDto>,
    navPerfume: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { perfumes.size })
    Column(
        modifier = Modifier.padding(top = 20.dp).fillMaxHeight(0.7f).fillMaxWidth().background(Color.White),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
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
        onClickNoteSorted = {}
    )
}