package com.hmoa.core_network.service

import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.*
import java.io.File

interface BrandService {
    @GET("/brand/{brandId}")
    suspend fun getBrand(@Path(value = "brandId") brandId: Int): DataResponseDto<Any>

    @PUT("/brand/{brandId}/like")
    suspend fun putBrandLike(@Path(value = "brandId") brandId: Int): DataResponseDto<Any>

    @DELETE("/brand/{brandId}/like")
    suspend fun deleteBrandLike(@Path(value = "brandId") brandId: Int): DataResponseDto<Any>

    @FormUrlEncoded
    @POST("/brand/{brandId}/testSave")
    suspend fun postBrandTestSave(
        @Path(value = "brandId") brandId: Int, @Field("image") image: File
    ): DataResponseDto<Any> //임시?

    @FormUrlEncoded
    @POST("/brand/new")
    suspend fun postBrand(
        @Field("image") image: File,
        @Field("brandName") brandName: String,
        @Field("englishName") englishName: String
    ): DataResponseDto<Any>

    @GET("/brand/perfumes/{brandId}")
    suspend fun getPerfumesSortedChar(
        @Path(value = "brandId") brandId: Int,
        @Field("pageNum") pageNum: Int
    ): DataResponseDto<Any>

    @GET("/brand/perfumes/{brandId}/top")
    suspend fun getPerfumesSortedTop(
        @Path(value = "brandId") brandId: Int,
        @Field("pageNum") pageNum: Int
    ): DataResponseDto<Any>

    @GET("/brand/perfumes/{brandId}/update")
    suspend fun getPerfumesSortedUpdate(
        @Path(value = "brandId") brandId: Int,
        @Field("pageNum") pageNum: Int
    ): DataResponseDto<Any>
}