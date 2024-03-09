package com.hmoa.core_network.service

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.*

interface CommunityCommentService {
    @PUT("/community/comment/{commentId}")
    suspend fun putCommunityComment(
        @Path(value = "commentId")
        commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto

    @DELETE("/community/comment/{commentId}")
    suspend fun deleteCommunityComment(@Path(value = "commentId") commentId: Int): DataResponseDto<Any>

    @GET("/community/comment/{commentId}/findAll")
    suspend fun getCommunityComments(
        @Path(value = "commentId") commentId: Int,
        @Field("page") page: String
    ): CommunityCommentAllResponseDto

    @FormUrlEncoded
    @POST("/community/comment/{commentId}/save")
    suspend fun postCommunityComment(
        @Path(value = "commentId")
        commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto
}