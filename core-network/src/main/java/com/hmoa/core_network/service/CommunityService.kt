package com.hmoa.core_network.service

import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.CommunityWithCursorResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PagingData
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CommunityService {
    @GET("/community/{communityId}")
    suspend fun getCommunity(@Path(value = "communityId") communityId: Int): ApiResponse<CommunityDefaultResponseDto>

    @Multipart
    @POST("/community/{communityId}")
    suspend fun postCommunityUpdate(
        @Part images: Array<MultipartBody.Part>,
        @Part("deleteCommunityPhotoIds") deleteCommunityPhotoIds: Array<RequestBody>,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
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
    ): CommunityWithCursorResponseDto

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

    @GET("/community/like")
    suspend fun getMyCommunitiesByHeart(
        @Query("cursor") cursor : Int
    ) : ApiResponse<PagingData<CommunityByCategoryResponseDto>>

    @GET("/community/me")
    suspend fun getMyCommunities(
        @Query("cursor") cursor : Int
    ) : ApiResponse<PagingData<CommunityByCategoryResponseDto>>
}