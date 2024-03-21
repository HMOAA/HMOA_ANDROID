package com.example.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.usecase.GetCommunityMainUseCase
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityMainViewModel @Inject constructor(
    private val repository : CommunityRepository,
    private val communityMainUseCase : GetCommunityMainUseCase
) : ViewModel() {

    //type 정보
    private val _type = MutableStateFlow(Category.추천)
    val type get() = _type.asStateFlow()

    //community
    private val _community = MutableStateFlow(emptyList<CommunityByCategoryResponseDto>())
    val community get() = _community.asStateFlow()

    //page
    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    init {
        //초기화 시 추천 type으로 먼저 데이터를 받아옴
        viewModelScope.launch{
            communityMainUseCase(
                category = type.value.name,
                page = page.value
            ).collect{
                _community.update{it}
            }
        }
    }

    val uiState : StateFlow<CommunityMainUiState> = combine(
        type,
        community,
        CommunityMainUiState::Community
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = CommunityMainUiState.Loading
    )

    //community 리스트 갱신
    private fun updateCommunity(
        isAdd : Boolean
    ){
        viewModelScope.launch{
            communityMainUseCase(
                category = type.value.name,
                page = page.value
            ).collect{
                if (isAdd) {
                    _community.update {_community.value + it}
                } else {
                    _community.update { it }
                }
            }
        }
    }

    //category 정보 변경
    fun updateCategory(category : Category) {
        _type.update {category}
        updateCommunity(false)
    }

    //page 정보 변경
    fun addPage(){
        _page.update{_page.value + 1}
        updateCommunity(true)
    }

}

sealed interface CommunityMainUiState {
    data object Loading : CommunityMainUiState
    data class Community(
        val type : Category,
        val communities : List<CommunityByCategoryResponseDto>
    ) : CommunityMainUiState
    data object Empty : CommunityMainUiState
}