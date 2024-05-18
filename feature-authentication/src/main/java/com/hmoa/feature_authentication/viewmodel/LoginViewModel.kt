package com.hmoa.feature_authentication.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.usecase.SaveAuthAndRememberedTokenUseCase
import com.hmoa.core_domain.usecase.SaveKakaoTokenUseCase
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.feature_authentication.BuildConfig
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val application: Application,
    private val saveKakaoToken: SaveKakaoTokenUseCase,
    private val loginRepository: LoginRepository,
    private val saveAuthAndRememberedToken: SaveAuthAndRememberedTokenUseCase
) : ViewModel() {
    private val context = application.applicationContext
    private val _isAbleToGoHome = MutableStateFlow(false)
    var isAbleToGoHome = _isAbleToGoHome.asStateFlow()
    private val _isOauthTokenReceived = MutableStateFlow(false)
    var isOauthTokenReceived = _isOauthTokenReceived.asStateFlow()

    suspend fun onKakaoLoginSuccess(token: String) {
        saveKakoAccessToken(token)
        postKakaoAccessToken(token)
    }

    suspend fun onGoogleLoginSuccess(token: String) {
        loginRepository.saveGoogleAccessToken(token)
        postGoogleAccessToken(token)
    }

    suspend fun saveKakoAccessToken(token: String) {
        saveKakaoToken(token)
    }

    suspend fun postKakaoAccessToken(token: String) {
        viewModelScope.launch {
            flow {
                val result = loginRepository.postOAuth(OauthLoginRequestDto(token), provider = Provider.KAKAO)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                } else {
                    emit(result.data)
                }
            }.asResult()
                .collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val authToken = it.data?.authToken
                            val rememberedToken = it.data?.rememberedToken
                            Log.d("LoginViewModel", "authToken:${authToken},\n rememberedToken:${rememberedToken}")
                            if (it.data != null && authToken != null && rememberedToken != null) {
                                saveAuthAndRememberedToken(authToken, rememberedToken)
                                checkIsExistedMember(it.data!!)
                            }
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }
                }
        }
    }

    suspend fun postGoogleAccessToken(token: String) {
        viewModelScope.launch {
            flow {
                val result = loginRepository.postOAuth(OauthLoginRequestDto(token), provider = Provider.GOOGLE)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                } else {
                    emit(result.data)
                }
            }.asResult()
                .collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val authToken = it.data?.authToken
                            val rememberedToken = it.data?.rememberedToken
                            Log.d("LoginViewModel", "authToken:${authToken},\n rememberedToken:${rememberedToken}")
                            if (it.data != null && authToken != null && rememberedToken != null) {
                                saveAuthAndRememberedToken(authToken, rememberedToken)
                                checkIsExistedMember(it.data!!)
                            }
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }
                }
        }
    }

    fun checkIsExistedMember(data: MemberLoginResponseDto) {
        if (data.existedMember) {
            _isAbleToGoHome.update { true }
        } else {
            _isOauthTokenReceived.update { true }

        }
    }

    fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                viewModelScope.launch(Dispatchers.IO) { onKakaoLoginSuccess(token.accessToken) }
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
                    viewModelScope.launch(Dispatchers.IO) { onKakaoLoginSuccess(token.accessToken) }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun getGoogleAccessToken(serverAuthCode: String?) {
        val clientID = BuildConfig.GOOGLE_CLOUD_OAUTH_CLIENT_ID
        val clientSecret = BuildConfig.GOOGLE_CLOUD_CLIENT_SECRET
        val redirectUri = BuildConfig.REDIRECT_URI
        val grantType = BuildConfig.GRANT_TYPE

        if (serverAuthCode != null) {
            viewModelScope.launch(Dispatchers.IO) {
                flow {
                    emit(
                        loginRepository.postGoogleServerAuthCode(
                            GoogleAccessTokenRequestDto(
                                code = serverAuthCode,
                                client_id = clientID,
                                client_secret = clientSecret,
                                redirect_uri = redirectUri,
                                grant_type = grantType
                            )
                        )
                    )
                }.asResult().collectLatest { result ->
                    when (result) {
                        is Result.Error -> {}
                        Result.Loading -> {}
                        is Result.Success -> {
                            val accessToken = result.data.data?.access_token
                            if (accessToken != null) {
                                onGoogleLoginSuccess(accessToken)
                            }
                        }
                    }
                }
            }
        }
    }
}
