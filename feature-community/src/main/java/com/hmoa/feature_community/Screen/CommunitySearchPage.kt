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
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.SearchTopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_community.ViewModel.CommunitySearchUiState
import com.hmoa.feature_community.ViewModel.CommunitySearchViewModel

@Composable
fun CommunitySearchRoute(
    navBack: () -> Unit,
    navCommunityDesc: (Int) -> Unit,
    viewModel : CommunitySearchViewModel = hiltViewModel()
){
    val searchWord = viewModel.searchWord.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CommunitySearchPage(
        uiState = uiState.value,
        searchWord = searchWord.value,
        onSearchWordChanged = { viewModel.updateSearchWord(it) },
        onClearSearchWord = { viewModel.clearSearchWord() },
        onClickSearch = { viewModel.updateFlag() },
        navBack = navBack,
        navCommunityDesc = navCommunityDesc
    )
}

@Composable
fun CommunitySearchPage(
    uiState : CommunitySearchUiState,
    searchWord : String,
    onSearchWordChanged : (String) -> Unit,
    onClearSearchWord : () -> Unit,
    onClickSearch : () -> Unit,
    navBack : () -> Unit,
    navCommunityDesc : (Int) -> Unit
){

    when(uiState){
        CommunitySearchUiState.Loading ->  AppLoadingScreen()
        is CommunitySearchUiState.SearchResult -> {
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
                    navBack = navBack
                )

                HorizontalDivider(thickness = 1.dp, color = CustomColor.gray3)

                LazyColumn(
                    modifier = Modifier.weight(1f),
                ){
                    items(uiState.result){community ->
                        PostListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            onPostClick = { navCommunityDesc(community.communityId) },
                            postType = community.category,
                            postTitle = community.title,
                            heartCount = community.heartCount,
                            commentCount = community.commentCount
                        )

                        if (community != uiState.result[uiState.result.lastIndex]){
                            HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
                        }
                    }
                }
            }
        }
        CommunitySearchUiState.Error -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)){}
        }
    }
}