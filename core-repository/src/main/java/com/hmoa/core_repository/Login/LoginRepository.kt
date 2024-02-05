package com.hmoa.core_repository.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto

interface LoginRepository {
    fun postOAuth(accessToken : OauthLoginRequestDto, provider : String) : MemberLoginResponseDto
    fun postRemembered(dto : RememberedLoginRequestDto) : TokenResponseDto
}