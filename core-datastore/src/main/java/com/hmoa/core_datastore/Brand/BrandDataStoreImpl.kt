package com.hmoa.core_datastore.Brand

import ResultResponse
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.BrandService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import java.io.File
import javax.inject.Inject

class BrandDataStoreImpl @Inject constructor(
    private val brandService: BrandService
) : BrandDataStore {
    override suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>> {
        val result = ResultResponse<DataResponseDto<BrandDefaultResponseDto>>()
        brandService.getBrand(brandId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.putBrandLike(brandId)
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.deleteBrandLike(brandId)
    }

    override suspend fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        return brandService.postBrandTestSave(image = image, brandId = brandId)
    }

    override suspend fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        return brandService.postBrand(image, brandName, englishName)
    }

    override suspend fun getPerfumesSortedChar(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>> {
        val result = ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>()
        brandService.getPerfumesSortedChar(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getPerfumesSortedLike(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>> {
        val result = ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>()
        brandService.getPerfumesSortedLike(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getPerfumesSortedUpdate(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>> {
        val result = ResultResponse<DataResponseDto<BrandPerfumeBriefResponseDto>>()
        brandService.getPerfumesSortedUpdate(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }
}