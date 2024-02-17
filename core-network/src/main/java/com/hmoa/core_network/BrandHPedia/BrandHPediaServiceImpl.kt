package com.hmoa.core_network.BrandHPedia

import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.Parameters
import javax.inject.Inject

class BrandHPediaServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : BrandHPediaService {

    override suspend fun getBrandStoryAll(pageNum: Int): DataResponseDto<Any> {
        val response = httpClient.get("/brandstory"){
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return httpClient.get("/brandstory/${brandStoryId}").body()
    }

    override suspend fun deleteBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return httpClient.delete("/brandstory/${brandStoryId}").body()
    }

    override suspend fun updateBrandStory(
        brandStoryId: Int,
        content: String
    ): DataResponseDto<Any> {
        val response = httpClient.put("/brandstory/${brandStoryId}/update"){
            url.parameters.append("content", content)
        }
        return response.body()
    }

    override suspend fun postBrandStory(
        brandStorySubtitle: String,
        brandStoryTitle: String,
        content: String
    ): DataResponseDto<Any> {
        val parameter = Parameters.build{
            append("brandStorySubtitle", brandStorySubtitle)
            append("brandStoryTitle", brandStoryTitle)
            append("content", content)
        }
        val response = httpClient.post("/brandstory/new"){
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }

    override suspend fun postTestSave(): DataResponseDto<Any> {
        return httpClient.post("/brandstory/testSave").body()
    }
}