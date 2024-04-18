package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.LoginRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetAuthAndRememberedTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): Pair<String?, String?> {
        var authToken: String? = null
        val rememberToken: String? = null
        loginRepository.getAuthToken().collectLatest {
            authToken = it
        }
        loginRepository.getRememberedToken().collectLatest {
            rememberToken
        }
        return Pair(authToken, rememberToken)
    }
}