package com.hmoa.feature_authentication.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.PostKakaoTokenUseCase
import com.hmoa.core_domain.usecase.SaveAuthAndRememberedTokenUseCase
import com.hmoa.core_domain.usecase.SaveKakaoTokenUseCase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val application: Application,
    private val saveKakaoToken: SaveKakaoTokenUseCase,
    private val postSocialToken: PostKakaoTokenUseCase,
    private val saveAuthAndRememberedToken: SaveAuthAndRememberedTokenUseCase
) : ViewModel() {
    private val context = application.applicationContext
    private val _isPostComplete = MutableStateFlow(false)
    var isPostComplete = _isPostComplete.asStateFlow()
    val scope = CoroutineScope(Dispatchers.IO)

    suspend fun onKakaoLoginSuccess(token: String) {
        Log.i(TAG, "카카오계정으로 로그인 성공 ${token}")
        saveKakoAccessToken(token)
        postKakaoAccessToken(token)
    }

    fun saveKakoAccessToken(token: String) {
        suspend { saveKakaoToken(token) }
    }

    suspend fun postKakaoAccessToken(token: String) {
        postSocialToken(token).asResult()
            .collectLatest {
                when (it) {
                    is Result.Success -> {
                        val authToken = it.data.authToken
                        val rememberedToken = it.data.rememberedToken
                        saveAuthAndRememberedToken(authToken, rememberedToken)
                        _isPostComplete.update { true }
                    }

                    is Result.Loading -> {}//TODO("로딩화면")
                    is Result.Error -> {
                        it
                        it.exception
                    }//TODO()
                }
            }
    }

    fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                scope.launch { onKakaoLoginSuccess(token.accessToken) }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    scope.launch { onKakaoLoginSuccess(token.accessToken) }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}