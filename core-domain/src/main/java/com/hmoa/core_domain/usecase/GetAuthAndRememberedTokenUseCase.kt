package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class GetAuthAndRememberedTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): Pair<String?, String?> {
        return Pair(loginRepository.getAuthToken(), loginRepository.getRememberedToken())
    }
}