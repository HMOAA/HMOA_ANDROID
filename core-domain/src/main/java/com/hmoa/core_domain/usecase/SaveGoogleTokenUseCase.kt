package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class SaveGoogleTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(token: String) {}
}