package corenetwork.CommunityComment

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

internal class CommunityCommentServiceImpl @Inject constructor(private val httpClientProvider: HttpClientProvider) :
    CommunityCommentService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    @OptIn(InternalAPI::class)
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return jsonContentHttpClient.put("/community/comment/${commentId}") {
            url {
                body = dto
            }
        }.body()
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/community/comment/${commentId}").body()
    }

    override suspend fun getCommunityComments(commentId: Int, page: String): CommunityCommentAllResponseDto {
        return jsonContentHttpClient.get("/community/comment/${commentId}/findAll") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return jsonContentHttpClient.post("/community/comment/${commentId}/save") {
            url {
                body = dto
            }
        }.body()
    }

}