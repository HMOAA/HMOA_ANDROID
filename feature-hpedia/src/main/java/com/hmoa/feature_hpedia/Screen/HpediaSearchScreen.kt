package com.hmoa.feature_hpedia.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_model.response.NoteDefaultResponseDto
import com.hmoa.core_model.response.PerfumerDefaultResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto
import com.hmoa.feature_hpedia.ViewModel.HPediaSearchViewModel

@Composable
fun HPediaSearchRoute(
    type : String?,
    navBack : () -> Unit,
    navHPediaDesc : (hpediaId: Int, type: String) -> Unit,
    viewModel : HPediaSearchViewModel = hiltViewModel()
){
    viewModel.setType(type)

    val errState by viewModel.errorUiState.collectAsStateWithLifecycle()
    val topBarState = viewModel.topBarState.collectAsStateWithLifecycle()
    val searchWord = viewModel.searchWord.collectAsStateWithLifecycle()
    val type = viewModel.type.collectAsStateWithLifecycle()
    val result = viewModel.communityPagingSource().collectAsLazyPagingItems()
    val onClearWord = remember<() -> Unit> {{viewModel.updateSearchWord("")}}

    HPediaSearchScreen(
        errState = errState,
        type = type.value,
        topBarState = topBarState.value,
        onChagneTopBarState = viewModel::updateTopBarState,
        searchWord = searchWord.value,
        onChangeSearchWord = viewModel::updateSearchWord,
        onClearWord = onClearWord,
        onClickSearch = {  },
        termResult = if(type.value == "용어") (result as LazyPagingItems<TermDefaultResponseDto>) else null,
        noteResult = if(type.value == "노트") (result as LazyPagingItems<NoteDefaultResponseDto>) else null,
        perfumerResult = if (type.value == "조향사") (result as LazyPagingItems<PerfumerDefaultResponseDto>) else null,
        navBack = navBack,
        navHPediaDesc = navHPediaDesc,
    )
}

@Composable
fun HPediaSearchScreen(
    errState: ErrorUiState,
    type : String?,
    topBarState : Boolean,
    onChagneTopBarState : (topBarState: Boolean) -> Unit,
    searchWord : String,
    onChangeSearchWord : (keyword: String) -> Unit,
    onClearWord : () -> Unit,
    onClickSearch : () -> Unit,
    termResult : LazyPagingItems<TermDefaultResponseDto>? = null,
    noteResult : LazyPagingItems<NoteDefaultResponseDto>? = null,
    perfumerResult : LazyPagingItems<PerfumerDefaultResponseDto>? = null,
    navBack: () -> Unit,
    navHPediaDesc: (hpediaId: Int, type: String) -> Unit
){
    if (errState is ErrorUiState.ErrorData && errState.isValidate()){
        ErrorUiSetView(
            onLoginClick = navBack,
            errorUiState = errState,
            onCloseClick = navBack
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            HPediaEventTopBar(
                type = type ?: "Null Type",
                topBarState = topBarState,
                onChagneTopBarState = onChagneTopBarState,
                searchWord = searchWord,
                onChangeSearchWord = onChangeSearchWord,
                onClearWord = onClearWord,
                onClickSearch = onClickSearch,
                navBack = navBack
            )
            HPediaSearchResult(
                type = type ?: "Null Type",
                termResult = termResult,
                noteResult = noteResult,
                perfumerResult = perfumerResult,
                navHPediaDesc = navHPediaDesc
            )
        }
    }
}

@Composable
fun HPediaEventTopBar(
    type : String,
    topBarState : Boolean,
    onChagneTopBarState : (topBarState: Boolean) -> Unit,
    searchWord : String,
    onChangeSearchWord : (keyword: String) -> Unit,
    onClearWord : () -> Unit,
    onClickSearch : () -> Unit,
    navBack : () -> Unit
){
    if (topBarState){
        SearchTopBar(
            searchWord = searchWord,
            onChangeWord = { onChangeSearchWord(it) },
            onClearWord = onClearWord,
            onClickSearch = onClickSearch,
            navBack = navBack
        )
    } else {
        TopBar(
            title = type,
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = navBack,
            menuIcon = painterResource(R.drawable.ic_search),
            onMenuClick = {onChagneTopBarState(true)}
        )
    }
}

@Composable
fun HPediaSearchResult(
    type : String,
    termResult : LazyPagingItems<TermDefaultResponseDto>? = null,
    noteResult : LazyPagingItems<NoteDefaultResponseDto>? = null,
    perfumerResult : LazyPagingItems<PerfumerDefaultResponseDto>? = null,
    navHPediaDesc: (hpediaId: Int, type: String) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        if(termResult != null){
            items(
                items = termResult.itemSnapshotList,
                key = {it!!.termId}
            ){
                if (it != null){
                    HPediaResultItem(
                        type = type,
                        id = it.termId,
                        koTitle = it.termTitle,
                        engTitle = it.termEnglishTitle,
                        navHPediaDesc = navHPediaDesc
                    )
                }
            }
        }
        else if (noteResult != null) {
            items(
                items = noteResult.itemSnapshotList,
                key = {it!!.noteId}
            ){
                if (it != null){
                    HPediaResultItem(
                        type = type,
                        id = it.noteId,
                        koTitle = it.noteTitle,
                        engTitle = it.noteSubtitle,
                        navHPediaDesc = navHPediaDesc
                    )
                }
            }
        }
        else if (perfumerResult != null){
            items(
                items = perfumerResult.itemSnapshotList,
                key = {it!!.perfumerId}
            ){
                if (it != null){
                    HPediaResultItem(
                        type = type,
                        id = it.perfumerId,
                        koTitle = it.perfumerTitle,
                        engTitle = it.perfumerSubTitle,
                        navHPediaDesc = navHPediaDesc
                    )
                }
            }
        }
    }
}

@Composable
fun HPediaResultItem(
    type : String,
    id : Int,
    koTitle : String,
    engTitle : String,
    navHPediaDesc: (hpediaId: Int, type: String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { navHPediaDesc(id, type) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = engTitle,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = koTitle,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular))
        )
    }
}