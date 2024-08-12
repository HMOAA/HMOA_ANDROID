package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.request.FCMTokenSaveRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface FcmService {
    @DELETE("/fcm/delete")
    suspend fun deleteFcmToken(): ApiResponse<DataResponseDto<Any>>

    @POST("/fcm/save")
    suspend fun saveFcmToken(@Body fcmTokenSaveRequest: FCMTokenSaveRequestDto): ApiResponse<DataResponseDto<String>>
}