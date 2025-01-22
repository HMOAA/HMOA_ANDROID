package com.hmoa.feature_brand.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.component.TagBadge
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.component.WrapContentView
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.Consonant
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
    val uiState by viewModel.uiState.collectAsState()
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().requiredSize(width = width.dp, height = height.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (uiState) {
            is BrandSearchViewmodel.BrandSearchUiState.Loading -> {}
            is BrandSearchViewmodel.BrandSearchUiState.Data -> {
                BrandSearchContent(
                    consonantBrands = (uiState as BrandSearchViewmodel.BrandSearchUiState.Data).consonants,
                    onBrandClick = { onBrandClick(it) },
                    onBackClick = { onBackClick() },
                    onClearWord = { viewModel.clearWord() },
                    onChangedWord = { viewModel.searchBrandResult(it) },
                    onClickSearch = { viewModel.searchBrandResult(it) },
                    searchWord = (uiState as BrandSearchViewmodel.BrandSearchUiState.Data).searchWord,
                    searchResult = (uiState as BrandSearchViewmodel.BrandSearchUiState.Data).searchResult
                )
            }

            is BrandSearchViewmodel.BrandSearchUiState.Error -> {}
        }
    }
}

@Composable
fun BrandSearchContent(
    searchWord: String,
    consonantBrands: Map<Consonant?, List<BrandDefaultResponseDto>?>?,
    searchResult: Map<Consonant?, List<BrandDefaultResponseDto>?>?,
    onBrandClick: (brandId: Int) -> Unit,
    onBackClick: () -> Unit,
    onChangedWord: (word: String) -> Unit,
    onClearWord: () -> Unit,
    onClickSearch: (word: String) -> Unit
) {
    Column {
        Row(modifier = Modifier.padding(start = 16.dp)) {
            SearchTopBar(
                searchWord = searchWord,
                onChangeWord = { onChangedWord(it) },
                onClearWord = { onClearWord() },
                onClickSearch = { onClickSearch(searchWord) },
                navBack = { onBackClick() }
            )
        }

        Spacer(
            modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray3)
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                .padding(top = 16.dp)
        ) {
            if (searchWord == "") {
                LazyColumn {
                    items(Consonant.entries.toList()) { consonant ->
                        BrandGridView(
                            brands = consonantBrands?.get(consonant),
                            onBrandClick = { onBrandClick(it) },
                            consonant = consonant
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(Consonant.entries.toList()) { consonant ->
                        BrandGridView(
                            brands = searchResult?.get(consonant),
                            onBrandClick = { onBrandClick(it) },
                            consonant = consonant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BrandGridView(brands: List<BrandDefaultResponseDto>?, onBrandClick: (brandId: Int) -> Unit, consonant: Consonant) {
    val entriesPerRow = 4
    if ((brands?.size ?: 0) > 0) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                TypeBadge(
                    roundedCorner = 20.dp,
                    type = "  ${consonant.name}  ",
                    fontColor = Color.White,
                    fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                    unSelectedColor = Color.Black,
                    selectedColor = Color.Black
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                WrapContentView(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    brands?.map { brand ->
                        Column(modifier = Modifier.padding(horizontal = 4.dp).padding(bottom = 10.dp)) {
                            BrandItem(
                                brand = brand,
                                onBrandClick = { onBrandClick(brand.brandId) })
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BrandItem(brand: BrandDefaultResponseDto?, onBrandClick: (brandId: Int) -> Unit) {
    Column(
        modifier = Modifier.clickable { onBrandClick(brand!!.brandId) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TagBadge(tag = brand?.brandName ?: "")
    }
}
