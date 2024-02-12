package com.hmoa.core_datastore.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.Fcm.FcmService

private class FcmDataStoreImpl constructor(
    private val fcmDataService : FcmService
) : FcmDataStore {

    override suspend fun deleteFcmToken(): DataResponseDto<Any> {
        return fcmDataService.deleteFcmToken()
    }

    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any> {
        return fcmDataService.saveFcmToken(fcmTokenSaveRequest)
    }
}