package com.hyangmoa.core_datastore.Login

import ResultResponse
import com.hyangmoa.core_model.Provider
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hyangmoa.core_model.request.OauthLoginRequestDto
import com.hyangmoa.core_model.request.RememberedLoginRequestDto
import com.hyangmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hyangmoa.core_model.response.MemberLoginResponseDto
import com.hyangmoa.core_model.response.TokenResponseDto
import com.hyangmoa.core_network.authentication.GoogleServerAuthCodeService
import com.hyangmoa.core_network.service.LoginService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LoginRemoteDataStoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val googleServerAuthCodeService: GoogleServerAuthCodeService
) : LoginRemoteDataStore {


    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): ResultResponse<MemberLoginResponseDto> {
        var result = ResultResponse<MemberLoginResponseDto>()
        loginService.postOAuth(accessToken, provider).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto> {
        var result = ResultResponse<TokenResponseDto>()
        loginService.postRemembered(dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun postGoogleServerAuthCode(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto> {
        return googleServerAuthCodeService.postGoogleServerAuthCodeServiceImpl(dto)
    }
}