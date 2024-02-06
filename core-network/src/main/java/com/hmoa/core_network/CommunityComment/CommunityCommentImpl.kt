package com.hmoa.core_network.CommunityComment

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class CommunityCommentImpl @Inject constructor(private val httpClient: HttpClient) : CommunityComment {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return httpClient.put("/community/comment/${commentId}") {
            url {
                body = dto
            }
        }.body()
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return httpClient.delete("/community/comment/${commentId}").body()
    }

    override suspend fun postCommunityComment(commentId: Int, page: String): CommunityCommentAllResponseDto {
        return httpClient.post("/community/comment/${commentId}/findAll") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    override suspend fun postCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return httpClient.post("/community/comment/${commentId}/save") {
            url {
                body = dto
            }
        }.body()
    }

}