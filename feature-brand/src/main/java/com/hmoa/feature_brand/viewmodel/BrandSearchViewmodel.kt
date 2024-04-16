package com.hmoa.feature_brand.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.data.Consonant
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandSearchResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    val PAGE_SIZE = 10
    private var consonantBrandMapState =
        MutableStateFlow<MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?>(null)
    private var searchWordState = MutableStateFlow<String>("")
    private var searchResultState = MutableStateFlow<List<BrandSearchResponseDto>?>(emptyList())
    var uiState: StateFlow<BrandSearchUiState> =
        combine(
            consonantBrandMapState,
            searchWordState,
            searchResultState
        ) { consonantsBrands, searchWord, searchResult ->
            BrandSearchUiState.Data(consonants = consonantsBrands, searchWord = searchWord, searchResult = searchResult)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BrandSearchUiState.Loading
        )

    init {
        initializeBrands()
    }

//    fun getConsonantBrandsPagingSource(): Flow<PagingData<BrandDefaultResponseDto>>? {
//        return Pager(
//            config = PagingConfig(PAGE_SIZE),
//            pagingSourceFactory = { ConsonantBrandsPagingSource(searchRepository) }
//        ).flow.cachedIn(viewModelScope)
//    }

    fun initializeBrands() {
        viewModelScope.launch {
            for (i in 1..19) {
                flow { emit(searchRepository.getBrandAll(i)) }.asResult().collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val newMap = consonantBrandMapState.value?.toMutableMap() ?: mutableMapOf()
                            newMap[mapNumberIntoConsonant(i)] = it.data.data
                            consonantBrandMapState.value = newMap
                            Log.d("BrandSearchViewModel Key", mapNumberIntoConsonant(i).toString())
                            Log.d("BrandSearchViewModel Value", it.data.data.toString())
                            Log.d("BrandSearchViewModel MapState", newMap.toString())
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    fun clearWord() {
        searchWordState.value = ""
    }

    fun searchBrandResult(word: String) {
        searchWordState.value = word
        viewModelScope.launch {
            flow { emit(searchRepository.getBrand(word)) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        searchResultState.value = it.data.data
                    }

                    is Result.Error -> {

                    }

                    is Result.Loading -> {

                    }
                }
            }
        }
    }

    fun mapNumberIntoConsonant(number: Int): Consonant? {
        return when (number) {
            Consonant.ㄱ.id -> Consonant.ㄱ
            Consonant.ㄴ.id -> Consonant.ㄴ
            Consonant.ㄷ.id -> Consonant.ㄷ
            Consonant.ㄹ.id -> Consonant.ㄹ
            Consonant.ㅁ.id -> Consonant.ㅁ
            Consonant.ㅂ.id -> Consonant.ㅂ
            Consonant.ㅅ.id -> Consonant.ㅅ
            Consonant.ㅇ.id -> Consonant.ㅇ
            Consonant.ㅈ.id -> Consonant.ㅈ
            Consonant.ㅊ.id -> Consonant.ㅊ
            Consonant.ㅋ.id -> Consonant.ㅋ
            Consonant.ㅌ.id -> Consonant.ㅌ
            Consonant.ㅍ.id -> Consonant.ㅍ
            Consonant.ㅎ.id -> Consonant.ㅎ
            Consonant.ㄲ.id -> Consonant.ㄲ
            Consonant.ㄸ.id -> Consonant.ㄸ
            Consonant.ㅃ.id -> Consonant.ㅃ
            Consonant.ㅆ.id -> Consonant.ㅆ
            Consonant.ㅉ.id -> Consonant.ㅉ
            else -> null
        }
    }

    sealed interface BrandSearchUiState {
        data object Loading : BrandSearchUiState
        data class Data(
            var consonants: MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?,
            var searchWord: String,
            var searchResult: List<BrandSearchResponseDto>?
        ) : BrandSearchUiState

        data object Error : BrandSearchUiState
    }

}