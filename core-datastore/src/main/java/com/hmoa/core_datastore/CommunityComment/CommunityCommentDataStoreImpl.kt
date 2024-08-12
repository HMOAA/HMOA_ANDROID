package com.hmoa.core_datastore.CommunityComment

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_network.service.CommunityCommentService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CommunityCommentDataStoreImpl @Inject constructor(private val communityCommentService: CommunityCommentService) :
    CommunityCommentDataStore {
    override suspend fun getCommunityComment(
        commentId: Int
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.getCommunityComment(commentId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
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
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityComment(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
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
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityCommentLiked(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
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
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getMyCommunityCommentsByHeart(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        communityCommentService.getMyCommunityCommentsByHeart(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getMyCommunityComments(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        val result = ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>()
        communityCommentService.getMyCommunityComments(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}