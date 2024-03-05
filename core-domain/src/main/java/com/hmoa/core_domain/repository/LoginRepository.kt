package com.hmoa.core_domain.repository

import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginRepository {
    suspend fun getAuthToken(): String?
    suspend fun getRememberedToken(): String?
    suspend fun getKakaoAccessToken(): String?
    suspend fun postOAuth(accessToken: OauthLoginRequestDto, provider: Provider): MemberLoginResponseDto
    suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun deleteKakaoAccessToken()
}