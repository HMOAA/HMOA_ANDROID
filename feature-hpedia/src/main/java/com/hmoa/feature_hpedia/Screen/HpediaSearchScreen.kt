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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_model.response.NoteDefaultResponseDto
import com.hmoa.core_model.response.PerfumerDefaultResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto
import com.hmoa.feature_hpedia.ViewModel.HPediaSearchViewModel

@Composable
fun HPediaSearchRoute(
    type : String?,
    onNavBack : () -> Unit,
    onNavHPediaDesc : (Int) -> Unit,
    viewModel : HPediaSearchViewModel = hiltViewModel()
){
    viewModel.setType(type)

    val topBarState = viewModel.topBarState.collectAsStateWithLifecycle()
    val searchWord = viewModel.searchWord.collectAsStateWithLifecycle()

    val result = viewModel.communityPagingSource().collectAsLazyPagingItems()

    HPediaSearchScreen(
        topBarState = topBarState.value,
        onChagneTopBarState = { viewModel.updateTopBarState(it) },
        searchWord = searchWord.value,
        onChangeSearchWord = { viewModel.updateSearchWord(it) },
        onClearWord = { viewModel.updateSearchWord("") },
        onClickSearch = {  },
        termResult = if(type == "용어") (result as LazyPagingItems<TermDefaultResponseDto>) else null,
        noteResult = if(type == "노트") (result as LazyPagingItems<NoteDefaultResponseDto>) else null,
        perfumerResult = if (type == "조향사") (result as LazyPagingItems<PerfumerDefaultResponseDto>) else null,
        onNavBack = onNavBack,
        onNavHPediaDesc = onNavHPediaDesc,
    )
}

@Composable
fun HPediaSearchScreen(
    topBarState : Boolean,
    onChagneTopBarState : (Boolean) -> Unit,
    searchWord : String,
    onChangeSearchWord : (String) -> Unit,
    onClearWord : () -> Unit,
    onClickSearch : () -> Unit,
    termResult : LazyPagingItems<TermDefaultResponseDto>? = null,
    noteResult : LazyPagingItems<NoteDefaultResponseDto>? = null,
    perfumerResult : LazyPagingItems<PerfumerDefaultResponseDto>? = null,
    onNavBack: () -> Unit,
    onNavHPediaDesc: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        HPediaEventTopBar(
            topBarState = topBarState,
            onChagneTopBarState = onChagneTopBarState,
            searchWord = searchWord,
            onChangeSearchWord = onChangeSearchWord,
            onClearWord = onClearWord,
            onClickSearch = onClickSearch,
            onNavBack = onNavBack
        )
        HPediaSearchResult(
            termResult = termResult,
            noteResult = noteResult,
            perfumerResult = perfumerResult,
            onNavHPediaDesc = onNavHPediaDesc
        )
    }
}

@Composable
fun HPediaEventTopBar(
    topBarState : Boolean,
    onChagneTopBarState : (Boolean) -> Unit,
    searchWord : String,
    onChangeSearchWord : (String) -> Unit,
    onClearWord : () -> Unit,
    onClickSearch : () -> Unit,
    onNavBack : () -> Unit
){
    if (topBarState){
        SearchTopBar(
            searchWord = searchWord,
            onChangeWord = { onChangeSearchWord(it) },
            onClearWord = onClearWord,
            onClickSearch = onClickSearch,
            onNavBack = onNavBack
        )
    } else {
        TopBar(
            title = "type",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack,
            menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_search),
            onMenuClick = {
                onChagneTopBarState(true)
            }
        )
    }
}

@Composable
fun HPediaSearchResult(
    termResult : LazyPagingItems<TermDefaultResponseDto>? = null,
    noteResult : LazyPagingItems<NoteDefaultResponseDto>? = null,
    perfumerResult : LazyPagingItems<PerfumerDefaultResponseDto>? = null,
    onNavHPediaDesc: (Int) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        if(termResult != null){
            items(termResult.itemSnapshotList){
                if (it != null){
                    HPediaResultItem(
                        id = it.termId,
                        koTitle = it.termTitle,
                        engTitle = it.termEnglishTitle,
                        onNavHPediaDesc = onNavHPediaDesc
                    )
                }
            }
        }
        else if (noteResult != null) {
            items(noteResult.itemSnapshotList){
                if (it != null){
                    HPediaResultItem(
                        id = it.noteId,
                        koTitle = it.noteTitle,
                        engTitle = it.noteSubtitle,
                        onNavHPediaDesc = onNavHPediaDesc
                    )
                }
            }
        }
        else if (perfumerResult != null){
            items(perfumerResult.itemSnapshotList){
                if (it != null){
                    HPediaResultItem(
                        id = it.perfumerId,
                        koTitle = it.perfumerTitle,
                        engTitle = it.perfumeSubTitle,
                        onNavHPediaDesc = onNavHPediaDesc
                    )
                }
            }
        }
    }
}

@Composable
fun HPediaResultItem(
    id : Int,
    koTitle : String,
    engTitle : String,
    onNavHPediaDesc: (Int) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onNavHPediaDesc(id)
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = engTitle,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = koTitle,
            fontSize = 16.sp
        )
    }
}