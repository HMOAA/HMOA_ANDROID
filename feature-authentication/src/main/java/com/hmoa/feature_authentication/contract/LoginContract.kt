package com.hmoa.feature_authentication.contract

import com.hmoa.core_common.ui.contract.UiEffect
import com.hmoa.core_common.ui.contract.UiEvent
import com.hmoa.core_common.ui.contract.UiState
import com.hmoa.core_model.Provider

sealed interface LoginEvent : UiEvent {
    data object ClickKakaoLogin : LoginEvent
    data object ClickGoogleLogin : LoginEvent
    data object ClickHome : LoginEvent
    data class RequestGoogleToken(val serverAuthCode: String?) : LoginEvent
}

data object LoginState : UiState

sealed interface LoginEffect : UiEffect {
    data object NavigateToHome : LoginEffect
    data class NavigateToSignup(val loginProvider: Provider) : LoginEffect
    data object StartGoogleLogin : LoginEffect
}

