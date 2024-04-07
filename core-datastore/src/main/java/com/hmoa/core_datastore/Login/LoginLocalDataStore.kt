package com.hmoa.core_datastore.Login

interface LoginLocalDataStore {
    suspend fun getAuthToken(): String?
    suspend fun getRememberedToken(): String?
    suspend fun getKakaoAccessToken(): String?

    fun saveAuthToken(token: String)
    fun saveRememberedToken(token: String)
    fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}