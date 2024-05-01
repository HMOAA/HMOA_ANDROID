package com.hmoa.feature_brand

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.BrandRepository
import com.hmoa.core_model.response.BrandPerfumeBriefResponseDto

class PerfumePagingSource(
    val brandRepository: BrandRepository,
    val brandId: Int
) : PagingSource<Int, BrandPerfumeBriefResponseDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BrandPerfumeBriefResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response = brandRepository.getPerfumesSortedUpdate(brandId = brandId, pageNum = pageNumber)

            if (response?.errorMessage != null) {
                throw Exception(response?.errorMessage!!.message)
            }

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.data!!.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = response.data!!.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BrandPerfumeBriefResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}