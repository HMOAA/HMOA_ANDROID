package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.repository.SignupRepository
import com.hmoa.core_model.request.NickNameRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PickNicknameViewmodel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val signupRepository: SignupRepository,
) : ViewModel() {

    private val _isExistedNicknameState = MutableStateFlow(PickNicknameUiState.PickNickname(isExistedNickname = true))
    val isExistedNicknameState = _isExistedNicknameState.asStateFlow()

    suspend fun saveNickname(nickname: String) {
        signupRepository.saveNickname(nickname)
    }

    suspend fun onNicknameChanged(nickname: String?) {
        val result = memberRepository.postExistsNickname(NickNameRequestDto(nickname)).data!!
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