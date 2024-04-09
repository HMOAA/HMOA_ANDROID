package com.hmoa.core_network.service

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.CommunityWithCursorResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("/community/category/cursor")
    suspend fun getCommunityByCategory(
        @Query("category") category: String,
        @Query("cursor") cursor: Int
    ): ApiResponse<CommunityWithCursorResponseDto>

    @GET("/community/home")
    suspend fun getCommunitiesHome(): ApiResponse<List<CommunityByCategoryResponseDto>>

    @Multipart
    @POST("/community/save")
    suspend fun postCommunitySave(
        @Part images: Array<MultipartBody.Part>,
        @Part title: MultipartBody.Part,
        @Part category: MultipartBody.Part,
        @Part content: MultipartBody.Part
    ): ApiResponse<CommunityDefaultResponseDto>

}