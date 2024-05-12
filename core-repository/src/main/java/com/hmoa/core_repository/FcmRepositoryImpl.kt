package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Fcm.FcmDataStore
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(private val fcmDataStore: FcmDataStore) :
    com.hmoa.core_domain.repository.FcmRepository {

    override suspend fun deleteFcmToken(): ResultResponse<Any> {
        return fcmDataStore.deleteFcmToken()
    }

    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String> {
        return fcmDataStore.saveFcmToken(fcmTokenSaveRequest)
    }
}