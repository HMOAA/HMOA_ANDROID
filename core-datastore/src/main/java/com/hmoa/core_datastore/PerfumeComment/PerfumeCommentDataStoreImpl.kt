package com.hmoa.core_datastore.PerfumeComment

import ResultResponse
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.PerfumeCommentService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class PerfumeCommentDataStoreImpl @Inject constructor(
    private val perfumeCommentService: PerfumeCommentService,
    private val authenticator: Authenticator
) :
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeCommentService.putPerfumeCommentLike(commentId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): ResultResponse<DataResponseDto<Nothing?>> {
        var result = ResultResponse<DataResponseDto<Nothing?>>()
        perfumeCommentService.deletePerfumeCommentLike(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeCommentService.deletePerfumeCommentLike(commentId)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeCommentService.getPerfumeCommentsWithHeart(cursor)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeCommentService.getMyPerfumeComments(cursor)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

}