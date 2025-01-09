package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val commentId = MutableStateFlow<Int>(-1)

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
        if (errorState is ErrorUiState.ErrorData && errorState.isValidate()) throw Exception("")
        val response = communityCommentRepository.getCommunityComment(id)
        if (response.errorMessage != null) throw Exception(response.errorMessage!!.message)
        response.data!!
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> CommunityCommentEditUiState.Loading
            is Result.Success -> CommunityCommentEditUiState.Comment(comment = result.data.content)
            is Result.Error -> {
                if (result.exception.message != "") {
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = {expiredTokenErrorState.update{true}},
                        onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                        onUnknownError = {unLoginedErrorState.update{true}},
                        onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}},
                    )
                }
                CommunityCommentEditUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityCommentEditUiState.Loading
    )

    //comment id 설정
    fun setId(id: Int?) {
        if (id == null) {
            generalErrorState.update { Pair(true, "해당 댓글을 찾을 수 없습니다.") }
            return
        }
        commentId.update { id }
    }

    //comment 수정
    fun editComment(newComment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val requestDto = CommunityCommentDefaultRequestDto(newComment)
            val result = communityCommentRepository.putCommunityComment(
                commentId = commentId.value,
                dto = requestDto
            )
            if (result.errorMessage != null) {
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            onSuccess()
        }
    }

}

sealed interface CommunityCommentEditUiState {
    data object Error : CommunityCommentEditUiState
    data class Comment(val comment: String) : CommunityCommentEditUiState
    data object Loading : CommunityCommentEditUiState
}