package com.hmoa.app

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hmoa.app.navigation.MAIN_ROUTE
import com.hmoa.core_domain.usecase.GetAuthAndRememberedTokenUseCase
import com.hmoa.feature_authentication.navigation.LOGIN_ROUTE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val getAuthAndRememberedTokenUseCase: GetAuthAndRememberedTokenUseCase) :
    ViewModel() {
    suspend fun routeInitialScreen(): String {
        val pair = getAuthAndRememberedTokenUseCase()
        val authToken = pair.first
        val rememberedToken = pair.second
        Log.d("TAG TEST", "token : ${authToken} / remember : ${rememberedToken}")

        if (authToken == null && rememberedToken == null) {
            return LOGIN_ROUTE
        }
        return MAIN_ROUTE
    }
}