package com.hmoa.feature_userinfo.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.EmptyDataPage
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.LikeGridItem
import com.hmoa.core_designsystem.component.LikeRowItem
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import com.hmoa.feature_userinfo.viewModel.MyFavoritePerfumeUiState
import com.hmoa.feature_userinfo.viewModel.MyFavoritePerfumeViewModel

@Composable
fun MyFavoritePerfumeRoute(
    navPerfume: (perfumeId: Int) -> Unit,
    navHome: () -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit,
    viewModel: MyFavoritePerfumeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()
    MyFavoritePerfumeScreen(
        uiState = uiState,
        errorUiState = errorUiState,
        navLogin = navLogin,
        navPerfume = navPerfume,
        navHome = navHome,
        navBack = navBack
    )
}

@Composable
fun MyFavoritePerfumeScreen(
    errorUiState: ErrorUiState,
    uiState: MyFavoritePerfumeUiState,
    navPerfume: (perfumeId: Int) -> Unit,
    navHome: () -> Unit,
    navBack: () -> Unit,
    navLogin: () -> Unit
) {
    when (uiState) {
        MyFavoritePerfumeUiState.Loading -> AppLoadingScreen()
        is MyFavoritePerfumeUiState.Like -> {
            MyFavoritePerfumeContent(
                perfumes = uiState.perfumes,
                navPerfume = navPerfume,
                navBack = navBack
            )
        }
        is MyFavoritePerfumeUiState.Error -> {
            ErrorUiSetView(
                onLoginClick = navLogin,
                errorUiState = errorUiState,
                onCloseClick = navHome
            )
        }
    }
}

@Composable
private fun MyFavoritePerfumeContent(
    perfumes: List<PerfumeLikeResponseDto>,
    navPerfume: (perfumeId: Int) -> Unit,
    navBack: () -> Unit
) {
    var type by remember { mutableStateOf("ROW") }
    if (perfumes.isNotEmpty()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
        ) {
            TopBar(
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                title = "저장",
                onNavClick = navBack
            )
            Spacer(Modifier.height(16.dp))
            IconRow(type = type, onTypeChanged = { type = it })
            Spacer(Modifier.height(20.dp))
            if (type == "ROW") {
                LikePerfumeListByRow(
                    perfumes = perfumes,
                    navPerfume = navPerfume
                )
            } else if (type == "GRID") {
                LikePerfumeListByGrid(
                    perfumes = perfumes,
                    navPerfume = navPerfume
                )
            }
        }
    } else {
        EmptyDataPage(mainText = "좋아요한 향수가 없습니다.")
    }
}

@Composable
private fun IconRow(
    type: String,
    onTypeChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { onTypeChanged("ROW") },
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_sorted_row_type),
                tint = if (type == "ROW") CustomColor.gray4 else CustomColor.gray2,
                contentDescription = "Sorted Type Row"
            )
        }
        Spacer(Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { onTypeChanged("GRID") },
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_sorted_type_grid),
                tint = if (type == "GRID") CustomColor.gray4 else CustomColor.gray2,
                contentDescription = "Sorted Type Grid"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LikePerfumeListByRow(
    perfumes: List<PerfumeLikeResponseDto>,
    navPerfume: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { perfumes.size }
    )
    HorizontalPager(
        modifier = Modifier.padding(bottom = 22.dp),
        state = pagerState,
        pageSpacing = 8.dp,
    ) {
        val perfume = perfumes[it]
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            LikeRowItem(
                brand = perfume.brandName,
                itemPicture = perfume.perfumeImageUrl,
                price = perfume.price.toString(),
                itemNameKo = perfume.koreanName,
                itemNameEng = perfume.englishName,
                onClickClose = {/* event nothing */ },
                navPerfume = { navPerfume(perfume.perfumeId) }
            )
        }
    }
}

@Composable
private fun LikePerfumeListByGrid(
    perfumes: List<PerfumeLikeResponseDto>,
    navPerfume: (Int) -> Unit
) {
    var showCard by remember { mutableStateOf(false) }
    var selectedPerfumeIdx by remember { mutableIntStateOf(0) }
    if (showCard) {
        val perfume = perfumes[selectedPerfumeIdx]
        Dialog(
            onDismissRequest = { showCard = false }
        ) {
            Box(
                modifier = Modifier
                    .height(354.dp)
                    .width(280.dp)
            ) {
                LikeRowItem(
                    brand = perfume.brandName,
                    itemPicture = perfume.perfumeImageUrl,
                    price = perfume.price.toString(),
                    itemNameKo = perfume.koreanName,
                    itemNameEng = perfume.englishName,
                    onClickClose = { showCard = false },
                    navPerfume = { navPerfume(perfume.perfumeId) }
                )
            }
        }
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        state = rememberLazyGridState(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(perfumes) { idx, perfume ->
            LikeGridItem(
                itemPicture = perfume.perfumeImageUrl,
                onClickItem = {
                    showCard = true
                    selectedPerfumeIdx = idx
                }
            )
        }
    }
}
