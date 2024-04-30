package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.request.SexRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val errState = MutableStateFlow<String?>(null)

    val isEnabled = _gender.map {
        it != defaultGender
    }

    val uiState: StateFlow<MyGenderUiState> = errState.map {
        if (it != null) {
            throw NullPointerException("Gender Info is NULL")
        }
        val result = getMyUserInfoUseCase()
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        result.data
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> MyGenderUiState.Loading
            is Result.Success -> {
                _gender.update { result.data!!.gender }
                defaultGender = result.data!!.gender
                MyGenderUiState.Success
            }

            is Result.Error -> MyGenderUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MyGenderUiState.Loading
    )

    //gender 정보 수정
    fun updateGender(newGender: String) {
        _gender.update { newGender }
    }

    //gender 정보 저장
    fun saveGender() {
        if (gender.value == null) {
            errState.update { "Gender Info is NULL" }
            return
        }
        val requestDto = SexRequestDto(gender.value == "남성")
        viewModelScope.launch {
            try {
                memberRepository.updateSex(requestDto)
            } catch (e: Exception) {
                errState.update { e.message }
            }
        }
    }
}

sealed interface MyGenderUiState {
    data object Loading : MyGenderUiState
    data object Success : MyGenderUiState
    data object Error : MyGenderUiState
}