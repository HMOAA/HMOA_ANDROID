package com.hmoa.core_repository.Fcm

import com.hmoa.core_datastore.Fcm.FcmDataStore
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

class FcmRepositoryImpl(private val fcmDataStore : FcmDataStore) : FcmRepository {

    override fun deleteFcmToken(): DataResponseDto<Any> {
        return fcmDataStore.deleteFcmToken()
    }

    override fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any> {
        return saveFcmToken(fcmTokenSaveRequest)
    }
}