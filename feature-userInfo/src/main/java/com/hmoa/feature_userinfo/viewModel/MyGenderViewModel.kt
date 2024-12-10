package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.*
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.request.SexRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGenderViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    getMyUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {
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
    val uiState: StateFlow<MyGenderUiState> = errorUiState.map {errState ->
        if (errState is ErrorUiState.ErrorData && errState.isValidate()) throw Exception("")
        val result = getMyUserInfoUseCase()
        if (result.errorMessage != null) {throw Exception(result.errorMessage!!.message)}
        result.data
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> MyGenderUiState.Loading
            is Result.Success -> MyGenderUiState.Success(result.data!!.gender)
            is Result.Error -> {
                if (result.exception.message != ""){
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = { expiredTokenErrorState.update { true } },
                        onWrongTypeTokenError = { wrongTypeTokenErrorState.update{true}},
                        onUnknownError = {unLoginedErrorState.update{true}},
                        onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                    )
                }
                MyGenderUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MyGenderUiState.Loading
    )

    //gender 정보 저장
    fun saveGender(gender: String, onSuccess: () -> Unit) {
        val requestDto = SexRequestDto(gender == "남성")
        viewModelScope.launch {
            val result = memberRepository.updateSex(requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            onSuccess()
        }
    }
}

sealed interface MyGenderUiState {
    data object Loading : MyGenderUiState
    data class Success(
        val defaultGender: String
    ) : MyGenderUiState

    data object Error : MyGenderUiState
}