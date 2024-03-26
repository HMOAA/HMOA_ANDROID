package com.hmoa.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto

@Composable
fun HomeRoute(
    onPerfumeClick: (perfumeId: Int) -> Unit
) {
    HomeScreen(onPerfumeClick = { onPerfumeClick(it) })
}

@Composable
private fun HomeScreen(onPerfumeClick: (perfumeId: Int) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.getFirstMenuWithBanner()
        viewModel.getSecondMenu()
        viewModel.getThirdMenu()
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is HomeViewModel.HomeUiState.Loading -> {
                Text("로딩 중 입니다", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            is HomeViewModel.HomeUiState.HomeData -> {
                HomeContent(
                    onPerfumeClick = { onPerfumeClick(it) },
                    bannerImgUrl = (uiState as HomeViewModel.HomeUiState.HomeData).bannerImg,
                    bannerTitle = (uiState as HomeViewModel.HomeUiState.HomeData).bannerTitle,
                    firstMenu = (uiState as HomeViewModel.HomeUiState.HomeData).firstMenu
                )
            }

            is HomeViewModel.HomeUiState.Error -> {}
        }
    }
}

@Composable
private fun HomeContent(
    onPerfumeClick: (perfumeId: Int) -> Unit,
    bannerImgUrl: String?,
    bannerTitle: String?,
    firstMenu: HomeMenuDefaultResponseDto?
) {
    val fullWidth = LocalConfiguration.current.screenWidthDp
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(verticalScrollState).background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        ImageView(imageUrl = bannerImgUrl, width = fullWidth, height = 107, backgroundColor = Color.White)
        Text(bannerTitle ?: "글씨가 없습니다", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Text(firstMenu?.title ?: "글씨가 없습니다", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
@Preview
private fun HomePreview() {

}