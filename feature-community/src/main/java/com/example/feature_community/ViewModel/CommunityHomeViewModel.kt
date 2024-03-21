package com.example.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.usecase.GetCommunityHomeUseCase
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityHomeViewModel @Inject constructor(
    communityHomeUseCase: GetCommunityHomeUseCase
) : ViewModel() {

    private val _comments = MutableStateFlow(emptyList<CommunityByCategoryResponseDto>())
    val community get() = _comments.asStateFlow()

    init{
        viewModelScope.launch{
            communityHomeUseCase().collect{
                _comments.value = it
            }
        }
    }

    val uiState : StateFlow<CommunityHomeUiState> = community.transform{
        emit(CommunityHomeUiState.Community(it))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = CommunityHomeUiState.Loading
    )

}

sealed interface CommunityHomeUiState {
    data object Loading : CommunityHomeUiState
    data class Community(
        val communities : List<CommunityByCategoryResponseDto>
    ) : CommunityHomeUiState
    data object Empty : CommunityHomeUiState
}