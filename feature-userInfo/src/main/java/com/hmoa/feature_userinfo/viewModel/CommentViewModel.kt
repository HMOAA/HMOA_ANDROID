package com.hmoa.feature_userinfo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.MemberRepository
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
    private val repository : MemberRepository,
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
    }

    //type 변환
    fun changeType(newType : String){
        if (_type.value != newType) {
            _type.update{newType}
        }
    }

    //comment list 업데이트
    fun updateComments(){
        viewModelScope.launch(Dispatchers.IO){
            if (type.value == "Perfume") {
                commentUseCaseByPerfume(page.value)
            } else {
                commentUseCaseByPost(page.value)
            }.map{
                _comments.update{it}
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