package com.hyangmoa.core_domain.usecase

import com.hyangmoa.core_domain.repository.LoginRepository
import javax.inject.Inject

class SaveGoogleTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(token: String) {}
}