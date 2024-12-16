package com.hmoa.feature_home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_designsystem.component.PerfumeItemView
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_domain.entity.data.PerfumeSearchViewType
import com.hmoa.core_model.response.PerfumeNameSearchResponseDto
import com.hmoa.core_model.response.PerfumeSearchResponseDto
import com.hmoa.feature_home.viewmodel.PerfumeSearchViewmodel
import kotlinx.coroutines.flow.flowOf

@Composable
fun PerfumeSearchRoute(onBackClick: () -> Unit) {
    PerfumeSearchScreen(
        onBackClick = { onBackClick() }
    )
}

@Composable
fun PerfumeSearchScreen(
    onBackClick: () -> Unit,
    viewModel: PerfumeSearchViewmodel = hiltViewModel()
) {
    val perfumeNameSearchResult = viewModel.getPagingPerfumeNameSearchResults()?.collectAsLazyPagingItems()
    val perfumeSearchResult = viewModel.getPagingPerfumeSearchResults()?.collectAsLazyPagingItems()
    val searchWord = viewModel.perfumeNameSearchWordState.collectAsStateWithLifecycle()
    val viewType = viewModel.searchResultViewType.collectAsStateWithLifecycle()

    PerfumeSearchContent(
        searchWord = searchWord.value,
        perfumeNameSearchResult = perfumeNameSearchResult,
        perfumeSearchResult = perfumeSearchResult,
        viewType = viewType.value,
        onChangedWord = { viewModel.handlePerfumeNameChange(it) },
        onClearWord = {
            viewModel.updatePerfumeNameSearchWord(word = "")
            viewModel.changeViewType(PerfumeSearchViewType.List)
        },
        onClickSearch = {
            perfumeSearchResult?.refresh()
            viewModel.changeViewType(PerfumeSearchViewType.Grid)
        },
        onPerfumeSearchResultClick = {
            viewModel.updatePerfumeSearchWord(it)
            perfumeSearchResult?.refresh()
            viewModel.changeViewType(PerfumeSearchViewType.Grid)
        },
        onBackClick = { onBackClick() },
    )
}

@Composable
fun PerfumeSearchContent(
    searchWord: String?,
    perfumeNameSearchResult: LazyPagingItems<PerfumeNameSearchResponseDto>?,
    perfumeSearchResult: LazyPagingItems<PerfumeSearchResponseDto>?,
    viewType: PerfumeSearchViewType,
    onChangedWord: (word: String) -> Unit,
    onClearWord: () -> Unit,
    onClickSearch: (word: String) -> Unit,
    onPerfumeSearchResultClick: (searchWord: String) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().background(color = Color.White)
    ) {
        Row(Modifier.fillMaxWidth().padding(start = 16.dp)) {
            SearchTopBar(
                searchWord = searchWord ?: "",
                onChangeWord = { onChangedWord(it) },
                onClearWord = { onClearWord() },
                onClickSearch = { onClickSearch(searchWord ?: "") },
                navBack = { onBackClick() }
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2))
        PerfumeSearchView(viewType, perfumeNameSearchResult, perfumeSearchResult, onPerfumeSearchResultClick)
    }
}

@Composable
fun PerfumeSearchView(
    viewType: PerfumeSearchViewType,
    perfumeNameSearchResult: LazyPagingItems<PerfumeNameSearchResponseDto>?,
    perfumeSearchResult: LazyPagingItems<PerfumeSearchResponseDto>?,
    onPerfumeSearchResultClick: (searchWord: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp).background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (viewType) {
            PerfumeSearchViewType.List -> {
                PerfumeNameSearchResultList(
                    perfumeNameList = perfumeNameSearchResult,
                    onPerfumeSearchResultClick = { onPerfumeSearchResultClick(it) })
            }

            PerfumeSearchViewType.Grid -> {
                PerfumeSearchResultList(
                    perfumeList = perfumeSearchResult,
                    onPerfumeSearchResultClick = { onPerfumeSearchResultClick(it) })
            }
        }
    }
}

@Composable
fun PerfumeNameSearchResultList(
    perfumeNameList: LazyPagingItems<PerfumeNameSearchResponseDto>?,
    onPerfumeSearchResultClick: (searchWord: String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
        items(perfumeNameList?.itemSnapshotList?.items ?: emptyList()) {
            Text(
                text = it!!.perfumeName ?: "",
                modifier = Modifier.clickable { onPerfumeSearchResultClick(it.perfumeName) }
                    .padding(vertical = 10.dp).fillMaxWidth(),
                style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
            )
        }
    }
}

@Composable
fun PerfumeSearchResultList(
    perfumeList: LazyPagingItems<PerfumeSearchResponseDto>?,
    onPerfumeSearchResultClick: (searchWord: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        items(perfumeList?.itemSnapshotList?.items ?: emptyList()) {
            Column(modifier = Modifier.clickable { onPerfumeSearchResultClick(it.perfumeName) }
                .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                PerfumeItemView(
                    imageUrl = it?.perfumeImageUrl ?: "",
                    perfumeName = it?.perfumeName ?: "",
                    brandName = it?.brandName ?: "",
                    containerWidth = 160,
                    containerHeight = 160,
                    imageWidth = 0.7f,
                    imageHeight = 0.7f,
                    imageBackgroundColor = Color.White,
                    imageBorderStroke = BorderStroke(width = 1.dp, color = CustomColor.gray9)
                )
            }
        }
    }
}

@Preview
@Composable
fun PerfumeSearchScreenPreview() {
    PerfumeSearchContent(
        searchWord = null,
        perfumeNameSearchResult = flowOf(
            PagingData.from(
                listOf(
                    PerfumeNameSearchResponseDto(perfumeName = "어쩌구1"),
                    PerfumeNameSearchResponseDto(perfumeName = "어쩌구1"),
                    PerfumeNameSearchResponseDto(perfumeName = "어쩌구1"),
                    PerfumeNameSearchResponseDto(perfumeName = "어쩌구1"),
                    PerfumeNameSearchResponseDto(perfumeName = "어쩌구1")
                )
            )
        ).collectAsLazyPagingItems(),
        perfumeSearchResult = flowOf(
            PagingData.from(
                listOf(
                    PerfumeSearchResponseDto(
                        brandName = "channel",
                        heart = true,
                        perfumeId = 0,
                        perfumeImageUrl = "",
                        perfumeName = "어쩌구1"
                    ),
                    PerfumeSearchResponseDto(
                        brandName = "channel",
                        heart = true,
                        perfumeId = 0,
                        perfumeImageUrl = "",
                        perfumeName = "어쩌구1"
                    ),
                    PerfumeSearchResponseDto(
                        brandName = "channel",
                        heart = true,
                        perfumeId = 0,
                        perfumeImageUrl = "",
                        perfumeName = "어쩌구1"
                    ),
                    PerfumeSearchResponseDto(
                        brandName = "channel",
                        heart = true,
                        perfumeId = 0,
                        perfumeImageUrl = "",
                        perfumeName = "어쩌구1"
                    )
                )
            )
        ).collectAsLazyPagingItems(),
        viewType = PerfumeSearchViewType.List,
        onChangedWord = {},
        onClearWord = {},
        onClickSearch = {},
        onPerfumeSearchResultClick = {},
        onBackClick = {}
    )
}
