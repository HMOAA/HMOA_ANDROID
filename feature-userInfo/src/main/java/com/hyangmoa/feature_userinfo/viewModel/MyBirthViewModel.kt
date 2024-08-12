package com.hyangmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.MemberRepository
import com.hyangmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hyangmoa.core_model.request.AgeRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    val isEnabled = _birth.map {
        birth.value != defaultBirth
    }
    private val errState = MutableStateFlow<String?>(null)

    val uiState: StateFlow<MyBirthUiState> = errState.map { err ->
        if (err != null) {
            throw Exception(errState.value)
        }
        val result = getMyUserInfoUseCase()
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        result.data!!
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> MyBirthUiState.Loading
            is Result.Success -> {
                _birth.update { result.data.birth }
                defaultBirth = birth.value!!
                MyBirthUiState.Success
            }

            is Result.Error -> MyBirthUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MyBirthUiState.Loading
    )

    //update local 날짜
    fun updateBirth(newBirth: Int) {
        _birth.update { newBirth }
    }

    //remote 출생 날짜 저장
    fun saveBirth() {
        if (birth.value == null) {
            errState.update { "birth data is NULL" }
            return
        }
        val age = LocalDateTime.now().year - birth.value!! + 1
        val requestDto = AgeRequestDto(age)
        viewModelScope.launch {
            try {
                memberRepository.updateAge(requestDto)
            } catch (e: Exception) {
                errState.update { e.message }
            }
        }
    }
}

sealed interface MyBirthUiState {
    data object Error : MyBirthUiState
    data object Success : MyBirthUiState
    data object Loading : MyBirthUiState
}