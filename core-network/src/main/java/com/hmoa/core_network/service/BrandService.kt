package com.hmoa.core_network.service

import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefPagingResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface BrandService {
    @GET("/brand/{brandId}")
    suspend fun getBrand(@Path(value = "brandId") brandId: Int): ApiResponse<DataResponseDto<BrandDefaultResponseDto>>

    @PUT("/brand/{brandId}/like")
    suspend fun putBrandLike(@Path(value = "brandId") brandId: Int): DataResponseDto<Any>

    @DELETE("/brand/{brandId}/like")
    suspend fun deleteBrandLike(@Path(value = "brandId") brandId: Int): DataResponseDto<Any>

    @GET("/brand/perfumes/{brandId}")
    suspend fun getPerfumesSortedChar(
        @Path(value = "brandId") brandId: Int,
        @Field("pageNum") pageNum: Int
    ): ApiResponse<BrandPerfumeBriefPagingResponseDto>

    @GET("/brand/perfumes/{brandId}/top")
    suspend fun getPerfumesSortedLike(
        @Path(value = "brandId") brandId: Int,
        @Query("pageNum") pageNum: Int
    ): ApiResponse<BrandPerfumeBriefPagingResponseDto>

    @GET("/brand/perfumes/{brandId}/update")
    suspend fun getPerfumesSortedUpdate(
        @Path(value = "brandId") brandId: Int,
        @Query("pageNum") pageNum: Int
    ): ApiResponse<BrandPerfumeBriefPagingResponseDto>
}