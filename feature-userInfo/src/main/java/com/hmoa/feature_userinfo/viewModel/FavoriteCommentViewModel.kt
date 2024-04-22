package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.FavoriteCommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val PAGE_SIZE = 10
@HiltViewModel
class FavoriteCommentViewModel @Inject constructor(
    private val communityCommentRepository : CommunityCommentRepository,
    private val perfumeCommentRepository: PerfumeCommentRepository,
) : ViewModel() {

    //선택된 type
    private val _type = MutableStateFlow("향수")
    val type get() = _type.asStateFlow()

    //comment 리스트
    private val _comments = MutableStateFlow(emptyList<CommunityCommentDefaultResponseDto>())
    val comments get() = _comments.asStateFlow()

    val uiState: StateFlow<FavoriteCommentUiState> = type.map{
        commentPagingSource(it)
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> {
                FavoriteCommentUiState.Loading
            }
            is Result.Success -> {
                FavoriteCommentUiState.Comments(result.data)
            }
            is Result.Error -> {
                FavoriteCommentUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = FavoriteCommentUiState.Loading
    )

    //type 변환
    fun changeType(newType : String){
        if (_type.value != newType) {
            _type.update{newType}
        }
    }

    //댓글 Paging
    private fun commentPagingSource(type : String) : Flow<PagingData<CommunityCommentDefaultResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getFavoriteCommentPaging(type)
        }
    ).flow.cachedIn(viewModelScope)


    //paging
    private fun getFavoriteCommentPaging(type : String) = FavoriteCommentPagingSource(
        communityCommentRepository = communityCommentRepository,
        perfumeCommentRepository = perfumeCommentRepository,
        type = type
    )
}

sealed interface FavoriteCommentUiState{
    data object Loading : FavoriteCommentUiState

    data class Comments(
        val comments : Flow<PagingData<CommunityCommentDefaultResponseDto>>
    ) : FavoriteCommentUiState

    data object Error : FavoriteCommentUiState
}