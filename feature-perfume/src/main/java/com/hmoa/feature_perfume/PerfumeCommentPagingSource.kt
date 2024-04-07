package com.hmoa.feature_perfume

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.PerfumeCommentResponseDto

class PerfumeCommentPagingSource(
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val page: Int,
    private val perfumeId: Int,
) : PagingSource<Int, PerfumeCommentResponseDto>() {
    private var commentCounts = 0
    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PerfumeCommentResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response =
                perfumeCommentRepository.getPerfumeCommentsLatest(
                    page,
                    cursor = cursor,
                    perfumeId = perfumeId
                )
            commentCounts = response.commentCount
            cursor = response.comments.get(response.comments.lastIndex).id
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = response.comments,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PerfumeCommentResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}