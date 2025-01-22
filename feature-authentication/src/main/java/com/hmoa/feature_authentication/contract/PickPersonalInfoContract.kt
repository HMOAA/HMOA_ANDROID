package com.hmoa.feature_authentication.contract

import com.hmoa.core_common.ui.contract.UiEffect
import com.hmoa.core_common.ui.contract.UiEvent
import com.hmoa.core_common.ui.contract.UiState

sealed interface PickPersonalInfoEvent : UiEvent {
    data class FinishOnBoarding(val loginProvider: String) : PickPersonalInfoEvent
    data class SaveBirthYear(val birthYear: Int?) : PickPersonalInfoEvent
    data class SaveSex(val sex: String) : PickPersonalInfoEvent
}

data class PickPersonalInfoState(
    val SEX: List<String> = listOf<String>("여성", "남성"),
    val isAvailableToSignup: Boolean = false,
    val birthYear: Int?,
    val sex: String? = SEX[0],
    val PrepareSocialToken: String?
) : UiState

sealed interface PickPersonalInfoEffect : UiEffect {
    data object PrepareToken : PickPersonalInfoEffect
    data object NavigateToHome : PickPersonalInfoEffect
}
