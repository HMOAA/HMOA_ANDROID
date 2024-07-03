package com.hmoa.feature_fcm

import ResultResponse
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto
import kotlinx.coroutines.flow.Flow

class TestFcmRepository : FcmRepository {

    private val testFcmData : MutableList<AlarmResponse> = mutableListOf()
    private var type = ""
    override suspend fun deleteRemoteFcmToken(): ResultResponse<Any> {
        TODO("Not yet implemented")
    }
    override suspend fun postRemoteFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String> {
        TODO("Not yet implemented")
    }
    override suspend fun getFcmList(): ResultResponse<List<AlarmResponse>> {
        return when(type) {
            "loading" -> ResultResponse()
            "error" -> ResultResponse(errorMessage = ErrorMessage(code = "400", message = "Login Error"))
            "success" -> ResultResponse(testFcmData)
            else -> ResultResponse(errorMessage = ErrorMessage(code = "0", message = "type error"))
        }
    }
    override suspend fun checkAlarm(alarmId: Int): ResultResponse<DataResponseDto<Any>> {
        testFcmData.forEach{
            if (it.id == alarmId) it.read = !it.read
        }
        return ResultResponse(DataResponseDto("Success"))
    }
    override suspend fun getLocalFcmToken(): Flow<String?> {
        TODO("Not yet implemented")
    }
    override suspend fun saveLocalFcmToken(token: String) {
        TODO("Not yet implemented")
    }
    override suspend fun deleteLocalFcmToken() {
        TODO("Not yet implemented")
    }
    override suspend fun saveNotificationEnabled(isEnabled: Boolean) {
        TODO("Not yet implemented")
    }
    override suspend fun getNotificationEnabled(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
    fun setTestData(alarm : AlarmResponse){
        testFcmData.add(alarm)
    }
    fun setType(type : String) {
        this.type = type
    }
}