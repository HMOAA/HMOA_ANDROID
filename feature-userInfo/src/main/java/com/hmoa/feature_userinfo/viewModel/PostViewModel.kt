package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.CommunityPagingSource
import com.hmoa.feature_userinfo.FavoriteCommentPagingSource
import com.hmoa.feature_userinfo.MyPageCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    val uiState: StateFlow<PostUiState> = flow{ emit(commentPagingSource()) }.asResult().map{ result ->
        when(result) {
            Result.Loading -> PostUiState.Loading
            is Result.Success -> PostUiState.Posts(result.data)
            is Result.Error -> PostUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = PostUiState.Loading
    )

    //댓글 Paging
    private fun commentPagingSource() : Flow<PagingData<CommunityByCategoryResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getCommunityPaging()
        }
    ).flow.cachedIn(viewModelScope)


    //paging
    private fun getCommunityPaging() = CommunityPagingSource(
        communityRepository = communityRepository
    )
}

sealed interface PostUiState {
    data object Loading : PostUiState
    data class Posts(
        val posts : Flow<PagingData<CommunityByCategoryResponseDto>>
    ) : PostUiState
    data object Error : PostUiState
}