package com.hmoa.core_network.service

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*
import java.io.File

interface CommunityService {
    @GET("/community/{communityId}")
    suspend fun getCommunity(@Path(value = "communityId") communityId: Int): ApiResponse<CommunityDefaultResponseDto>

    @FormUrlEncoded
    @POST("/community/{communityId}")
    suspend fun postCommunityUpdate(
        @Field("images") images: Array<File>,
        @Field("deleteCommunityPhotoIds") deleteCommunityPhotoIds: Array<Int>,
        @Field("title") title: String,
        @Field("content") content: String,
        @Path("communityId") communityId: Int
    ): ApiResponse<CommunityDefaultResponseDto>

    @DELETE("/community/{communityId}")
    suspend fun deleteCommunity(@Path("communityId") communityId: Int): ApiResponse<DataResponseDto<Nothing>>

    @PUT("/community/{communityId}/like")
    suspend fun putCommunityLike(@Path("communityId") communityId: Int): ApiResponse<DataResponseDto<Nothing>>

    @DELETE("/community/{communityId}/like")
    suspend fun deleteCommunityLike(@Path("communityId") communityId: Int): ApiResponse<DataResponseDto<Nothing>>

    @GET("/community/category")
    suspend fun getCommunityByCategory(
        @Query("category") category: String,
        @Query("page") page: Int
    ): ApiResponse<List<CommunityByCategoryResponseDto>>

    @GET("/community/home")
    suspend fun getCommunitiesHome(): ApiResponse<List<CommunityByCategoryResponseDto>>

    @FormUrlEncoded
    @POST("/community/save")
    suspend fun postCommunitySave(
        @Field("images") images: Array<File>,
        @Field("category") category: String,
        @Field("title") title: String,
        @Field("content") content: String
    ): CommunityDefaultResponseDto

}