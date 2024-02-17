package com.hmoa.core_repository.Login

import com.hmoa.core_datastore.Login.LoginDataStore
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

class LoginRepositoryImpl(
    private val loginDataStore: LoginDataStore
) : LoginRepository {

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: String
    ): MemberLoginResponseDto {
        return loginDataStore.postOAuth(accessToken, provider)
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        return loginDataStore.postRemembered(dto)
    }
}