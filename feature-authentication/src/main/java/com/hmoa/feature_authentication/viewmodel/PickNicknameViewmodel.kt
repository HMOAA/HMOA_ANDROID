package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.repository.SignupRepository
import com.hmoa.core_model.request.NickNameRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickNicknameViewmodel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val signupRepository: SignupRepository,
) : ViewModel() {

    val uiState: StateFlow<PickNicknameUiState> = flow{
        emit(PickNicknameUiState.PickNickname("", MutableSharedFlow(1)))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = PickNicknameUiState.Loading
    )
    fun saveNickname(nickname: String) {
        viewModelScope.launch{signupRepository.saveNickname(nickname)}
    }
    fun onNicknameChanged(nickname: String) {
        viewModelScope.launch{
            val result = memberRepository.postExistsNickname(NickNameRequestDto(nickname)).data!!
            if(uiState.value is PickNicknameUiState.PickNickname) {
                (uiState.value as PickNicknameUiState.PickNickname).updateIsExisted(nickname, !result)
            }
        }
    }
}

sealed interface PickNicknameUiState {
    data object Loading : PickNicknameUiState
    data class PickNickname(
        var initNickname: String,
        val isExistedNickname: MutableSharedFlow<Boolean?>
    ) : PickNicknameUiState {
        fun updateIsExisted(nickname: String, isExisted: Boolean?){
            this.isExistedNickname.tryEmit(isExisted)
            this.initNickname = nickname
        }
    }

    data object Empty : PickNicknameUiState
}