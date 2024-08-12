package com.hyangmoa.core_datastore.PerfumeComment

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.request.PerfumeCommentRequestDto
import com.hyangmoa.core_model.response.*
import com.hyangmoa.core_network.service.PerfumeCommentService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
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
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        var result = ResultResponse<DataResponseDto<Nothing?>>()
        perfumeCommentService.deletePerfumeCommentLike(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return perfumeCommentService.putPerfumeCommentModify(commentId, dto)
    }

    override suspend fun getPerfumeCommentsWithHeart(
        cursor: Int
    ): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        perfumeCommentService.getPerfumeCommentsWithHeart(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getMyPerfumeComments(
        cursor: Int
    ): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        perfumeCommentService.getMyPerfumeComments(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

}