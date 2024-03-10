package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSocialTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    fun postKakaoAccessToken(token: String): Flow<MemberLoginResponseDto> {
        return flow {
            emit(loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.KAKAO))
        }
    }

    fun postGoogleAccessToken(token: String): Flow<MemberLoginResponseDto> {
        return flow {
            emit(loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.GOOGLE))
        }
    }
}