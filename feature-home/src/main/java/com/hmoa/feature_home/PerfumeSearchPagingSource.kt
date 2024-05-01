package com.hmoa.feature_home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.PerfumeSearchResponseDto

class PerfumeSearchPagingSource(
    val searchRepository: SearchRepository,
    val word: String
) : PagingSource<Int, PerfumeSearchResponseDto>() {
    override fun getRefreshKey(state: PagingState<Int, PerfumeSearchResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PerfumeSearchResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response = searchRepository.getPerfume(page = pageNumber, searchWord = word)

            if (response.errorMessage != null) {
                throw Exception(response.errorMessage!!.message)
            }

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.data!!.isEmpty()) null else pageNumber + 1

            return LoadResult.Page(
                data = response.data!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}