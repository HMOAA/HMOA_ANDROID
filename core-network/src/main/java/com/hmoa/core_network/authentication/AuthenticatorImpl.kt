package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManagerImpl
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.TokenResponseDto
import corenetwork.Login.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

private class AuthenticatorImpl constructor(
    private val tokenManager: TokenManagerImpl,
    private val loginService: LoginService
) : Authenticator {
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

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        return loginService.postRemembered(dto).apply {
            CoroutineScope(Dispatchers.IO).async {
                tokenManager.deleteAccessToken()
            }.await()
            tokenManager.saveAccessToken(this.authToken)
            tokenManager.saveRememberedToken(this.rememberedToken)
        }
    }
}