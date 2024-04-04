package com.hmoa.core_domain.repository

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto

interface PerfumeCommentRepository {
    suspend fun getPerfumeCommentsLatest(page: Int, cursor: Int, perfumeId: Int): PerfumeCommentGetResponseDto
    suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto
    suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any>
    suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentModify(commentId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
}