package com.hyangmoa.core_network.authentication

import ResultResponse
import com.hyangmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hyangmoa.core_model.response.GoogleAccessTokenResponseDto

interface GoogleServerAuthCodeService {
    suspend fun postGoogleServerAuthCodeServiceImpl(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto>
}