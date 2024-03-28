package com.hmoa.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MainRepository
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private var _firstMenuWithBannerState =
        MutableStateFlow<BannerWithFirstMenuState?>(BannerWithFirstMenuState.Loading)
    val firstMenuWithBannerState: StateFlow<BannerWithFirstMenuState?> = _firstMenuWithBannerState

    private val secondMenu = MutableStateFlow<HomeMenuDefaultResponseDto?>(null)
    private val thirdMenu = MutableStateFlow<HomeMenuDefaultResponseDto?>(null)

    init {
        getFirstMenuWithBanner()
        getSecondMenu()
        getThirdMenu()
    }

    fun getFirstMenuWithBanner() {
        viewModelScope.launch {
            flow { emit(mainRepository.getFirst()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        _firstMenuWithBannerState.update { it }
                    }

                    is Result.Error -> {}//TODO()
                    is Result.Loading -> {}//TODO()
                }
            }
        }
    }

    fun getSecondMenu() {
        viewModelScope.launch {
            flow { emit(mainRepository.getSecond()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        secondMenu.update { it }
                    }

                    is Result.Error -> {
                        //uiState.value = HomeUiState.Error
                    }//TODO()
                    is Result.Loading -> {}//TODO()
                }
            }
        }
    }

    //세번째는 전체보기 메뉴 밖에 없어서 전체보기로 매핑함
    fun getThirdMenu() {
        viewModelScope.launch {
            flow { emit(mainRepository.getThirdMenu()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        thirdMenu.update { it }
                    }

                    is Result.Error -> {

                    }//TODO()
                    is Result.Loading -> {}//TODO()
                }
            }
        }
    }

    sealed interface BannerWithFirstMenuState {
        data object Loading : BannerWithFirstMenuState
        data class HomeData(
            val bannerImg: String?,
            val bannerTitle: String?,
            val firstMenu: HomeMenuDefaultResponseDto?
        ) : BannerWithFirstMenuState

        data object Error : BannerWithFirstMenuState
    }

    sealed interface SecondMenuState {
        data object Loading : SecondMenuState
        data class HomeData(
            val bannerImg: String?,
            val bannerTitle: String?,
            val firstMenu: HomeMenuDefaultResponseDto?
        ) : BannerWithFirstMenuState

        data object Error : BannerWithFirstMenuState
    }

}