package com.hmoa.core_datastore.Brand

import com.hmoa.core_model.response.DataResponseDto
import java.io.File

private class BrandDataStoreImpl : BrandDataStore {
    override fun getBrand(brandId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getPerfumesSortedChar(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getPerfumesSortedTop(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getPerfumesSortedUpdate(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }
}