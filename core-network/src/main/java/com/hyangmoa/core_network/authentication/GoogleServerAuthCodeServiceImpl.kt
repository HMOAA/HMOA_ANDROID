package com.hyangmoa.core_network.authentication

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hyangmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hyangmoa.core_network.service.LoginService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GoogleServerAuthCodeServiceImpl @Inject constructor() : GoogleServerAuthCodeService {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val okHttp =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS).build()

    override suspend fun postGoogleServerAuthCodeServiceImpl(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto> {
        val result = ResultResponse<GoogleAccessTokenResponseDto>(null)
        createWebService("https://oauth2.googleapis.com", okHttp).create(LoginService::class.java)
            .postGoogleServerAuthCode(dto.code, dto.client_id, dto.client_secret, dto.redirect_uri, dto.grant_type)
            .suspendMapSuccess {
                result.data = this
            }
            .suspendOnError {
                result.errorMessage = ErrorMessage(code = this.statusCode.code.toString(), message = this.message())
            }
        return result
    }
}
