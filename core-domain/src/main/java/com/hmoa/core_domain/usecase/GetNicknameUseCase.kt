package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.SignupRepository
import javax.inject.Inject

class GetNicknameUseCase @Inject constructor(private val signupRepository: SignupRepository) {

    suspend operator fun invoke(): String? {
        return signupRepository.getNickname()
    }
}