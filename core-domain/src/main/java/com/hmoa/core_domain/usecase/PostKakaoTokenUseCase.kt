package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostKakaoTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(token: String): Flow<ResultResponse<MemberLoginResponseDto>> {
        while (true) {
            val result = loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.KAKAO)
            return flow {
                emit(result)
                delay(1_000)
            }
        }
    }
}