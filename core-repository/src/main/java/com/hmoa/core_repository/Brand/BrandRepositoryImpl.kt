package com.hmoa.core_repository.Brand

import com.hmoa.core_datastore.Brand.BrandDataStore
import com.hmoa.core_model.response.DataResponseDto
import java.io.File

class BrandRepositoryImpl(
    private val brandDataStore: BrandDataStore
) : BrandRepository {

    override fun getBrand(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.getBrand(brandId)
    }

    override fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.putBrandLike(brandId)
    }

    override fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandDataStore.deleteBrandLike(brandId)
    }

    override fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        return brandDataStore.postBrandTestSave(image, brandId)
    }

    override fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        return brandDataStore.postBrand(image, brandName, englishName)
    }

    override fun getPerfumesSortedChar(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedChar(brandId, pageNum)
    }

    override fun getPerfumesSortedTop(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedTop(brandId, pageNum)
    }

    override fun getPerfumesSortedUpdate(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        return brandDataStore.getPerfumesSortedUpdate(brandId, pageNum)
    }
}