package com.hyangmoa.core_network.authentication

import com.hyangmoa.core_model.request.RememberedLoginRequestDto
import com.hyangmoa.core_model.response.TokenResponseDto
import com.skydoves.sandwich.ApiResponse

interface RefreshTokenManager {

    suspend fun refreshTokens(dto: RememberedLoginRequestDto): ApiResponse<TokenResponseDto>

    suspend fun saveRefreshTokens(authToken: String, rememberedToken: String)
}