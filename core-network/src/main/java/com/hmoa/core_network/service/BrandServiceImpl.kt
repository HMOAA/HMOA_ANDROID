package com.hmoa.core_network.service

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.HttpClientProvider
import com.hmoa.core_network.service.BrandService
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.io.File
import javax.inject.Inject

class BrandServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : BrandService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    val formUrlEncodedContentHttpClient = httpClientProvider.getHttpClientWithFormUrlEncodedHeader()

    override suspend fun getBrand(brandId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/brand/${brandId}").body()
    }

    override suspend fun putBrandLike(brandId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.put("/brand/${brandId}/like").body()
    }

    override suspend fun deleteBrandLike(brandId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/brand/${brandId}/like").body()
    }

    /** 미해결 */
    override suspend fun postBrandTestSave(image: File, brandId: Int): DataResponseDto<Any> {
        val response = formUrlEncodedContentHttpClient.post("/brand/${brandId}/testSave")
        return response.body()
    }

    /** 미해결 */
    override suspend fun postBrand(
        image: File,
        brandName: String,
        englishName: String
    ): DataResponseDto<Any> {
        val response = formUrlEncodedContentHttpClient.post("/brand/new")
        return response.body()
    }

    override suspend fun getPerfumesSortedChar(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/brand/perfumes/${brandId}") {
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getPerfumesSortedTop(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/brand/perfumes/${brandId}/top") {
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getPerfumesSortedUpdate(brandId: Int, pageNum: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/brand/perfumes/${brandId}/update") {
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }
}