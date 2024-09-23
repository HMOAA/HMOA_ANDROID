package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.BootpayRepository
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.response.FinalOrderResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefundViewModel @Inject constructor(
    private val bootpayRepository: BootpayRepository,
    private val hshopRepository: HshopRepository
): ViewModel(){
    private val orderId = MutableStateFlow<Int?>(null)
    val isDone = MutableStateFlow<Boolean>(false)

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

    val uiState = errorUiState.map{
        if(it is ErrorUiState.ErrorData && it.isValidate()) return@map
        val result = hshopRepository.getFinalOrderResult(orderId = orderId.value!!)
        if(result.errorMessage != null) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.filterNot{it == Unit}.asResult().map{ result ->
        when(result){
            Result.Loading -> RefundUiState.Loading
            is Result.Success -> RefundUiState.Success(result.data as FinalOrderResponseDto)
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update{true}},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                    onUnknownError = {unLoginedErrorState.update{true}},
                    onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                )
                RefundUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RefundUiState.Loading
    )

    fun setId(newId: Int?){
        if (newId == null) {
            generalErrorState.update{Pair(true, "해당 주문 내역을 찾을 수 없습니다.")}
            return
        }
        orderId.update{newId}
    }

    fun refundOrder(){
        viewModelScope.launch{
            val result = bootpayRepository.deleteOrder(orderId.value!!)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            isDone.update{true}
        }
    }
}

sealed interface RefundUiState{
    data object Loading: RefundUiState
    data object Error: RefundUiState
    data class Success(
        val data: FinalOrderResponseDto
    ): RefundUiState
}