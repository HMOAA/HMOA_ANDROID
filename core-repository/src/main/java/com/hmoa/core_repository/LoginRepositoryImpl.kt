package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Login.LoginLocalDataStore
import com.hmoa.core_datastore.Login.LoginRemoteDataStore
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataStore: LoginRemoteDataStore,
    private val loginLocalDataStore: LoginLocalDataStore
) : com.hmoa.core_domain.repository.LoginRepository {
    override suspend fun getAuthToken(): String? {
        return loginLocalDataStore.getAuthToken()
    }

    override suspend fun getRememberedToken(): String? {
        return loginLocalDataStore.getRememberedToken()
    }

    override suspend fun getKakaoAccessToken(): String? {
        return loginLocalDataStore.getKakaoAccessToken()
    }

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        return loginRemoteDataStore.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto> {
        return loginRemoteDataStore.postRemembered(dto)
    }

    override suspend fun saveAuthToken(token: String) {
        loginLocalDataStore.saveAuthToken(token)
    }

    override suspend fun saveRememberedToken(token: String) {
        loginLocalDataStore.saveRememberedToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        loginLocalDataStore.saveKakaoAccessToken(token)
    }

    override suspend fun deleteAuthToken() {
        loginLocalDataStore.deleteAuthToken()
    }

    override suspend fun deleteRememberedToken() {
        loginLocalDataStore.deleteRememberedToken()
    }

    override suspend fun deleteKakaoAccessToken() {
        loginLocalDataStore.deleteKakaoAccessToken()
    }
}