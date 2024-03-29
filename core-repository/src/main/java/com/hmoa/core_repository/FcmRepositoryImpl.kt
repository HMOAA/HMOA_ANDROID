package com.hmoa.core_repository

import com.hmoa.core_datastore.Fcm.FcmDataStore
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(private val fcmDataStore: FcmDataStore) :
    com.hmoa.core_domain.repository.FcmRepository {

    override suspend fun deleteFcmToken(): DataResponseDto<Any> {
        return fcmDataStore.deleteFcmToken()
    }

    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any> {
        return saveFcmToken(fcmTokenSaveRequest)
    }
}