package com.hmoa.core_database

import kotlinx.coroutines.flow.Flow

interface TokenManager {

    fun getAuthToken(): Flow<String?>
    fun getRememberedToken(): Flow<String?>
    fun getKakaoAccessToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}