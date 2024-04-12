package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefResponseDto
import com.hmoa.core_model.response.DataResponseDto
import java.io.File

interface BrandRepository {
    suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>>
    suspend fun putBrandLike(brandId: Int): DataResponseDto<Any>
    suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any>
    suspend fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> //임시?
    suspend fun postBrand(image: File, brandName: String, englishName: String): DataResponseDto<Any>
    suspend fun getPerfumesSortedChar(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>

    suspend fun getPerfumesSortedLike(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>

    suspend fun getPerfumesSortedUpdate(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>
}