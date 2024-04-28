package com.hmoa.app

import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    suspend fun authToken(): Flow<String?> {
        return loginRepository.getAuthToken()
    }

    suspend fun rememberedToken(): Flow<String?> {
        return loginRepository.getRememberedToken()
    }
}