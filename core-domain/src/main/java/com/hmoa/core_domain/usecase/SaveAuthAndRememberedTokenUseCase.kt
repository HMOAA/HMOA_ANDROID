package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class SaveAuthAndRememberedTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(authToken: String, rememberedToken: String) {
        loginRepository.saveAuthToken(authToken)
        loginRepository.saveRememberedToken(rememberedToken)
    }
}