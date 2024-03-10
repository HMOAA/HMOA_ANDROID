package com.hmoa.core_datastore.Login

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class LoginRemoteDataStoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val tokenManager: TokenManager
) : LoginRemoteDataStore {


    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        return loginService.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        var result = TokenResponseDto("", "")
        loginService.postRemembered(dto).suspendOnSuccess {
            result.authToken = this.data.authToken
            result.rememberedToken = this.data.rememberedToken
        }
        return result
    }
}