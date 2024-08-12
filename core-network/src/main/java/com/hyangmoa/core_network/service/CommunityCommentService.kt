package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hyangmoa.core_model.response.CommunityCommentAllResponseDto
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hyangmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PagingData
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface CommunityCommentService {
    @GET("/community/comment/{commentId}")
    suspend fun getCommunityComment(
        @Path("commentId") commentId : Int
    ) : ApiResponse<CommunityCommentWithLikedResponseDto>

    @PUT("/community/comment/{commentId}")
    suspend fun putCommunityComment(
        @Path(value = "commentId") commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): ApiResponse<CommunityCommentWithLikedResponseDto>

    @DELETE("/community/comment/{commentId}")
    suspend fun deleteCommunityComment(
        @Path(value = "commentId") commentId: Int
    ): ApiResponse<DataResponseDto<Any>>

    @PUT("/community/comment/{commentId}/like")
    suspend fun putCommunityCommentLiked(
        @Path(value = "commentId") commentId: Int
    ): ApiResponse<DataResponseDto<Any>>

    @DELETE("/community/comment/{commentId}/like")
    suspend fun deleteCommunityCommentLiked(
        @Path("commentId") commentId: Int
    ): ApiResponse<DataResponseDto<Any>>

    @GET("/community/comment/{communityId}/findAll/cursor")
    suspend fun getAllCommunityComment(
        @Path("communityId") communityId: Int,
        @Query("cursor") cursor: Int
    ): CommunityCommentAllResponseDto

    @POST("/community/comment/{communityId}/save")
    suspend fun postCommunityComment(
        @Path(value = "communityId") commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): ApiResponse<CommunityCommentWithLikedResponseDto>

    @GET("/community/comment/like")
    suspend fun getMyCommunityCommentsByHeart(
        @Query("cursor") cursor : Int,
    ) : ApiResponse<PagingData<CommunityCommentDefaultResponseDto>>

    @GET("/community/comment/me")
    suspend fun getMyCommunityComments(
        @Query("cursor") cursor : Int
    ) : ApiResponse<PagingData<CommunityCommentDefaultResponseDto>>
}