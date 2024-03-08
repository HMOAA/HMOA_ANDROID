package com.hmoa.core_network.service

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.core_network.HttpClientProvider
import com.hmoa.core_network.service.PerfumeCommentService
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

internal class PerfumeCommentServiceImpl @Inject constructor(private val httpClientProvider: HttpClientProvider) :
    PerfumeCommentService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    override suspend fun getPerfumeCommentsLatest(page: String, perfumeId: Int): PerfumeCommentGetResponseDto {
        return jsonContentHttpClient.get("/perfume/${perfumeId}/comments") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto {
        return jsonContentHttpClient.post("/perfume/${perfumeId}/comments") {
            url {
                body = dto
            }
        }.body()
    }

    override suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto {
        return jsonContentHttpClient.get("/perfume/${perfumeId}/comments/top") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    override suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/perfume/${commentId}").body()
    }

    override suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.put("/perfume/comments/${commentId}/like").body()
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/perfume/comments/${commentId}/like").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return jsonContentHttpClient.put("/perfume/comments/${commentId}/modify") {
            url {
                body = dto
            }
        }.body()
    }
}