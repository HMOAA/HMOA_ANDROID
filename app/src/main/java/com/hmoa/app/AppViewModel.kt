package com.hmoa.app

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.usecase.GetAuthAndRememberedTokenUseCase
import com.hmoa.feature_authentication.navigation.AuthenticationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val getAuthAndRememberedTokenUseCase: GetAuthAndRememberedTokenUseCase) :
    ViewModel() {
    private var authToken: String? = null
    private var rememberedToken: String? = null
    suspend fun routeInitialScreen(): String {
        val pair = getAuthAndRememberedTokenUseCase()
        authToken = pair.first
        rememberedToken = pair.second

        Log.d("TAG TEST", "token : ${authToken} / remember : ${rememberedToken}")

        if (authToken == null && rememberedToken == null) {
            return AuthenticationRoute.Login.name
        }
        return com.hmoa.feature_home.navigation.HomeRoute.Home.name
    }
}