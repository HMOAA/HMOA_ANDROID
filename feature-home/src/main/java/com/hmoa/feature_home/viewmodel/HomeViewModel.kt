package com.hmoa.feature_home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.MainRepository
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val fcmRepository: FcmRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var _firstMenuWithBannerState =
        MutableStateFlow<BannerWithFirstMenuState>(BannerWithFirstMenuState.Loading)
    val firstMenuWithBannerState: StateFlow<BannerWithFirstMenuState> = _firstMenuWithBannerState

    private var _bottomMenuState = MutableStateFlow<BottomMenuState>(BottomMenuState.Loading)
    val bottomMenuState: StateFlow<BottomMenuState> = _bottomMenuState

    private val fcmToken = MutableStateFlow<String?>(null)
    private val authToken = MutableStateFlow<String?>(null)

    init {
        getFirstMenuWithBanner()
        getSecondMenu()
        getAuthToken()
    }

    fun getFirstMenuWithBanner() {
        viewModelScope.launch {
            flow { emit(mainRepository.getFirst()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        _firstMenuWithBannerState.update { it }
                        _firstMenuWithBannerState.value = BannerWithFirstMenuState.Data(
                            bannerImg = it.data.data!!.mainImage,
                            bannerTitle = it.data.data!!.banner,
                            firstMenu = it.data.data!!.firstMenu
                        )
                    }

                    is Result.Error -> {}//TODO()
                    is Result.Loading -> {
                        BannerWithFirstMenuState.Loading
                    }//TODO()
                }
            }
        }

    }

    fun getSecondMenu() {
        viewModelScope.launch {
            flow { emit(mainRepository.getSecond()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        _bottomMenuState.value =
                            BottomMenuState.Data(it.data.data)
                    }

                    is Result.Error -> {

                    }//TODO()
                    is Result.Loading -> {
                        BottomMenuState.Loading
                    }//TODO()
                }
            }
        }
    }

    private fun getAuthToken() {
        Log.d("TAG TEST", "get auth token")
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().onEmpty {}.collectLatest {
                Log.d("TAG TEST", "token : ${it}")
                authToken.value = it
            }
            Log.d("TAG TEST", "auth token : ${authToken.value}")
        }
    }

    fun postFCMToken(token: String) {
        if (authToken.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("TAG TEST", "fcmToken : ${token}")
                val requestDto = FCMTokenSaveRequestDto(token)
                Log.d("TAG TEST", "requestDto : ${requestDto}")
                try {
                    fcmRepository.saveFcmToken(requestDto)
                } catch (e: Exception) {
                    Log.e("TAG TEST", "Error : ${e.message}")
                }
            }
        } else {
            Log.e("TAG TEST", "Not Login")
        }
    }

    sealed interface BannerWithFirstMenuState {
        data object Loading : BannerWithFirstMenuState
        data class Data(
            val bannerImg: String?,
            val bannerTitle: String?,
            val firstMenu: HomeMenuDefaultResponseDto?
        ) : BannerWithFirstMenuState

        data object Error : BannerWithFirstMenuState
    }

    sealed interface BottomMenuState {
        data object Loading : BottomMenuState
        data class Data(
            val bottomMenu: List<HomeMenuDefaultResponseDto>?,
        ) : BottomMenuState

        data object Error : BottomMenuState
    }

}