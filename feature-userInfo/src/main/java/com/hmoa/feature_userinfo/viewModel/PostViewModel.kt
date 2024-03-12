package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetMyPostUseCase
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
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
class PostViewModel @Inject constructor(
    private val postUseCase: GetMyPostUseCase
) : ViewModel() {

    //선택된 type
    private val _type = MutableStateFlow("Perfume")
    val type get() = _type.asStateFlow()

    //현재 page
    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    //comment 리스트
    private val _posts = MutableStateFlow(emptyList<CommunityByCategoryResponseDto>())
    val posts get() = _posts.asStateFlow()

    val uiState: StateFlow<PostUiState> = combine(
        type,
        posts,
        PostUiState::Posts
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = PostUiState.Loading
    )

    //page 증가
    fun addPage(){
        _page.update{_page.value + 1}
        updatePosts(true)
    }

    //type 변환
    fun changeType(newType : String){
        if (_type.value != newType) {
            _type.update{newType}
        }
        _page.update{0}
        updatePosts(false)
    }

    //comment list 업데이트
    private fun updatePosts(isAdd : Boolean){
        viewModelScope.launch(Dispatchers.IO){
            postUseCase(page.value).asResult().map{result ->
                when(result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        _posts.update{
                            if (isAdd) {
                                it + result.data
                            } else {
                                result.data
                            }
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }
        }
    }

}

sealed interface PostUiState {
    data object Loading : PostUiState

    data class Posts(
        val type : String,
        val posts : List<CommunityByCategoryResponseDto>
    ) : PostUiState

    data object Empty : PostUiState
}