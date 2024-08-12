package com.hmoa.core_datastore.Fcm

import ResultResponse
import com.hmoa.core_model.request.FCMTokenSaveRequestDto

interface FcmRemoteDataStore {

    suspend fun deleteFcmToken(): ResultResponse<Any>

    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>

}