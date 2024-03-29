package com.hmoa.feature_community.ViewModel

import ResultResponse
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

    //page
    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    val uiState : StateFlow<CommunityMainUiState> = type.combine(page){ type, page ->
        val result = repository.getCommunityByCategory(type.name, page)
        if (result.exception is Exception) {
            throw result.exception!!
        } else {
            result.data
        }
    }.asResult().map{
        when (it) {
            is Result.Loading -> CommunityMainUiState.Loading
            is Result.Success -> {
                if (it.data == null){
                    CommunityMainUiState.Error
                } else {
                    CommunityMainUiState.Community(it.data!!)
                }
            }
            is Result.Error -> CommunityMainUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = CommunityMainUiState.Loading
    )

    //category 정보 변경
    fun updateCategory(category : Category) {
        _type.update {category}
    }

    //page 정보 변경
    fun addPage(){
        _page.update{_page.value + 1}
    }

}

sealed interface CommunityMainUiState {
    data object Loading : CommunityMainUiState
    data class Community(
        val communities : List<CommunityByCategoryResponseDto>
    ) : CommunityMainUiState
    data object Error : CommunityMainUiState
}