package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.request.PerfumeCommentRequestDto
import com.hyangmoa.core_model.response.*

interface PerfumeCommentRepository {
    suspend fun getPerfumeComment(commentId: Int): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLatest(page: Int, cursor: Int, perfumeId: Int): PerfumeCommentGetResponseDto
    suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto
    suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>>
    suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>>
    suspend fun putPerfumeCommentModify(commentId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsWithHeart(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
    suspend fun getMyPerfumeComments(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
}