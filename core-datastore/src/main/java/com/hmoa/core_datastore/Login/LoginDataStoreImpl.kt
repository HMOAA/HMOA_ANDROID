package com.hmoa.core_datastore.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import corenetwork.Login.LoginService

private class LoginDataStoreImpl constructor(
    private val loginService: LoginService
) : LoginDataStore {
    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: String
    ): MemberLoginResponseDto {
        return loginService.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        return loginService.postRemembered(dto)
    }
}