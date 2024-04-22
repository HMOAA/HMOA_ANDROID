package com.hmoa.core_datastore.PerfumeComment

import ResultResponse
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.core_network.service.PerfumeCommentService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class PerfumeCommentDataStoreImpl @Inject constructor(private val perfumeCommentService: PerfumeCommentService) :
    PerfumeCommentDataStore {
    override suspend fun getPerfumeComment(commentId: Int): PerfumeCommentResponseDto {
        return perfumeCommentService.getPerfumeComment(commentId)
    }

    override suspend fun getPerfumeCommentsLatest(
        page: Int,
        cursor: Int,
        perfumeId: Int
    ): PerfumeCommentGetResponseDto {
        return perfumeCommentService.getPerfumeCommentsLatest(page = page, cursor = cursor, perfumeId = perfumeId)
    }

    override suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto {
        return perfumeCommentService.postPerfumeComment(perfumeId, dto)
    }

    override suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto {
        return perfumeCommentService.getPerfumeCommentsLikest(page = page, perfumeId = perfumeId)
    }

    override suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any> {
        return perfumeCommentService.deletePerfumeComments(commentId)
    }

    override suspend fun putPerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        var result = ResultResponse<DataResponseDto<Nothing?>>()
        perfumeCommentService.putPerfumeCommentLike(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        var result = ResultResponse<DataResponseDto<Nothing?>>()
        perfumeCommentService.deletePerfumeCommentLike(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return perfumeCommentService.putPerfumeCommentModify(commentId, dto)
    }
}