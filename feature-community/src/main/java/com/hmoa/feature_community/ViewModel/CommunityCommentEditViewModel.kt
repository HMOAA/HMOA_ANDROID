package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityCommentEditViewModel @Inject constructor(
    private val communityCommentRepository: CommunityCommentRepository
) : ViewModel() {

    private val commentId = MutableStateFlow<Int?>(null)

    private val _comment = MutableStateFlow<String>("")
    val comment get() = _comment.asStateFlow()

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

    val uiState: StateFlow<CommunityCommentEditUiState> = combine(errorUiState, commentId) { errorState, id ->
        if (id == null) throw NullPointerException("Id is NULL")
        if (errorState is ErrorUiState.ErrorData) throw Exception(errorState.generalError.second)
        val response = communityCommentRepository.getCommunityComment(id)
        if (response.errorMessage != null) throw Exception(response.errorMessage!!.message)
        response.data!!
    }.asResult()
        .map { result ->
            when (result) {
                Result.Loading -> CommunityCommentEditUiState.Loading
                is Result.Success -> {
                    val data = result.data
                    _comment.update { data.content }
                    CommunityCommentEditUiState.Comment
                }
                is Result.Error -> {
                    generalErrorState.update{Pair(true, result.exception.message)}
                    CommunityCommentEditUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = CommunityCommentEditUiState.Loading
        )
    //comment id 설정
    fun setId(id: Int?) = commentId.update { id }
    //comment 업데이트
    fun updateComment(newComment: String) = _comment.update { newComment }
    //comment 수정
    fun editComment() {
        viewModelScope.launch {
            val id = commentId.value!!
            val requestDto = CommunityCommentDefaultRequestDto(comment.value)
            val result = communityCommentRepository.putCommunityComment(
                commentId = id,
                dto = requestDto
            )
            if (result.errorMessage is ErrorMessage) generalErrorState.update{Pair(true, result.errorMessage!!.message)}
        }
    }

}

sealed interface CommunityCommentEditUiState {
    data object Error : CommunityCommentEditUiState
    data object Comment : CommunityCommentEditUiState
    data object Loading : CommunityCommentEditUiState
}