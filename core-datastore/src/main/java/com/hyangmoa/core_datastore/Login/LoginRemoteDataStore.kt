package com.hyangmoa.core_datastore.Login

import ResultResponse
import com.hyangmoa.core_model.Provider
import com.hyangmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hyangmoa.core_model.request.OauthLoginRequestDto
import com.hyangmoa.core_model.request.RememberedLoginRequestDto
import com.hyangmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hyangmoa.core_model.response.MemberLoginResponseDto
import com.hyangmoa.core_model.response.TokenResponseDto

interface LoginRemoteDataStore {
    suspend fun postOAuth(accessToken: OauthLoginRequestDto, provider: Provider): ResultResponse<MemberLoginResponseDto>
    suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto>
    suspend fun postGoogleServerAuthCode(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto>
}