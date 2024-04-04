package com.hmoa.feature_perfume

import androidx.paging.PagingSource
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import java.io.IOException

class PerfumeCommentPagingSource(
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val page: Int,
    private val perfumeId: Int,
    private val isNextPageExist: Boolean,
) :
    PagingSource<Int, PerfumeCommentResponseDto>() {
    private var commentCounts = 0
    private var cursor = 0 //TODO("처음 요청할 때 어떤값으로 줘야 하는지 확인하기")
    private val STARTING_PAGE_INDEX = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PerfumeCommentResponseDto> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try {
            val response =
                perfumeCommentRepository.getPerfumeCommentsLatest(page, cursor = cursor, perfumeId = perfumeId)
            commentCounts = response.commentCount
            cursor = response.comments.get(response.comments.lastIndex).id

            return LoadResult.Page(
                data = response.comments,
                prevKey = calculatePrevKey(position),
                nextKey = calculateNextKey(isNextPageExist, position)
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    fun calculatePrevKey(position: Int): Int? {
        when (position) {
            STARTING_PAGE_INDEX -> return null
            else -> return position - 1
        }
    }

    fun calculateNextKey(isNextPageExist: Boolean, position: Int): Int? {
        when (isNextPageExist) {
            true -> return position + 1
            false -> return null
        }
    }

    fun getTotalPerfumeCommentCount(): Int {
        return commentCounts
    }

}