package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Brand.BrandDataStore
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefPagingResponseDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class BrandRepositoryImpl @Inject constructor(
    private val brandDataStore: BrandDataStore
) : com.hmoa.core_domain.repository.BrandRepository {

    override suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>> {
        return brandDataStore.getBrand(brandId)
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.putBrandLike(brandId)
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.deleteBrandLike(brandId)
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