package com.hmoa.feature_hbti.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.HShopReviewRepository
import com.hmoa.core_model.response.ReviewResponseDto

class ReviewPagingSource (
    private val hShopReviewRepository: HShopReviewRepository
): PagingSource<Int, ReviewResponseDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response = hShopReviewRepository.getReviews(page = pageNumber).data!!
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}