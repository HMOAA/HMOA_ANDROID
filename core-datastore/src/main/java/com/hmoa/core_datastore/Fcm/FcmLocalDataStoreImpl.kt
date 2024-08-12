package com.hmoa.core_datastore.Fcm

import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FcmLocalDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager
) : FcmLocalDataStore {
    override suspend fun getLocalFcmToken(): Flow<String?> {
        return tokenManager.getFcmToken()
    }

    override suspend fun saveLocalFcmToken(token: String) {
        tokenManager.saveFcmToken(token)
    }

    override suspend fun deleteLocalFcmToken() {
        tokenManager.deleteFcmToken()
    }
}