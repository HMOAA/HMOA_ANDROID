package com.hmoa.core_network.service

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import java.io.File
import javax.inject.Inject

internal class CommunityServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : CommunityService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    val formUrlEncodedContentHttpClient = httpClientProvider.getHttpClientWithFormUrlEncodedHeader()
    override suspend fun getCommunity(communityId: Int): CommunityDefaultResponseDto {
        return jsonContentHttpClient.get("/community/${communityId}").body()
    }

    override suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): CommunityDefaultResponseDto {
        val formData = listOf(
            "image" to images,
            "deleteCommunityPhotoIds" to deleteCommunityPhotoIds,
            "title" to title,
            "content" to content
        )

        return formUrlEncodedContentHttpClient.post("/community/${communityId}") {
            formData {
                formData
            }
        }.body()
    }

    override suspend fun deleteCommunity(communityId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/community/${communityId}").body()
    }

    override suspend fun getCommunityByCategory(category: Category, page: String): CommunityByCategoryResponseDto {
        return jsonContentHttpClient.get("/community/category") {
            url {
                parameters.append("category", category.name)
                parameters.append("page", page)
            }
        }.body()
    }

    override suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto> {
        return jsonContentHttpClient.get("/community/home").body()
    }

    override suspend fun postCommunitySave(
        images: Array<File>,
        category: Category,
        title: String,
        content: String
    ): CommunityDefaultResponseDto {
        val formData = listOf(
            "image" to images,
            "category" to category.name,
            "title" to title,
            "content" to content
        )

        return formUrlEncodedContentHttpClient.post("/community/save") {
            formData {
                formData
            }

        }.body()
    }
}