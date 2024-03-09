package com.hmoa.core_network.service

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.*
import java.io.File

interface CommunityService {
    @GET("/community/{communityId}")
    suspend fun getCommunity(@Path(value = "communityId") communityId: Int): CommunityDefaultResponseDto

    @FormUrlEncoded
    @POST("/community/{communityId}")
    suspend fun postCommunityUpdate(
        @Field("images") images: Array<File>,
        @Field("eleteCommunityPhotoIds") deleteCommunityPhotoIds: Array<Int>,
        @Field("title") title: String,
        @Field("content") content: String,
        @Path("communityId") communityId: Int
    ): CommunityDefaultResponseDto

    @DELETE("/community/{communityId}")
    suspend fun deleteCommunity(@Path("communityId") communityId: Int): DataResponseDto<Nothing>

    @GET("/community/{communityId}/like")
    suspend fun putCommuntiyLike(@Path("communityId") communityId: Int): DataResponseDto<Nothing>

    @GET("/community/{communityId}/like")
    suspend fun deleteCommunityLike(@Path("communityId") communityId: Int): DataResponseDto<Nothing>

    @GET("/community/{category}")
    suspend fun getCommunityByCategory(
        @Path("category") category: Category,
        @Field("page") page: String
    ): CommunityByCategoryResponseDto

    @GET("/community/home")
    suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto>

    @FormUrlEncoded
    @POST("/community/save")
    suspend fun postCommunitySave(
        @Field("images") images: Array<File>,
        @Field("category") category: Category,
        @Field("title") title: String,
        @Field("content") content: String
    ): CommunityDefaultResponseDto

}