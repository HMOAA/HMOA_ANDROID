package com.hmoa.core_database

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getAuthTokenForHeader(): String?
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRememberedToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>
    suspend fun getGoogleAccessToken(): Flow<String?>
    suspend fun getFcmToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun saveGoogleAccessToken(token: String)
    suspend fun saveFcmToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
    suspend fun deleteGoogleAccessToken()
    suspend fun deleteFcmToken()
}