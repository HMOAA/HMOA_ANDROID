package com.hyangmoa.core_datastore.Fcm

import ResultResponse
import com.hyangmoa.core_model.request.FCMTokenSaveRequestDto

interface FcmRemoteDataStore {

    suspend fun deleteFcmToken(): ResultResponse<Any>

    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>

}