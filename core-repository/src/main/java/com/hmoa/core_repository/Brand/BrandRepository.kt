package com.hmoa.core_repository.Brand

import com.hmoa.core_model.response.DataResponseDto
import java.io.File

interface BrandRepository {
    fun getBrand(brandId : Int) : DataResponseDto<Any>
    fun putBrandLike(brandId : Int) : DataResponseDto<Any>
    fun deleteBrandLike(brandId : Int) : DataResponseDto<Any>
    fun postBrandTestSave(image : File, brandId : Int) : DataResponseDto<Any> //임시?
    fun postBrand(image : File, brandName : String, englishName : String) : DataResponseDto<Any>
    fun getPerfumesSortedChar(brandId : Int, pageNum : Int) : DataResponseDto<Any>
    fun getPerfumesSortedTop(brandId: Int, pageNum: Int) : DataResponseDto<Any>
    fun getPerfumesSortedUpdate(brandId : Int, pageNum : Int) : DataResponseDto<Any>
}