package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.response.DataResponseDto
import retrofit2.http.*

interface BrandHPediaService {
    @GET("/brandstory")
    suspend fun getBrandStoryAll(@Field("pageNum") pageNum: Int): DataResponseDto<Any>

    @GET("/brandstory/{brandStoryId}")
    suspend fun getBrandStory(@Path(value = "brandStoryId") brandStoryId: Int): DataResponseDto<Any>

    @DELETE("/brandstory/{brandStoryId}")
    suspend fun deleteBrandStory(@Path(value = "brandStoryId") brandStoryId: Int): DataResponseDto<Any>

    @PUT("/brandstory/{brandStoryId}/update")
    suspend fun updateBrandStory(
        @Path(value = "brandStoryId") brandStoryId: Int,
        @Field("content") content: String
    ): DataResponseDto<Any>

    @FormUrlEncoded
    @POST("/brandstory/new")
    suspend fun postBrandStory(
        @Field("brandStorySubtitle") brandStorySubtitle: String,
        @Field("brandStoryTitle") brandStoryTitle: String,
        @Field("content") content: String
    ): DataResponseDto<Any>

    @POST("/brandstory/testSave")
    suspend fun postTestSave(): DataResponseDto<Any> //Test
}