package com.hmoa.core_datastore.PerfumeComment

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.core_network.PerfumeComment.PerfumeCommentService

internal class PerfumeCommentDataStoreImpl constructor(private val perfumeCommentService: PerfumeCommentService) :
    PerfumeCommentDataStore {
    override suspend fun getPerfumeCommentsLatest(page: String, perfumeId: Int): PerfumeCommentGetResponseDto {
        return perfumeCommentService.getPerfumeCommentsLatest(page, perfumeId)
    }

    override suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto {
        return perfumeCommentService.postPerfumeComment(perfumeId, dto)
    }

    override suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto {
        return perfumeCommentService.getPerfumeCommentsLikest(page, perfumeId)
    }

    override suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentService.deletePerfumeComments(commentId)
    }

    override suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentService.putPerfumeCommentLike(commentId)
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentService.deletePerfumeCommentLike(commentId)
    }

    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return perfumeCommentService.putPerfumeCommentModify(commentId, dto)
    }
}