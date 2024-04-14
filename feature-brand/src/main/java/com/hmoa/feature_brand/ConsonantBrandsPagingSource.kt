package com.hmoa.feature_brand

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.BrandDefaultResponseDto

class ConsonantBrandsPagingSource(
    val searchRepository: SearchRepository
) : PagingSource<Int, BrandDefaultResponseDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BrandDefaultResponseDto> {
        val consonant = params.key ?: 1
        try {
            val response = searchRepository.getBrandAll(consonant)

            if (response?.data == null) {
                throw response?.exception!!
            }

            val prevKey = if (consonant > 0) consonant - 1 else null
            val nextKey = if (consonant <= 19) null else consonant + 1

            return LoadResult.Page(
                data = response.data!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BrandDefaultResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}