package com.hmoa.core_datastore.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

private class LoginDataStoreImpl : LoginDataStore {
    override fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: String
    ): MemberLoginResponseDto {
        TODO("Not yet implemented")
    }

    override fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        TODO("Not yet implemented")
    }
}