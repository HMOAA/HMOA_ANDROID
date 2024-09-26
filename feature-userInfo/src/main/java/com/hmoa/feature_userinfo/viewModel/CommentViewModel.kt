package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.feature_userinfo.viewModel.PAGE_SIZE
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.feature_userinfo.CommentPagingSource
import com.hmoa.core_domain.entity.data.MyPageCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val communityCommentRepository: CommunityCommentRepository,
    private val perfumeCommentRepository: PerfumeCommentRepository,
): ViewModel() {

    //선택된 type
    private val _type = MutableStateFlow(MyPageCategory.향수.name)
    val type get() = _type.asStateFlow()

    //comment 리스트
    private val _comments = MutableStateFlow(emptyList<CommunityCommentDefaultResponseDto>())
    val comments get() = _comments.asStateFlow()

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState: StateFlow<CommentUiState> = type.map{
        commentPagingSource(it)
    }.asResult().map{ result ->
        when(result) {
            Result.Loading -> CommentUiState.Loading
            is Result.Success -> CommentUiState.Comments(result.data)
            is Result.Error -> CommentUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommentUiState.Loading
    )

    //type 변환
    fun changeType(newType : String){
        _type.update{ newType }
    }

    //댓글 Paging
    private fun commentPagingSource(type : String) : Flow<PagingData<CommunityCommentDefaultResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getFavoriteCommentPaging(type)
        }
    ).flow.cachedIn(viewModelScope)


    //paging
    private fun getFavoriteCommentPaging(type : String) = CommentPagingSource(
        communityCommentRepository = communityCommentRepository,
        perfumeCommentRepository = perfumeCommentRepository,
        type = type
    )
}

sealed interface CommentUiState{
    data object Loading : CommentUiState
    data class Comments(
        val comments : Flow<PagingData<CommunityCommentDefaultResponseDto>>
    ) : CommentUiState
    data object Error : CommentUiState
}