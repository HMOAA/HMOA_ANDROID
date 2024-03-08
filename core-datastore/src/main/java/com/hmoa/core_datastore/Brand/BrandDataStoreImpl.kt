package com.hmoa.core_datastore.Brand

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.BrandService
import java.io.File
import javax.inject.Inject

class BrandDataStoreImpl @Inject constructor(
    private val brandService: BrandService
) : BrandDataStore {
    override suspend fun getBrand(brandId: Int): DataResponseDto<Any> {
        return brandService.getBrand(brandId)
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.putBrandLike(brandId)
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.deleteBrandLike(brandId)
    }

    override suspend fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        return brandService.postBrandTestSave(image, brandId)
    }

    override suspend fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        return brandService.postBrand(image, brandName, englishName)
    }

    override suspend fun getPerfumesSortedChar(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandService.getPerfumesSortedChar(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedTop(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandService.getPerfumesSortedTop(brandId, pageNum)
    }

    override suspend fun getPerfumesSortedUpdate(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandService.getPerfumesSortedUpdate(brandId, pageNum)
    }
}