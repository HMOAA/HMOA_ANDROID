package com.hmoa.core_datastore.Login

interface LoginLocalDataStore {
    suspend fun getAuthToken(): String?
    suspend fun getRememberedToken(): String?
    suspend fun getKakaoAccessToken(): String?

    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}