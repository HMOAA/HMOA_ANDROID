package com.hmoa.core_datastore.Login

import android.util.Log
import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginLocalDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager
) : LoginLocalDataStore {
    override suspend fun getAuthToken(): Flow<String?> {
        val token = tokenManager.getAuthToken()
        Log.d("LoginLocalDataStoreImpl", "getAuthToken:${token}")
        return token
    }

    override suspend fun getRememberedToken(): Flow<String?> {
        val token = tokenManager.getRememberedToken()
        Log.d("LoginLocalDataStoreImpl", "getRememberedToken:${token}")
        return token
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        return tokenManager.getKakaoAccessToken()
    }

    override suspend fun getGoogleAccessToken(): Flow<String?> {
        return tokenManager.getGoogleAccessToken()
    }

    override suspend fun getFcmToken(): Flow<String?> {
        return tokenManager.getFcmToken()
    }

    override suspend fun saveAuthToken(token: String) {
        Log.d("LoginLocalDataStoreImpl", "saveAuthToken:${token}")
        tokenManager.saveAuthToken(token)
    }

    override suspend fun saveRememberedToken(token: String) {
        Log.d("LoginLocalDataStoreImpl", "saveRememberedToken:${token}")
        tokenManager.saveRememberedToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        tokenManager.saveKakaoAccessToken(token)
    }

    override suspend fun saveGoogleAccessToken(token: String) {
        tokenManager.saveGoogleAccessToken(token)
    }

    override suspend fun saveFcmToken(token: String) {
        tokenManager.saveFcmToken(token)
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

    override suspend fun deleteFcmToken() {
        tokenManager.deleteFcmToken()
    }
}