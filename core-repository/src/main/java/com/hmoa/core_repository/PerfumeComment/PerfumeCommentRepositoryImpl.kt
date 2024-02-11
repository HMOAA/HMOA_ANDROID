package com.hmoa.core_repository.PerfumeComment

import com.hmoa.core_datastore.PerfumeComment.PerfumeCommentDataStore
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto

internal class PerfumeCommentRepositoryImpl constructor(private val perfumeCommentDataStore: PerfumeCommentDataStore) :
    PerfumeCommentRepository {
    override suspend fun getPerfumeCommentsLatest(page: String, perfumeId: Int): PerfumeCommentGetResponseDto {
        return perfumeCommentDataStore.getPerfumeCommentsLatest(page, perfumeId)
    }

    override suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto {
        return perfumeCommentDataStore.postPerfumeComment(perfumeId, dto)
    }

    override suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto {
        return perfumeCommentDataStore.getPerfumeCommentsLikest(page, perfumeId)
    }

    override suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentDataStore.deletePerfumeComments(commentId)
    }

    override suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentDataStore.putPerfumeCommentLike(commentId)
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentDataStore.deletePerfumeCommentLike(commentId)
    }

    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return perfumeCommentDataStore.putPerfumeCommentModify(commentId, dto)
    }

}