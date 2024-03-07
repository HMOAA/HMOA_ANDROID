package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.SignupRepository
import javax.inject.Inject

class GetSignupInfoUseCase @Inject constructor(private val signupRepository: SignupRepository) {

    suspend fun getNickName(): String? {
        return signupRepository.getNickname()
    }

    suspend fun getSex(): String? {
        return signupRepository.getSex()
    }

    suspend fun getAge(): String? {
        return signupRepository.getAge()
    }

}