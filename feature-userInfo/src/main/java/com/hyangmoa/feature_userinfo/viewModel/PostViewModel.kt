package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.CommunityRepository
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.feature_userinfo.CommunityPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    val uiState: StateFlow<PostUiState> = flow { emit(commentPagingSource()) }.asResult().map { result ->
        when (result) {
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
    private fun commentPagingSource(): Flow<PagingData<CommunityByCategoryResponseDto>> = Pager(
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
        val posts: Flow<PagingData<CommunityByCategoryResponseDto>>
    ) : PostUiState

    data object Error : PostUiState
}