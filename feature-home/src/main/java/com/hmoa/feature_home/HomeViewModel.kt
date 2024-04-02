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
        MutableStateFlow<BannerWithFirstMenuState>(BannerWithFirstMenuState.Loading)
    val firstMenuWithBannerState: StateFlow<BannerWithFirstMenuState> = _firstMenuWithBannerState

    private var _bottomMenuState = MutableStateFlow<BottomMenuState>(BottomMenuState.Loading)
    val bottomMenuState: StateFlow<BottomMenuState> = _bottomMenuState

    init {
        getFirstMenuWithBanner()
        getSecondMenu()
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
                        _bottomMenuState.value =
                            BottomMenuState.Data(it.data.data)
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