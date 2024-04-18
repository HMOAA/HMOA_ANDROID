package com.hmoa.core_datastore.Login

import kotlinx.coroutines.flow.Flow

interface LoginLocalDataStore {
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRememberedToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>

    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
}