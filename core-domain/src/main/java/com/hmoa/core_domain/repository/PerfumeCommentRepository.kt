package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto

interface PerfumeCommentRepository {
    suspend fun getPerfumeComment(commentId: Int): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLatest(page: Int, cursor: Int, perfumeId: Int): PerfumeCommentGetResponseDto
    suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto
    suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any>
    suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentModify(commentId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsWithHeart(cursor : Int) : ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
    suspend fun getMyPerfumeComments(cursor : Int) : ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
}