package com.hmoa.core_repository

import com.hmoa.core_datastore.Login.LoginDataStore
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataStore: LoginDataStore
) : com.hmoa.core_domain.repository.LoginRepository {
    override suspend fun getAuthToken(): String? {
        return loginDataStore.getAuthToken()
    }

    override suspend fun getRememberedToken(): String? {
        return loginDataStore.getRememberedToken()
    }

    override suspend fun getKakaoAccessToken(): String? {
        return loginDataStore.getKakaoAccessToken()
    }

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        return loginDataStore.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        return loginDataStore.postRemembered(dto)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        loginDataStore.saveKakaoAccessToken(token)
    }

    override suspend fun deleteKakaoAccessToken() {
        loginDataStore.deleteKakaoAccessToken()
    }
}