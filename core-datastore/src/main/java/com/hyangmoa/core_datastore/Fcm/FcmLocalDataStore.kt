package com.hyangmoa.core_datastore.Fcm

import kotlinx.coroutines.flow.Flow

interface FcmLocalDataStore {
    suspend fun getLocalFcmToken(): Flow<String?>
    suspend fun saveLocalFcmToken(token: String)
    suspend fun deleteLocalFcmToken()
}