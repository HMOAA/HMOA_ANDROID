package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.data.DefaultAddressDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val memberRepository: MemberRepository
): ViewModel() {
    private val _isPostAddressCompleted = MutableStateFlow<Boolean>(false)
    val isPostAddressCompleted get() = _isPostAddressCompleted.asStateFlow()
    private val generalErrorState = MutableStateFlow<Pair<Boolean, String>>(Pair(false, ""))
    private val expiredTokenError = MutableStateFlow<Boolean>(false)
    private val wrongTypeToken = MutableStateFlow<Boolean>(false)
    val errorState: StateFlow<ErrorUiState> = combine(generalErrorState, expiredTokenError, wrongTypeToken){generalError, expiredTokenError, wrongTypeTokenError ->
        if (generalError.first || expiredTokenError || wrongTypeTokenError){
            ErrorUiState.ErrorData(
                expiredTokenError = expiredTokenError,
                wrongTypeTokenError = wrongTypeTokenError,
                unknownError = false,
                generalError = generalError
            )
        } else {
            ErrorUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    fun postAddress(
        name: String,
        addressName: String,
        phone: String,
        homePhone: String,
        postalCode: Int,
        address: String,
        detailAddress: String,
        request: String
    ){
        viewModelScope.launch{
            val requestDto = DefaultAddressDto(
                addressName = addressName,
                detailAddress = detailAddress,
                landlineNumber = homePhone,
                name = name,
                phoneNumber = phone,
                request = request,
                streetAddress = address,
                zipCode = postalCode.toString()
            )
            val result = memberRepository.postAddress(requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.code) {
                    "404" -> expiredTokenError.update{true}
                    "401" -> wrongTypeToken.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            _isPostAddressCompleted.update{ true }
        }
    }
}