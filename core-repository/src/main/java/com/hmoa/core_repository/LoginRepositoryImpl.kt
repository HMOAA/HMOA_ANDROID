package com.hmoa.core_repository

import ResultResponse
import android.util.Log
import com.hmoa.core_datastore.Login.LoginLocalDataStore
import com.hmoa.core_datastore.Login.LoginRemoteDataStore
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataStore: LoginRemoteDataStore,
    private val loginLocalDataStore: LoginLocalDataStore
) : com.hmoa.core_domain.repository.LoginRepository {
    override suspend fun getAuthToken(): Flow<String?> {
        val token = loginLocalDataStore.getAuthToken()
        Log.d("LoginRepositoryImpl", "getAuthToken:${token}")
        return token
    }

    override suspend fun getRememberedToken(): Flow<String?> {
        val token = loginLocalDataStore.getRememberedToken()
        Log.d("LoginRepositoryImpl", "getRememberedToken:${token}")
        return token
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        return loginLocalDataStore.getKakaoAccessToken()
    }

    override suspend fun getGoogleAccessToken(): Flow<String?> {
        return loginLocalDataStore.getGoogleAccessToken()
    }

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): ResultResponse<MemberLoginResponseDto> {
        return loginRemoteDataStore.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto> {
        return loginRemoteDataStore.postRemembered(dto)
    }

    override suspend fun saveAuthToken(token: String) {
        Log.d("LoginRepositoryImpl", "saveAuthToken:${token}")
        loginLocalDataStore.saveAuthToken(token)
    }

    override suspend fun saveRememberedToken(token: String) {
        Log.d("LoginRepositoryImpl", "saveRememberedToken:${token}")
        loginLocalDataStore.saveRememberedToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        loginLocalDataStore.saveKakaoAccessToken(token)
    }

    override suspend fun saveGoogleAccessToken(token: String) {
        loginLocalDataStore.saveGoogleAccessToken(token)
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

    override suspend fun deleteGoogleAccessToken() {
        loginLocalDataStore.deleteGoogleAccessToken()
    }
}