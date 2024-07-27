package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.request.SexRequestDto
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
class MyGenderViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    getMyUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {
    private var defaultGender = ""
    private val _gender = MutableStateFlow<String?>(null)
    val gender get() = _gender.asStateFlow()

    val isEnabled = _gender.map {it != defaultGender}
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
        if (errState is ErrorUiState.ErrorData && errState.generalError.first) throw Exception(errState.generalError.second)
        val result = getMyUserInfoUseCase()
        if (result.errorMessage != null) {throw Exception(result.errorMessage!!.message)}
        result.data
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> MyGenderUiState.Loading
            is Result.Success -> {
                _gender.update { result.data!!.gender }
                defaultGender = result.data!!.gender
                MyGenderUiState.Success
            }
            is Result.Error -> {
                if (!(errorUiState as ErrorUiState.ErrorData).generalError.first) {
                    generalErrorState.update{Pair(true, result.exception.message)}
                }
                MyGenderUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MyGenderUiState.Loading
    )

    //gender 정보 수정
    fun updateGender(newGender: String) = _gender.update { newGender }
    //gender 정보 저장
    fun saveGender() {
        if (gender.value == null) {
            generalErrorState.update { Pair(true, "Gender Info is NULL") }
            return
        }
        val requestDto = SexRequestDto(gender.value == "남성")
        viewModelScope.launch {
            try {
                memberRepository.updateSex(requestDto)
            } catch (e: Exception) {
                generalErrorState.update { Pair(true, e.message) }
            }
        }
    }
}

sealed interface MyGenderUiState {
    data object Loading : MyGenderUiState
    data object Success : MyGenderUiState
    data object Error : MyGenderUiState
}