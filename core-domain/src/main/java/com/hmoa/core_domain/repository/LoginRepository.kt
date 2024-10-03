package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRememberedToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>
    suspend fun getGoogleAccessToken(): Flow<String?>
    suspend fun postOAuth(accessToken: OauthLoginRequestDto, provider: Provider): ResultResponse<MemberLoginResponseDto>
    suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto>
    suspend fun postGoogleServerAuthCode(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun saveGoogleAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRememberedToken()
    suspend fun deleteKakaoAccessToken()
    suspend fun deleteGoogleAccessToken()
    suspend fun refreshToken()
}