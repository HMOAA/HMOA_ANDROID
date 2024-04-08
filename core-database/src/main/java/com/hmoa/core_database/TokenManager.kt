package com.hmoa.core_database

import kotlinx.coroutines.flow.Flow

interface TokenManager {

    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRememberedToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>
    fun saveAuthToken(token: String)
    fun saveRememberedToken(token: String)
    fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}