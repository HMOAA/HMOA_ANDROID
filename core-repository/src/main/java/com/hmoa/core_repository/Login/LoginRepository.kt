package com.hmoa.core_repository.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginRepository {
    suspend fun getAuthToken(): String?
    suspend fun getRememberedToken(): String?
    suspend fun postOAuth(accessToken: OauthLoginRequestDto, provider: String): MemberLoginResponseDto
    suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto
}