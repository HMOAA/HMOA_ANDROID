package com.hyangmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyangmoa.core_domain.repository.SearchRepository
import com.hyangmoa.feature_community.ViewModel.PAGE_SIZE
import com.hyangmoa.feature_hpedia.SearchResultPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HPediaSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    //검색 카테고리?
    private val _type = MutableStateFlow<String?>(null)
    val type get() = _type.asStateFlow()

    //검색어
    private val _searchWord = MutableStateFlow("")
    val searchWord get() = _searchWord.asStateFlow()

    //상단바 상태
    private val _topBarState = MutableStateFlow(false)
    val topBarState get() = _topBarState.asStateFlow()

    //err 상태
    private val _errState = MutableStateFlow<String?>(null)
    val errState get() = _errState.asStateFlow()


    //type 설정
    fun setType(newType : String?){
        if (newType == null) {
            _errState.update{ "Type is NULL" }
        } else {
            _type.update{ newType }
        }
    }

    //상단바 상태 수정
    fun updateTopBarState(state : Boolean) {
        _topBarState.update{ state }
    }

    //검색어 변경
    fun updateSearchWord(newSearchWord : String){
        _searchWord.update{ newSearchWord }
    }

    //paging데이터 외부 노출
    fun communityPagingSource() : Flow<PagingData<Any>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getSearchResultPaging()
        }
    ).flow.cachedIn(viewModelScope)

    //paging 가져오기
    private fun getSearchResultPaging() : SearchResultPagingSource{
        return SearchResultPagingSource(
            searchRepository = searchRepository,
            type = type.value,
            searchWord = searchWord.value
        )
    }
}