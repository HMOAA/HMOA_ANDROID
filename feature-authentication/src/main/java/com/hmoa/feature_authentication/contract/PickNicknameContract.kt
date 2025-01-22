package com.hmoa.feature_authentication.contract

import com.hmoa.core_common.ui.contract.UiEffect
import com.hmoa.core_common.ui.contract.UiEvent
import com.hmoa.core_common.ui.contract.UiState

sealed interface PickNicknameEvent : UiEvent {
    data class ClickCheckDuplicate(val nickname: String) : PickNicknameEvent
    data object NavigateToPickPersonalInfo : PickNicknameEvent
}

data class PickNicknameState(
    val isAvailableNickname: Boolean? = false,
    val isNextButtonEnabled: Boolean = false
) : UiState

sealed interface PickNicknameEffect : UiEffect {
    data object NavigateToPickPersonalInfo : PickNicknameEffect
}
