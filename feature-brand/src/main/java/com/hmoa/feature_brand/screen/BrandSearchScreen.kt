package com.hmoa.feature_brand.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.feature_brand.viewmodel.BrandSearchViewmodel

@Composable
fun BrandSearchRoute(onBrandClick: (brandId: Int) -> Unit, onBackClick: () -> Unit) {
    BrandSearchScreen(onBrandClick = { onBrandClick(it) }, onBackClick = { onBackClick() })
}

@Composable
fun BrandSearchScreen(
    onBrandClick: (brandId: Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: BrandSearchViewmodel = hiltViewModel(),
) {
    val brands = viewModel.getConsonantBrandsPagingSource()?.collectAsLazyPagingItems()
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (brands?.loadState?.prepend) {
            LoadState.Loading -> {}
            is LoadState.NotLoading -> {
                BrandSearchContent(brands, onBrandClick = { onBrandClick(it) }, onBackClick = { onBackClick() })
            }

            is LoadState.Error -> {}
            null -> {}
        }
    }
}

@Composable
fun BrandSearchContent(
    brands: LazyPagingItems<BrandDefaultResponseDto>?,
    onBrandClick: (brandId: Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column {
        SearchTopBar(
            searchWord = "",
            onChangeWord = {},
            onClearWord = {},
            onClickSearch = {},
            onNavBack = { onBackClick() })
        BrandGridView(brands = brands, onBrandClick = { onBrandClick(it) })
    }
}

@Composable
fun BrandGridView(brands: LazyPagingItems<BrandDefaultResponseDto>?, onBrandClick: (brandId: Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        item(span = { GridItemSpan(4) }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TypeBadge(
                    roundedCorner = 20.dp,
                    type = "한글",
                    fontColor = Color.White,
                    fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                    unSelectedColor = Color.Black,
                    selectedColor = Color.Black
                )
            }
        }
        items(brands?.itemSnapshotList ?: emptyList()) {
            BrandItem(brand = it, onBrandClick = { onBrandClick(it) })
        }
    }
}

@Composable
fun BrandItem(brand: BrandDefaultResponseDto?, onBrandClick: (brandId: Int) -> Unit) {
    Column(modifier = Modifier.clickable { onBrandClick(brand!!.brandId) }) {
        Column(
            modifier = Modifier.border(BorderStroke(width = 2.dp, color = CustomColor.gray9))
                .width(100.dp).height(100.dp).background(Color.White),
        ) {
            ImageView(
                imageUrl = null,
                width = 0.9f,
                height = 1f,
                backgroundColor = Color.White,
                contentScale = ContentScale.FillWidth
            )
        }
        Text(
            text = brand?.brandName ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}