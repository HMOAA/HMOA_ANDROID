package com.hmoa.core_network.Community

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.File
import javax.inject.Inject

internal class CommunityServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CommunityService {
    override suspend fun getCommunity(communityId: Int): CommunityDefaultResponseDto {
        return httpClient.get("/community/${communityId}").body()
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

        httpClient.config {
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            }
        }

        return httpClient.post("/community/${communityId}") {
            formData {
                formData
            }
        }.body()
    }

    override suspend fun deleteCommunity(communityId: Int): DataResponseDto<Any> {
        return httpClient.delete("/community/${communityId}").body()
    }

    override suspend fun getCommunityByCategory(category: Category, page: String): CommunityByCategoryResponseDto {
        return httpClient.get("/community/category") {
            url {
                parameters.append("category", category.name)
                parameters.append("page", page)
            }
        }.body()
    }

    override suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto> {
        return httpClient.get("/community/home").body()
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

        return httpClient.post("/community/save") {
            formData {
                formData
            }

        }.body()
    }
}