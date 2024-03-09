package com.hmoa.core_network.service

import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @FormUrlEncoded
    @POST("/login/oauth2/{provider}")
    suspend fun postOAuth(@Body dto: OauthLoginRequestDto, @Path("provider") provider: Provider): MemberLoginResponseDto

    @FormUrlEncoded
    @POST("/login/remembered")
    suspend fun postRemembered(dto: RememberedLoginRequestDto): ApiResponse<TokenResponseDto>
}