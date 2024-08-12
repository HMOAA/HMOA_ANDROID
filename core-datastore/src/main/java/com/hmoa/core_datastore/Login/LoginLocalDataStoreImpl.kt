package com.hmoa.core_datastore.Login

import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginLocalDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager
) : LoginLocalDataStore {
    override suspend fun getAuthToken(): Flow<String?> {
        val token = tokenManager.getAuthToken()
        return token
    }

    override suspend fun getRememberedToken(): Flow<String?> {
        val token = tokenManager.getRememberedToken()
        return token
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        val token = tokenManager.getKakaoAccessToken()
        return token
    }

    override suspend fun getGoogleAccessToken(): Flow<String?> {
        val token = tokenManager.getGoogleAccessToken()
        return token
    }

    override suspend fun saveAuthToken(token: String) {
        tokenManager.saveAuthToken(token)
    }

    override suspend fun saveRememberedToken(token: String) {
        tokenManager.saveRememberedToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        tokenManager.saveKakaoAccessToken(token)
    }

    override suspend fun saveGoogleAccessToken(token: String) {
        tokenManager.saveGoogleAccessToken(token)
    }

    override suspend fun deleteAuthToken() {
        tokenManager.deleteAuthToken()
    }

    override suspend fun deleteRememberedToken() {
        tokenManager.deleteRememberedToken()
    }

    override suspend fun deleteKakaoAccessToken() {
        tokenManager.deleteKakaoAccessToken()
    }

    override suspend fun deleteGoogleAccessToken() {
        tokenManager.deleteGoogleAccessToken()
    }
}