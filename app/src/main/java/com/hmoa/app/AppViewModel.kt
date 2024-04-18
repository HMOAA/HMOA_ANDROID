package com.hmoa.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var _authTokenState = MutableStateFlow<String?>(null)
    var authTokenState = _authTokenState
    private var _rememberedTokenState = MutableStateFlow<String?>(null)
    var rememberedTokenState = _rememberedTokenState

    init {
        initializeRoute()
    }

    fun initializeRoute() {
        viewModelScope.launch {
            loginRepository.getRememberedToken().onEmpty { }.collectLatest {
                _rememberedTokenState.value = it
            }
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                _authTokenState.value = it
            }
        }
    }
}