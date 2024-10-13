package com.hmoa.core_datastore.CommunityComment

import ResultResponse
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.CommunityCommentService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class CommunityCommentDataStoreImpl @Inject constructor(
    private val communityCommentService: CommunityCommentService,
    private val authenticator: Authenticator
) :
    CommunityCommentDataStore {
    override suspend fun getCommunityComment(
        commentId: Int
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.getCommunityComment(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.getCommunityComment(commentId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.putCommunityComment(commentId, dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.putCommunityComment(commentId, dto)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityComment(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.deleteCommunityComment(commentId)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun putCommunityCommentLiked(
        commentId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.putCommunityCommentLiked(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.putCommunityCommentLiked(commentId)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityCommentLiked(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.deleteCommunityCommentLiked(commentId)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getAllCommunityComment(
        communityId: Int,
        cursor: Int
    ): CommunityCommentAllResponseDto {
        return communityCommentService.getAllCommunityComment(communityId, cursor)
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.postCommunityComment(communityId, dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.postCommunityComment(communityId, dto)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getMyCommunityCommentsByHeart(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        communityCommentService.getMyCommunityCommentsByHeart(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.getMyCommunityCommentsByHeart(cursor)
                        .suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getMyCommunityComments(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        communityCommentService.getMyCommunityComments(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityCommentService.getMyCommunityComments(cursor).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}