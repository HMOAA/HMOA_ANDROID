package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.request.FCMTokenSaveRequestDto
import kotlinx.coroutines.flow.Flow

interface FcmRepository {
    suspend fun deleteRemoteFcmToken(): ResultResponse<Any>

    suspend fun postRemoteFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>
    suspend fun getLocalFcmToken(): Flow<String?>
    suspend fun saveLocalFcmToken(token: String)
    suspend fun deleteLocalFcmToken()
}