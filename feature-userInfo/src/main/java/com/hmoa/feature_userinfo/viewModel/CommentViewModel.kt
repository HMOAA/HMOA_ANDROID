package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetMyCommentByPerfumeUseCase
import com.hmoa.core_domain.usecase.GetMyCommentByPostUseCase
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class CommentViewModel @Inject constructor(
    private val commentUseCaseByPerfume : GetMyCommentByPerfumeUseCase,
    private val commentUseCaseByPost : GetMyCommentByPostUseCase
): ViewModel() {

    //선택된 type
    private val _type = MutableStateFlow("Perfume")
    val type get() = _type.asStateFlow()

    //현재 page
    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    //comment 리스트
    private val _comments = MutableStateFlow(emptyList<CommunityCommentDefaultResponseDto>())
    val comments get() = _comments.asStateFlow()

    init {
        //기본 perfume으로 데이터 init
        updateComments(false)
    }

    val uiState: StateFlow<CommentUiState> = combine(
        type,
        comments,
        CommentUiState::Comments
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommentUiState.Loading
    )

    //page 증가
    fun addPage(){
        _page.update{_page.value + 1}
        updateComments(true)
    }

    //type 변환
    fun changeType(newType : String){
        if (_type.value != newType) {
            _type.update{newType}
        }
        _page.update{0}
        updateComments(false)
    }

    //comment list 업데이트
    private fun updateComments(isAdd : Boolean){
        viewModelScope.launch(Dispatchers.IO){
            if (type.value == "Perfume") {
                commentUseCaseByPerfume(page.value)
            } else {
                commentUseCaseByPost(page.value)
            }.asResult().map{ result ->
                when (result) {
                    is Result.Success -> {
                        _comments.update{
                            if (isAdd) {
                                it + result.data
                            } else {
                                result.data
                            }
                        }
                    }
                    is Result.Loading -> {

                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }
}

sealed interface CommentUiState{
    data object Loading : CommentUiState

    data class Comments(
        val type : String,
        val comments : List<CommunityCommentDefaultResponseDto>
    ) : CommentUiState

    data object Empty : CommentUiState
}