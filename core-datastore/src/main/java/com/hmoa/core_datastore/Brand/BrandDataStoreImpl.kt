package com.hmoa.core_datastore.Brand

import ResultResponse
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefPagingResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.BrandService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class BrandDataStoreImpl @Inject constructor(
    private val brandService: BrandService,
    private val authenticator: Authenticator
) : BrandDataStore {
    override suspend fun getBrand(brandId: Int): ResultResponse<DataResponseDto<BrandDefaultResponseDto>> {
        val result = ResultResponse<DataResponseDto<BrandDefaultResponseDto>>()
        brandService.getBrand(brandId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    brandService.getBrand(brandId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.putBrandLike(brandId)
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return brandService.deleteBrandLike(brandId)
    }

    override suspend fun getPerfumesSortedChar(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        val result = ResultResponse<BrandPerfumeBriefPagingResponseDto>()
        brandService.getPerfumesSortedChar(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    brandService.getPerfumesSortedChar(brandId, pageNum).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getPerfumesSortedLike(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        val result = ResultResponse<BrandPerfumeBriefPagingResponseDto>()
        brandService.getPerfumesSortedLike(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    brandService.getPerfumesSortedLike(brandId, pageNum).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getPerfumesSortedUpdate(
        brandId: Int,
        pageNum: Int
    ): ResultResponse<BrandPerfumeBriefPagingResponseDto> {
        val result = ResultResponse<BrandPerfumeBriefPagingResponseDto>()
        brandService.getPerfumesSortedUpdate(brandId, pageNum).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    brandService.getPerfumesSortedUpdate(brandId, pageNum).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}