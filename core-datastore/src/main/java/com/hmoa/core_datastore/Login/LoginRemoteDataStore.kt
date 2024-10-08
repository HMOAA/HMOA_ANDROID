package com.hmoa.core_datastore.Login

import ResultResponse
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginRemoteDataStore {
    suspend fun postOAuth(accessToken: OauthLoginRequestDto, provider: Provider): ResultResponse<MemberLoginResponseDto>
    suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto>
    suspend fun postGoogleServerAuthCode(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto>
    suspend fun refreshToken()
}