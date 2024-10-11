package com.hmoa.core_network.service

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FcmService {
    @DELETE("/fcm/delete")
    suspend fun deleteFcmToken(): ApiResponse<DataResponseDto<Any>>

    @GET("/fcm/list")
    suspend fun getAlarmList(): ApiResponse<DataResponseDto<List<AlarmResponse>>>

    @PUT("/fcm/read/{alarmId}")
    suspend fun checkAlarm(
        @Path("alarmId") alarmId : Int
    ) : ApiResponse<DataResponseDto<Any>>

    @POST("/fcm/save")
    suspend fun saveFcmToken(@Body fcmTokenSaveRequest: FCMTokenSaveRequestDto): ApiResponse<DataResponseDto<String>>
}