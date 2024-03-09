package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.usecase.CheckNicknameDuplicationUseCase
import com.hmoa.core_domain.usecase.SaveSignupInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PickNicknameViewmodel @Inject constructor(
    private val checkNicknameDuplicateUseCase: CheckNicknameDuplicationUseCase,
    private val saveSignupInfoUseCase: SaveSignupInfoUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _isExistedNicknameState = MutableStateFlow(PickNicknameUiState.PickNickname(isExistedNickname = true))
    val isExistedNicknameState = _isExistedNicknameState.asStateFlow()

    fun saveNickname(nickname: String) {
        saveSignupInfoUseCase.saveNickname(nickname)
    }

    suspend fun onNicknameChanged(nickname: String?) {
        val result = checkNicknameDuplicateUseCase.invoke(nickname)
        _isExistedNicknameState.update { PickNicknameUiState.PickNickname(result) }
    }
}

sealed interface PickNicknameUiState {
    data object Loading : PickNicknameUiState
    data class PickNickname(
        val isExistedNickname: Boolean
    ) : PickNicknameUiState

    data object Empty : PickNicknameUiState
}