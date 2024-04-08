package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class SaveKakaoTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(token: String) {
        loginRepository.saveKakaoAccessToken(token)
    }
}