package com.hmoa.core_datastore.Login

import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginLocalDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager
) : LoginLocalDataStore {
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

    override suspend fun saveAuthToken(token: String) {
        tokenManager.saveAuthToken(token)
    }

    override suspend fun saveRememberedToken(token: String) {
        tokenManager.saveRememberedToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        tokenManager.saveKakaoAccessToken(token)
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
}