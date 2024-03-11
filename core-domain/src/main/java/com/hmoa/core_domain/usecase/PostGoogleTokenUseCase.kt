package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostGoogleTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(token: String): Flow<MemberLoginResponseDto> = flow {
        loginRepository.postOAuth(accessToken = OauthLoginRequestDto(token), provider = Provider.GOOGLE)
    }
}