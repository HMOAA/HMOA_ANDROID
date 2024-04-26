package com.hmoa.feature_like.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val perfumeRepository : PerfumeRepository
) : ViewModel() {
    private val errState = MutableStateFlow<String?>(null)

    val uiState : StateFlow<LikeUiState> = errState.map{
        if (errState.value != null){
            throw Exception(errState.value!!)
        }
        val result = perfumeRepository.getLikePerfumes()
        if (result.exception is Exception){
            throw result.exception!!
        }
        result.data!!
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> LikeUiState.Loading
            is Result.Success -> {
                LikeUiState.Like(result.data.data)
            }
            is Result.Error -> LikeUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = LikeUiState.Loading
    )

}

sealed interface LikeUiState{
    data object Loading : LikeUiState
    data class Like(
        val perfumes : List<PerfumeLikeResponseDto>
    ) : LikeUiState
    data object Error : LikeUiState
}