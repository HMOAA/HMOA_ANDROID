package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto
import kotlinx.coroutines.flow.Flow

interface FcmRepository {
    suspend fun deleteRemoteFcmToken(): ResultResponse<Any>

    suspend fun postRemoteFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>
    suspend fun getFcmList() : ResultResponse<List<AlarmResponse>>
    suspend fun checkAlarm(alarmId : Int) : ResultResponse<DataResponseDto<Any>>
    suspend fun getLocalFcmToken(): Flow<String?>
    suspend fun saveLocalFcmToken(token: String)
    suspend fun deleteLocalFcmToken()
}