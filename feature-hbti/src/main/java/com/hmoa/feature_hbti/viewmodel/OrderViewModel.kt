package com.hmoa.feature_hbti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.data.DefaultAddressDto
import com.hmoa.core_model.data.DefaultOrderInfoDto
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.FinalOrderResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val hshopRepository: HshopRepository,
    private val memberRepository: MemberRepository
): ViewModel() {
    private var orderId = MutableStateFlow<Int?>(null)
    var isSavedBuyerInfo = MutableStateFlow<Boolean>(false)
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
    private val isExistBuyerInfo = MutableStateFlow<Boolean>(false)
    private val isExistAddressInfo = MutableStateFlow<Boolean>(false)
    private val buyerInfoFlow = isExistBuyerInfo.flatMapLatest{
        flow{
            if (it) {
                val result = memberRepository.getOrderInfo()
                if (result.errorMessage != null) {
                    when (result.errorMessage!!.message) {
                        ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update { true }
                        ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update { true }
                        ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update { true }
                        else -> generalErrorState.update {
                            Pair(true,result.errorMessage!!.message)
                        }
                    }
                    emit(null)
                } else {emit(result.data)}
            } else {emit(null)}
        }
    }
    private val addressInfoFlow = isExistAddressInfo.flatMapLatest{
        flow{
            if (it) {
                val result = memberRepository.getAddress()
                if (result.errorMessage != null) {
                    when (result.errorMessage!!.message) {
                        ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update { true }
                        ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update { true }
                        ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update { true }
                        else -> generalErrorState.update {
                            Pair(true,result.errorMessage!!.message)
                        }
                    }
                    emit(null)
                } else {emit(result.data)}
            } else {emit(null)}
        }
    }
    private val productIds = MutableStateFlow<List<Int>>(emptyList())
    private val orderInfoFlow = orderId.filterNotNull().map{
        val result = hshopRepository.getFinalOrderResult(it)
        if (result.errorMessage != null) {
            when(result.errorMessage!!.message){
                ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
            }
            throw Exception("Error")
        }
        result.data
    }
    private val _uiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState.Loading)
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OrderUiState.Loading
    )
    init {
        viewModelScope.launch{
            combine(errorUiState, buyerInfoFlow, addressInfoFlow, orderInfoFlow){ errState, _buyerInfo, addressInfo, orderInfo ->
                Log.d("TAG TEST", "ui state flow combine : ${errState}\n${_buyerInfo}\n${addressInfo}\n${orderInfo}")
                if (errState is ErrorUiState.ErrorData && errState.isValidate()) throw Exception("Error")
                if (orderInfo == null) throw Exception("데이터가 없습니다")
                var buyerInfo = if (_buyerInfo == null && addressInfo != null){
                    DefaultOrderInfoDto(
                        name = addressInfo.name,
                        phoneNumber = addressInfo.phoneNumber
                    )
                } else _buyerInfo
                Triple(buyerInfo, addressInfo, orderInfo)
            }.asResult().map{ result ->
                when(result){
                    Result.Loading -> OrderUiState.Loading
                    is Result.Error -> {
                        if(errorUiState.value !is ErrorUiState.ErrorData || !(errorUiState.value as ErrorUiState.ErrorData).isValidate()){generalErrorState.update{ Pair(true, result.exception.message) }}
                        OrderUiState.Error
                    }
                    is Result.Success -> OrderUiState.Success(result.data.first, result.data.second, result.data.third)
                }
            }.collect{_uiState.value = it}
        }
    }
    fun setIds(initIds: List<Int>) {
        productIds.update{initIds}
        runBlocking{
            if(initIds.isNotEmpty()){
                val requestDto = ProductListRequestDto(initIds)
                val result = hshopRepository.postNoteOrder(requestDto)
                if (result.errorMessage != null){
                    when(result.errorMessage!!.message){
                        ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                        ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                        ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                        else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                    }
                    return@runBlocking
                }
                if(result.data!!.isExistMemberAddress){
                    isExistBuyerInfo.update{true}
                    isExistAddressInfo.update{true}
                } else {
                    isExistBuyerInfo.update{result.data!!.isExistMemberInfo}
                    isExistAddressInfo.update{result.data!!.isExistMemberAddress}
                }
                if(result.data!!.isExistMemberInfo){isSavedBuyerInfo.update{true}}
                orderId.update{result.data!!.orderId}
            }
        }
    }
    fun saveBuyerInfo(name: String, phoneNumber: String){
        viewModelScope.launch{
            val requestDto = DefaultOrderInfoDto(name, phoneNumber)
            val result = memberRepository.postOrderInfo(requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            (uiState.value as OrderUiState.Success).updateBuyerInfo(DefaultOrderInfoDto(name, phoneNumber))
            isSavedBuyerInfo.update{true}
        }
    }

    fun deleteNote(id: Int){
        productIds.update{ ids -> ids.filter{noteId -> noteId != id}}
    }
}
sealed interface OrderUiState{
    data object Loading: OrderUiState
    data object Error: OrderUiState
    data class Success(
        var buyerInfo: DefaultOrderInfoDto?,
        val addressInfo: DefaultAddressDto?,
        val orderInfo: FinalOrderResponseDto
    ): OrderUiState{
        fun updateBuyerInfo(newBuyerInfo: DefaultOrderInfoDto){
            this.buyerInfo = newBuyerInfo
        }
    }
}