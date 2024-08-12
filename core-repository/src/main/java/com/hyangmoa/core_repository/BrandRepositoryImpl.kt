package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Brand.BrandDataStore
import com.hyangmoa.core_model.response.BrandDefaultResponseDto
import com.hyangmoa.core_model.response.BrandPerfumeBriefPagingResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import java.io.File
import javax.inject.Inject

class BrandRepositoryImpl @Inject constructor(
    private val brandDataStore: BrandDataStore
) : com.hyangmoa.core_domain.repository.BrandRepository {

    override suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>> {
        return brandDataStore.getBrand(brandId)
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.putBrandLike(brandId)
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.deleteBrandLike(brandId)
    }

    override suspend fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        return brandDataStore.postBrandTestSave(image, brandId)
    }

    override suspend fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        return brandDataStore.postBrand(image, brandName, englishName)
    }

    override suspend fun getPerfumesSortedChar(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        return brandDataStore.getPerfumesSortedChar(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedLike(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        return brandDataStore.getPerfumesSortedLike(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedUpdate(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        return brandDataStore.getPerfumesSortedUpdate(brandId, pageNum)
    }
}