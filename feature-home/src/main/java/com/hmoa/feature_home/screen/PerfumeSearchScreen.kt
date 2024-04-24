package com.hmoa.feature_home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeNameSearchResponseDto
import com.hmoa.feature_home.viewmodel.PerfumeSearchViewmodel

@Composable
fun PerfumeSearchRoute(onPerfumeSearchResultClick: (searchWord: String) -> Unit, onBackClick: () -> Unit) {
    PerfumeSearchScreen(
        onPerfumeSearchResultClick = { onPerfumeSearchResultClick(it) },
        onBackClick = { onBackClick() }
    )
}

@Composable
fun PerfumeSearchScreen(
    onPerfumeSearchResultClick: (searchWord: String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: PerfumeSearchViewmodel = hiltViewModel()
) {
    val perfumeSearchResult = viewModel.getPagingPerfumeSearchResults()?.collectAsLazyPagingItems()
    val searchWord = viewModel.searchWordState.collectAsStateWithLifecycle()

    PerfumeSearchContent(
        searchWord = searchWord.value,
        searchResult = perfumeSearchResult,
        onChangedWord = {
            viewModel.updateSearchWord(it)
            perfumeSearchResult?.refresh()
        },
        onClearWord = { viewModel.updateSearchWord(word = "") },
        onClickSearch = { onPerfumeSearchResultClick(it) },
        onPerfumeSearchResultClick = { onPerfumeSearchResultClick(it) },
        onBackClick = { onBackClick() },
    )
}

@Composable
fun PerfumeSearchContent(
    searchWord: String?,
    searchResult: LazyPagingItems<PerfumeNameSearchResponseDto>?,
    onChangedWord: (word: String) -> Unit,
    onClearWord: () -> Unit,
    onClickSearch: (word: String) -> Unit,
    onPerfumeSearchResultClick: (searchWord: String) -> Unit,
    onBackClick: () -> Unit,
) {
    Column {
        Row(modifier = Modifier.padding(start = 16.dp)) {
            SearchTopBar(
                searchWord = searchWord ?: "",
                onChangeWord = { onChangedWord(it) },
                onClearWord = { onClearWord() },
                onClickSearch = { onClickSearch(searchWord ?: "") },
                onNavBack = { onBackClick() }
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2))
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            items(searchResult?.itemSnapshotList?.items ?: emptyList()) {
                Text(
                    text = it!!.perfumeName ?: "",
                    modifier = Modifier.clickable { onPerfumeSearchResultClick(it.perfumeName) }
                        .padding(vertical = 10.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
                )
            }
        }
    }
}
