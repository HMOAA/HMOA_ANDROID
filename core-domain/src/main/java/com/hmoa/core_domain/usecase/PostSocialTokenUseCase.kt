package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSocialTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun postKakaoAccessToken(token: String): Flow<MemberLoginResponseDto> {
        while (true) {
            val result = loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.KAKAO)
            return flow {
                emit(result)
                delay(1_000)
            }
        }
    }

    suspend fun postGoogleAccessToken(token: String): Flow<MemberLoginResponseDto> = flow {
        loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.GOOGLE)
    }
}