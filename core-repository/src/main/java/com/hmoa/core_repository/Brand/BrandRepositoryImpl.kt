package com.hmoa.core_repository.Brand

import com.hmoa.core_datastore.Brand.BrandDataStore
import com.hmoa.core_model.response.DataResponseDto
import java.io.File

class BrandRepositoryImpl(
    private val brandDataStore: BrandDataStore
) : BrandRepository {

    override suspend fun getBrand(brandId: Int): DataResponseDto<Any> {
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

    override suspend fun getPerfumesSortedChar(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedChar(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedTop(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedTop(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedUpdate(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedUpdate(brandId, pageNum)
    }
}