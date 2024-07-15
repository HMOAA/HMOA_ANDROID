package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.LikeRowItem
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationViewModel

@Composable
fun PerfumeRecommendationResultRoute(
    onNavBack: () -> Unit,
    onNavPerfumeDesc : (Int) -> Unit,
    viewModel: PerfumeRecommendationViewModel = hiltViewModel()
){
    PerfumeRecommendationResultScreen (
        onNavBack = onNavBack,
        onNavPerfumeDesc = onNavPerfumeDesc,
    )
}

@Composable
fun PerfumeRecommendationResultScreen(
    onNavBack : () -> Unit,
    onNavPerfumeDesc : (Int) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "향수 추천",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp)
        ){
            Text(
                text = "고객님에게 어울릴 향수는",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
            )
            /** 임시 더미 데이터 */
            PerfumeResult(
                modifier = Modifier.weight(1f),
                bestPerfumeBrand = "조말론",
                bestPerfumeName = "블랙베리앤베이",
                perfumes = listOf(
                    PerfumeLikeResponseDto(
                        0,
                        "조말론 런던",
                        "우드 세이지 앤 씨 솔트 코롱",
                        "Wood Sage & Sea Salt Cologne",
                        perfumeImageUrl = "",
                        price = 218000
                    )
                ),
                onNavPerfumeDesc = onNavPerfumeDesc
            )
            Spacer(Modifier.height(30.dp))
            Button(
                //여기서 버튼이 활성화되지 않을 것도 있나?
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                isEnabled = true,
                btnText = "다음",
                onClick = { /** 어디로 가야하나? */ }
            )
            Spacer(Modifier.height(40.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PerfumeResult(
    modifier : Modifier,
    bestPerfumeBrand : String,
    bestPerfumeName : String,
    perfumes : List<PerfumeLikeResponseDto>,
    onNavPerfumeDesc : (Int) -> Unit,
){
    val pagerState = rememberPagerState(pageCount = {perfumes.size})
    Column(
        modifier = modifier
            .padding(top = 20.dp)
    ){
        Text(
            text = "브랜드 : ${bestPerfumeBrand}",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
        )
        Text(
            text = "이름 : ${bestPerfumeName}",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_bold))
        )
        Spacer(Modifier.height(20.dp))
        HorizontalPager(
            modifier = Modifier
                .padding(horizontal = 25.dp),
            state = pagerState
        ) {page ->
            val perfume = perfumes[page]
            LikeRowItem(
                brand = perfume.brandName,
                itemPicture = perfume.perfumeImageUrl,
                price = perfume.price.toString(),
                itemNameKo = perfume.koreanName,
                itemNameEng = perfume.englishName,
                onClickClose = {/** 아무 이벤트도 실행하지 않음 */},
                onNavPerfumeDesc = { onNavPerfumeDesc(perfume.perfumeId) }
            )
        }
    }
}

@Preview
@Composable
private fun TestPerfumeRecommendationResult(){
    PerfumeRecommendationResultScreen(
        onNavBack = {},
        onNavPerfumeDesc = {}
    )
}