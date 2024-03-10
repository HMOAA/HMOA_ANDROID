package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class SaveSocialTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun saveKakaoAccessToken(token: String) {
        loginRepository.saveKakaoAccessToken(token)
    }

    suspend fun saveGoogleAccessToken(token: String) {}
}