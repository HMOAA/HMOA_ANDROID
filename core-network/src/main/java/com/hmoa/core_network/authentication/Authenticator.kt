package com.hmoa.core_network.authentication

import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.TokenResponseDto

interface Authenticator {
    suspend fun getAuthToken(): String?
    suspend fun getRememberedToken(): String?
    suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto
}