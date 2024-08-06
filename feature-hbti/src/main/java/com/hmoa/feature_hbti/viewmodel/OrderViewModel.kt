package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val hshopRepository: HshopRepository
): ViewModel() {
    private val productIds = MutableStateFlow<List<Int>>(emptyList())

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState: StateFlow<OrderUiState> = productIds.mapLatest{
        if(it.isEmpty()) throw Exception("데이터 로딩 오류")
        val requestDto = ProductListRequestDto(productIds = it)
        val request = hshopRepository.postNotesSelected(requestDto)
        if (request.errorMessage != null) throw Exception(request.errorMessage!!.message)
        request.data
    }.asResult().map{result ->
        when(result){
            Result.Loading -> OrderUiState.Loading
            is Result.Success -> OrderUiState.Success(result.data!!)
            is Result.Error -> {
                if (result.exception.toString().contains("statusCode=401")) {
                    wrongTypeTokenErrorState.update{ true }
                } else {
                    generalErrorState.update{Pair(true, result.exception.message)}
                }
                OrderUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OrderUiState.Loading
    )

    fun setIds(newProductIds: List<Int>) = productIds.update{newProductIds}
}

sealed interface OrderUiState{
    data object Loading: OrderUiState
    data object Error: OrderUiState
    data class Success(
        val noteResult: PostNoteSelectedResponseDto
    ): OrderUiState
}