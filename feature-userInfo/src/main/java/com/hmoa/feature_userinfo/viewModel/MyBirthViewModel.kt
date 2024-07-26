package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.AgeRequestDto
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MyBirthViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    getMyUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {
    private var defaultBirth = 0

    private val _birth = MutableStateFlow<Int?>(null)
    val birth get() = _birth.asStateFlow()

    val isEnabled = _birth.map {birth.value != defaultBirth}
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
    val uiState: StateFlow<MyBirthUiState> = errorUiState.map{ errState ->
        if (errState is ErrorUiState.ErrorData && errState.generalError.first) throw Exception(errState.generalError.second)
        val result = getMyUserInfoUseCase()
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> MyBirthUiState.Loading
            is Result.Success -> {
                _birth.update { result.data.birth }
                defaultBirth = birth.value!!
                MyBirthUiState.Success
            }
            is Result.Error -> {
                generalErrorState.update{Pair(true, result.exception.message)}
                MyBirthUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MyBirthUiState.Loading
    )

    //update local 날짜
    fun updateBirth(newBirth: Int) = _birth.update { newBirth }
    //remote 출생 날짜 저장
    fun saveBirth() {
        if (birth.value == null) {
            generalErrorState.update{Pair(true, "Input Data is NULL")}
            return
        }
        val age = LocalDateTime.now().year - birth.value!! + 1
        val requestDto = AgeRequestDto(age)
        viewModelScope.launch {
            try {
                memberRepository.updateAge(requestDto)
            } catch (e: Exception) {
                generalErrorState.update { Pair(true, e.message) }
            }
        }
    }
}

sealed interface MyBirthUiState {
    data object Error : MyBirthUiState
    data object Success : MyBirthUiState
    data object Loading : MyBirthUiState
}