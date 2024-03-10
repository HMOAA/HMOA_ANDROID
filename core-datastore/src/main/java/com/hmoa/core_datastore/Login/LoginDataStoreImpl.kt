package com.hmoa.core_datastore.Login

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginDataStoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val tokenManager: TokenManager
) : LoginDataStore {
    override suspend fun getAuthToken(): String? {
        val token = CoroutineScope(Dispatchers.IO).async {
            tokenManager.getAuthToken().first()
        }.await()
        return token
    }

    override suspend fun getRememberedToken(): String? {
        val token = CoroutineScope(Dispatchers.IO).async {
            tokenManager.getRememberedToken().first()
        }.await()
        return token
    }

    override suspend fun getKakaoAccessToken(): String? {
        val token = CoroutineScope(Dispatchers.IO).async {
            tokenManager.getKakaoAccessToken().first()
        }.await()
        return token
    }

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        return loginService.postOAuth(accessToken, provider).apply {
            tokenManager.saveAuthToken(this.authToken)
            tokenManager.saveRememberedToken(this.rememberedToken)
        }
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        var result = TokenResponseDto("", "")
        loginService.postRemembered(dto).suspendOnSuccess {
            tokenManager.saveAuthToken(this.data.authToken)
            tokenManager.saveRememberedToken(this.data.rememberedToken)
            result.authToken = this.data.authToken
            result.rememberedToken = this.data.rememberedToken
        }
        return result
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        tokenManager.saveKakaoAccessToken(token)
    }

    override suspend fun deleteKakaoAccessToken() {
        tokenManager.deleteKakaoAccessToken()
    }
}