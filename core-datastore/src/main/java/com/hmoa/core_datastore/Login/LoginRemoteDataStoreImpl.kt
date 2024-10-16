package com.hmoa.core_datastore.Login

import ResultResponse
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.GoogleAccessTokenRequestDto
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.GoogleAccessTokenResponseDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.authentication.GoogleServerAuthCodeService
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class LoginRemoteDataStoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val googleServerAuthCodeService: GoogleServerAuthCodeService,
    private val authenticator: Authenticator
) : LoginRemoteDataStore {


    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): ResultResponse<MemberLoginResponseDto> {
        var result = ResultResponse<MemberLoginResponseDto>()
        loginService.postOAuth(accessToken, provider).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    loginService.postOAuth(accessToken, provider).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto> {
        var result = ResultResponse<TokenResponseDto>()
        loginService.postRemembered(dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    loginService.postRemembered(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun postGoogleServerAuthCode(dto: GoogleAccessTokenRequestDto): ResultResponse<GoogleAccessTokenResponseDto> {
        return googleServerAuthCodeService.postGoogleServerAuthCodeServiceImpl(dto)
    }
}