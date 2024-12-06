package com.hmoa.feature_userinfo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto

class FavoriteCommentPagingSource(
    private val communityCommentRepository: CommunityCommentRepository,
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val type: String,
) : PagingSource<Int, CommunityCommentDefaultResponseDto>() {

    private var totalCount = 0
    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityCommentDefaultResponseDto> {
        val pageNumber = params.key ?: 0
        val response = if (type == "향수") {
            perfumeCommentRepository.getPerfumeCommentsWithHeart(cursor)
        } else {
            communityCommentRepository.getMyCommunityCommentsByHeart(cursor)
        }
        if (response.errorMessage != null) {
            return LoadResult.Error(Exception(response.errorMessage!!.message))
        }
        val result = response.data!!
        totalCount = result.data.size
        cursor = if (totalCount == 0) 0 else result.data.last().id
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (result.lastPage) null else pageNumber + 1

        return LoadResult.Page(
            data = result.data,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityCommentDefaultResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}