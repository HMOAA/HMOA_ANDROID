package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _errState = MutableStateFlow<String?>(null)
    val errState get() = _errState.asStateFlow()

    val uiState : StateFlow<CommunityCommentEditUiState> = commentId.map{ id ->
        if (id == null) throw NullPointerException("Id is NULL")
        val response = communityCommentRepository.getCommunityComment(id)
        if (response.exception is Exception) {
            throw response.exception!!
        }
        response.data!!
    }.asResult()
        .map { result ->
            when (result) {
                is Result.Error -> {
                    CommunityCommentEditUiState.Error
                }
                is Result.Success -> {
                    val data = result.data
                    _comment.update{ data.content }
                    CommunityCommentEditUiState.Comment
                }
                is Result.Loading -> {
                    CommunityCommentEditUiState.Loading
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = CommunityCommentEditUiState.Loading
        )

    //comment id 설정
    fun setId(id : Int?) {
        commentId.update{ id }
    }

    //comment 업데이트
    fun updateComment(newComment : String) {
        _comment.update{ newComment }
    }

    //comment 수정
    fun editComment() {
        viewModelScope.launch{
            val id = commentId.value ?: return@launch
            val requestDto = CommunityCommentDefaultRequestDto(comment.value)
            try{
                communityCommentRepository.putCommunityComment(
                    commentId = id,
                    dto = requestDto
                )
            } catch (e : Exception) {
                _errState.update{ e.message.toString() }
            }
        }
    }

}

sealed interface CommunityCommentEditUiState {
    data object Error : CommunityCommentEditUiState
    data object Comment : CommunityCommentEditUiState
    data object Loading : CommunityCommentEditUiState
}