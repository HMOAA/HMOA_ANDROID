package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ui.BaseViewModel
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.repository.SignupRepository
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.feature_authentication.contract.PickNicknameEffect
import com.hmoa.feature_authentication.contract.PickNicknameEvent
import com.hmoa.feature_authentication.contract.PickNicknameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickNicknameViewmodel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val signupRepository: SignupRepository,
) : BaseViewModel<PickNicknameEvent, PickNicknameState, PickNicknameEffect>() {
    override fun createInitialState(): PickNicknameState {
        return PickNicknameState()
    }

    override fun handleEvent(event: PickNicknameEvent) {
        when (event) {
            PickNicknameEvent.NavigateToPickPersonalInfo -> {
                setEffect(PickNicknameEffect.NavigateToPickPersonalInfo)
            }

            is PickNicknameEvent.ClickCheckDuplicate -> handleNicknameDuplication(event.nickname)
        }
    }

    fun saveNickname(nickname: String) {
        viewModelScope.launch { signupRepository.saveNickname(nickname) }
    }

    fun handleNicknameDuplication(nickname: String) {
        viewModelScope.launch {
            val isExistedNickname = memberRepository.postExistsNickname(NickNameRequestDto(nickname)).data!!
            setState {
                copy(
                    isAvailableNickname = !isExistedNickname,
                    isNextButtonEnabled = !isExistedNickname
                )
            }

            if (!isExistedNickname) {
                saveNickname(nickname)
            }
        }
    }
}
