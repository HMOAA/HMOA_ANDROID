package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.OrderRecordDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrderRecordViewModel @Inject constructor(
    private val memberRepository: MemberRepository
): ViewModel() {

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

    val uiState: StateFlow<OrderRecordUiState> = errorUiState.map{
        if (it is ErrorUiState.ErrorData && it.isValidate()) return@map
        val result = memberRepository.getOrder()
        if (result.errorMessage != null) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.filterNot{it == Unit}.asResult().map{ result ->
        when(result){
            Result.Loading -> OrderRecordUiState.Loading
            is Result.Success -> OrderRecordUiState.Success(result.data as List<OrderRecordDto>)
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update{true}},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                    onUnknownError = {unLoginedErrorState.update{true}},
                    onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                )
                OrderRecordUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OrderRecordUiState.Loading
    )

}

sealed interface OrderRecordUiState{
    data object Loading: OrderRecordUiState
    data object Error: OrderRecordUiState
    data class Success(
        val orderRecords: List<OrderRecordDto>
    ): OrderRecordUiState
}