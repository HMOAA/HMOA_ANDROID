package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.data.DefaultAddressDto
import com.hmoa.core_model.data.DefaultOrderInfoDto
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val hshopRepository: HshopRepository,
    private val memberRepository: MemberRepository
): ViewModel() {
    private val productIds = MutableStateFlow<List<Int>>(emptyList())
    private val _buyerInfo = MutableStateFlow<DefaultOrderInfoDto?>(null)
    val buyerInfo get() = _buyerInfo.asStateFlow()
    private val _addressInfo = MutableStateFlow<DefaultAddressDto?>(null)
    val addressInfo get() = _addressInfo.asStateFlow()

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
    val uiState: StateFlow<OrderUiState> = combine(productIds,errorUiState){ ids, errState ->
        if(ids.isEmpty()) throw Exception("데이터 로딩 오류")
        if(errState is ErrorUiState.ErrorData && errState.inValidate()) throw Exception("ERROR")
        val requestDto = ProductListRequestDto(productIds = ids)
        val request = hshopRepository.postNotesSelected(requestDto)
        if (request.errorMessage != null) throw Exception(request.errorMessage!!.message)
        request.data
    }.asResult().map{result ->
        when(result){
            Result.Loading -> OrderUiState.Loading
            is Result.Success -> OrderUiState.Success(result.data!!)
            is Result.Error -> {
                if(errorUiState.value !is ErrorUiState.ErrorData && !(errorUiState.value as ErrorUiState.ErrorData).inValidate()){
                    if (result.exception.toString().contains("statusCode=401")) {
                        wrongTypeTokenErrorState.update{ true }
                    } else {
                        generalErrorState.update{Pair(true, result.exception.message)}
                    }
                }
                OrderUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OrderUiState.Loading
    )

    init{
        getBuyerInfo()
        getPhoneInfo()
    }

    fun setIds(newProductIds: List<Int>) = productIds.update{newProductIds}
    private fun getBuyerInfo(){
        viewModelScope.launch{
            val result = memberRepository.getOrderInfo()
            if (result.errorMessage != null){
                when(result.errorMessage!!.code){
                    "404" -> {}
                    "401" -> unLoginedErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            _buyerInfo.update{result.data}
        }
    }
    private fun getPhoneInfo(){
        viewModelScope.launch{
            val result = memberRepository.getAddress()
            if (result.errorMessage != null){
                when(result.errorMessage!!.code){
                    "404" -> {}
                    "401" -> unLoginedErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            _addressInfo.update{result.data}
        }
    }
    fun saveBuyerInfo(name: String, phoneNumber: String){
        viewModelScope.launch{
            val requestDto = DefaultOrderInfoDto(name = name, phoneNumber = phoneNumber)
            val result = memberRepository.postOrderInfo(requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.code){
                    "404" -> wrongTypeTokenErrorState.update{true}
                    "401" -> unLoginedErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
            }
            _buyerInfo.update{DefaultOrderInfoDto(name, phoneNumber)}
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
        val noteResult: PostNoteSelectedResponseDto
    ): OrderUiState
}