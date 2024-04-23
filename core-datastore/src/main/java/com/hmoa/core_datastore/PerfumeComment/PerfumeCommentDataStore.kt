package com.hmoa.core_datastore.PerfumeComment

import ResultResponse
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto

interface PerfumeCommentDataStore {
    suspend fun getPerfumeComment(commentId: Int): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLatest(page: Int, cursor: Int, perfumeId: Int): PerfumeCommentGetResponseDto
    suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
    suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto
    suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any>
    suspend fun putPerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>>
    suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>>
    suspend fun putPerfumeCommentModify(commentId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto
}