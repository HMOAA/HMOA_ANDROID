package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityHomeViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {

    private val _community = MutableStateFlow(emptyList<CommunityByCategoryResponseDto>())
    val community get() = _community.asStateFlow()

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    init {
        viewModelScope.launch {
            _community.update {
                val result = repository.getCommunitiesHome()
                if (result.exception is Exception) {
                    throw result.exception!!
                } else {
                    result.data!!
                }
            }
        }
    }

    val uiState: StateFlow<CommunityHomeUiState> = community.asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> {
                    CommunityHomeUiState.Loading
                }

                is Result.Success -> {
                    CommunityHomeUiState.Community(result.data)
                }

                is Result.Error -> {
                    CommunityHomeUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = CommunityHomeUiState.Loading
        )

}

sealed interface CommunityHomeUiState {
    data object Loading : CommunityHomeUiState
    data class Community(
        val communities: List<CommunityByCategoryResponseDto>
    ) : CommunityHomeUiState

    data object Error : CommunityHomeUiState
}