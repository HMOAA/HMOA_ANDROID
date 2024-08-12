package com.hmoa.core_network.authentication

import ResultResponse
import com.hmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hmoa.core_model.response.GoogleAccessTokenResponseDto

interface GoogleServerAuthCodeService {
    suspend fun postGoogleServerAuthCodeServiceImpl(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto>
}