package com.hmoa.core_database

import kotlinx.coroutines.flow.Flow

interface TokenManager {

    fun getAuthToken(): Flow<String?>
    fun getRememberedToken(): Flow<String?>
    fun getKakaoAccessToken(): Flow<String?>
    fun saveAuthToken(token: String)
    fun saveRememberedToken(token: String)
    fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}