package com.hmoa.feature_community.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityMainViewModel @Inject constructor(
    private val repository : CommunityRepository
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
            _community.update { repository.getCommunityByCategory(Category.추천.name, page.value) }
        }
    }

    val uiState : StateFlow<CommunityMainUiState> = type.combine(page){ type, page ->
        repository.getCommunityByCategory(type.name, page)
    }.asResult().map{
        when (it) {
            is Result.Loading -> CommunityMainUiState.Loading
            is Result.Success -> CommunityMainUiState.Community(it.data)
            is Result.Error -> CommunityMainUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = CommunityMainUiState.Loading
    )

    //community 리스트 갱신
    private fun updateCommunity(
        isAdd : Boolean
    ){
        viewModelScope.launch{
            Log.d("TEST TAG", "Category : ${type.value}")
            repository.getCommunityByCategory(type.value.name, page.value)
                .map{
                    Log.d("TEST TAG", "community -> ${it}")
                    if(isAdd) {
                        _community.update { _community.value + it }
                    }
                    else {
                        _community.update { it }
                    }
                }
            Log.d("TEST TAG", "community : ${community.value}")
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
        val communities : List<CommunityByCategoryResponseDto>
    ) : CommunityMainUiState
    data object Error : CommunityMainUiState
}