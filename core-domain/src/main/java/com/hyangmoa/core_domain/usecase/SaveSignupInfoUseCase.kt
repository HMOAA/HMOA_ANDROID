package com.hyangmoa.core_domain.usecase

import com.hyangmoa.core_domain.repository.SignupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveSignupInfoUseCase @Inject constructor(private val signupRepository: SignupRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    operator fun invoke(value: String) {
        scope.launch { signupRepository.saveNickname(value) }
    }
}