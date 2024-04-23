package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.PerfumeComment.PerfumeCommentDataStore
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import javax.inject.Inject

class PerfumeCommentRepositoryImpl @Inject constructor(private val perfumeCommentDataStore: PerfumeCommentDataStore) :
    com.hmoa.core_domain.repository.PerfumeCommentRepository {
    override suspend fun getPerfumeComment(commentId: Int): PerfumeCommentResponseDto {
        return perfumeCommentDataStore.getPerfumeComment(commentId)
    }

    override suspend fun getPerfumeCommentsLatest(
        page: Int,
        cursor: Int,
        perfumeId: Int
    ): PerfumeCommentGetResponseDto {
        return perfumeCommentDataStore.getPerfumeCommentsLatest(page, cursor, perfumeId)
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

    override suspend fun putPerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        return perfumeCommentDataStore.putPerfumeCommentLike(commentId)
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        return perfumeCommentDataStore.deletePerfumeCommentLike(commentId)
    }

    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return perfumeCommentDataStore.putPerfumeCommentModify(commentId, dto)
    }

}