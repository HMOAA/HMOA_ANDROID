package com.hmoa.core_datastore.Fcm

import kotlinx.coroutines.flow.Flow

interface FcmLocalDataStore {
    suspend fun getLocalFcmToken(): Flow<String?>
    suspend fun saveLocalFcmToken(token: String)
    suspend fun deleteLocalFcmToken()
    suspend fun saveNotificationEnabled(isEnabled : Boolean)
    suspend fun getNotificationEnabled() : Flow<Boolean>
}