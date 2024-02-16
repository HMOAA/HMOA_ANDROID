package com.hmoa.core_datastore.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginDataStore {
    suspend fun postOAuth(accessToken : OauthLoginRequestDto, provider : String) : MemberLoginResponseDto
    suspend fun postRemembered(dto : RememberedLoginRequestDto) : TokenResponseDto
}