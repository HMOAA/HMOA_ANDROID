package com.hmoa.core_network.service

import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginService {
    @POST("/login/oauth2/{provider}")
    suspend fun postOAuth(
        @Body dto: OauthLoginRequestDto,
        @Path("provider") provider: Provider
    ): ApiResponse<MemberLoginResponseDto>

    @POST("/login/remembered")
    suspend fun postRemembered(@Body dto: RememberedLoginRequestDto): ApiResponse<TokenResponseDto>

    @POST("/token")
    suspend fun postGoogleServerAuthCode(
        @Query("code") code: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("grant_type") grant_type: String
    ): ApiResponse<GoogleAccessTokenResponseDto>
}