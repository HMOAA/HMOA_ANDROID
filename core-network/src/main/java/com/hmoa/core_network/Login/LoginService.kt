package com.hmoa.core_network.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginService {
    suspend fun postOAuth(accessToken : OauthLoginRequestDto, provider : String) : MemberLoginResponseDto
    suspend fun postRemembered(dto : RememberedLoginRequestDto) : TokenResponseDto
}