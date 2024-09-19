package com.hmoa.feature_community.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.PostListItem
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunitySearchUiState
import com.hmoa.feature_community.ViewModel.CommunitySearchViewModel

@Composable
fun CommunitySearchRoute(
    onNavBack : () -> Unit,
    onNavCommunityDesc: (Int) -> Unit,
    viewModel : CommunitySearchViewModel = hiltViewModel()
){
    val searchWord = viewModel.searchWord.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()

    CommunitySearchPage(
        uiState = uiState.value,
        errState = errState.value,
        searchWord = searchWord.value,
        onSearchWordChanged = { viewModel.updateSearchWord(it) },
        onClearSearchWord = { viewModel.clearSearchWord() },
        onClickSearch = { viewModel.updateFlag() },
        onNavBack = onNavBack,
        onNavCommunityDesc = onNavCommunityDesc
    )
}

@Composable
fun CommunitySearchPage(
    uiState : CommunitySearchUiState,
    errState: ErrorUiState,
    searchWord : String,
    onSearchWordChanged : (String) -> Unit,
    onClearSearchWord : () -> Unit,
    onClickSearch : () -> Unit,
    onNavBack : () -> Unit,
    onNavCommunityDesc : (Int) -> Unit
){
    when(uiState){
        CommunitySearchUiState.Loading ->  AppLoadingScreen()
        is CommunitySearchUiState.SearchResult -> {
            SearchContent(
                communities = uiState.result,
                searchWord = searchWord,
                onSearchWordChanged = onSearchWordChanged,
                onClearSearchWord = onClearSearchWord,
                onClickSearch = onClickSearch,
                onNavBack = onNavBack,
                onNavCommunityDesc = onNavCommunityDesc
            )
        }
        CommunitySearchUiState.Error -> {
            ErrorUiSetView(
                onConfirmClick = onNavBack,
                errorUiState = errState,
                onCloseClick = onNavBack
            )
        }
    }
}

@Composable
private fun SearchContent(
    communities: List<CommunityByCategoryResponseDto>,
    searchWord: String,
    onSearchWordChanged: (String) -> Unit,
    onClearSearchWord: () -> Unit,
    onClickSearch: () -> Unit,
    onNavBack: () -> Unit,
    onNavCommunityDesc: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        SearchTopBar(
            searchWord = searchWord,
            onChangeWord = onSearchWordChanged,
            onClearWord = onClearSearchWord,
            onClickSearch = onClickSearch,
            onNavBack = onNavBack
        )
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray3)
        LazyColumn(
            modifier = Modifier.weight(1f),
        ){
            items(communities){community ->
                PostListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onPostClick = { onNavCommunityDesc(community.communityId) },
                    postType = community.category,
                    postTitle = community.title,
                    heartCount = community.heartCount,
                    commentCount = community.commentCount
                )
                if (communities.indexOf(community) != communities.lastIndex){
                    HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                }
            }
        }
    }
}