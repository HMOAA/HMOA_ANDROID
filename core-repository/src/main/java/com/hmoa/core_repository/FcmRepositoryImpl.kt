package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Fcm.FcmLocalDataStore
import com.hmoa.core_datastore.Fcm.FcmRemoteDataStore
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val fcmDataStore: FcmRemoteDataStore,
    private val fcmLocalDataStore: FcmLocalDataStore
) : com.hmoa.core_domain.repository.FcmRepository {
    override suspend fun saveNotificationEnabled(isEnabled: Boolean) {
        fcmLocalDataStore.saveNotificationEnabled(isEnabled)
    }

    override suspend fun getNotificationEnabled(): Flow<Boolean> {
        return fcmLocalDataStore.getNotificationEnabled()
    }

    override suspend fun deleteRemoteFcmToken(): ResultResponse<Any> {
        return fcmDataStore.deleteFcmToken()
    }

    override suspend fun postRemoteFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String> {
        return fcmDataStore.saveFcmToken(fcmTokenSaveRequest)
    }

    override suspend fun getFcmList(): ResultResponse<DataResponseDto<List<AlarmResponse>>> {
        return fcmDataStore.getFcmList()
    }

    override suspend fun checkAlarm(alarmId: Int): ResultResponse<DataResponseDto<Any>> {
        return fcmDataStore.checkAlarm(alarmId)
    }

    override suspend fun getLocalFcmToken(): Flow<String?> {
        return fcmLocalDataStore.getLocalFcmToken()
    }

    override suspend fun saveLocalFcmToken(token: String) {
        fcmLocalDataStore.saveLocalFcmToken(token)
    }

    override suspend fun deleteLocalFcmToken() {
        fcmLocalDataStore.deleteLocalFcmToken()
    }
}