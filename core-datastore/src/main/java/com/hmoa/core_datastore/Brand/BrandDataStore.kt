package com.hmoa.core_datastore.Brand

import ResultResponse
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefPagingResponseDto
import com.hmoa.core_model.response.DataResponseDto

interface BrandDataStore {
    suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>>
    suspend fun putBrandLike(brandId: Int): DataResponseDto<Any>
    suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any>
    suspend fun getPerfumesSortedChar(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto>

    suspend fun getPerfumesSortedLike(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto>

    suspend fun getPerfumesSortedUpdate(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto>
}