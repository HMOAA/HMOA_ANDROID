package com.hmoa.core_datastore.Login

import ResultResponse
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import javax.inject.Inject

class LoginRemoteDataStoreImpl @Inject constructor(
    private val loginService: LoginService,
) : LoginRemoteDataStore {


    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        return loginService.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): ResultResponse<TokenResponseDto> {
        var result = ResultResponse<TokenResponseDto>(data = null, errorCode = null, errorMessage = null)
        loginService.postRemembered(dto).suspendMapSuccess {
            result.data!!.authToken = this.authToken
            result.data!!.rememberedToken = this.rememberedToken
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }
}