package com.hmoa.core_datastore.Fcm

import com.hmoa.core_database.AppInfoManager
import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FcmLocalDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val appInfoManager: AppInfoManager
) : FcmLocalDataStore {
    override suspend fun getLocalFcmToken(): Flow<String?> {
        return tokenManager.getFcmToken()
    }

    override suspend fun saveLocalFcmToken(token: String) {
        tokenManager.saveFcmToken(token)
    }

    override suspend fun saveNotificationEnabled(isEnabled: Boolean) {
        appInfoManager.saveNotificationEnabled(isEnabled)
    }

    override suspend fun getNotificationEnabled(): Flow<Boolean> {
        return appInfoManager.getNotificationEnabled()
    }

    override suspend fun deleteLocalFcmToken() {
        tokenManager.deleteFcmToken()
    }
}