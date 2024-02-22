package corenetwork.PerfumeComment

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*

internal class PerfumeCommentServiceImpl constructor(private val httpClient: HttpClient) :
    PerfumeCommentService {
    override suspend fun getPerfumeCommentsLatest(page: String, perfumeId: Int): PerfumeCommentGetResponseDto {
        return httpClient.get("/perfume/${perfumeId}/comments") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeComment(perfumeId: Int, dto: PerfumeCommentRequestDto): PerfumeCommentResponseDto {
        return httpClient.post("/perfume/${perfumeId}/comments") {
            url {
                body = dto
            }
        }.body()
    }

    override suspend fun getPerfumeCommentsLikest(page: String, perfumeId: String): PerfumeCommentGetResponseDto {
        return httpClient.get("/perfume/${perfumeId}/comments/top") {
            url {
                parameters.append("page", page)
            }
        }.body()
    }

    override suspend fun deletePerfumeComments(commentId: Int): DataResponseDto<Any> {
        return httpClient.get("/perfume/${commentId}").body()
    }

    override suspend fun putPerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return httpClient.put("/perfume/comments/${commentId}/like").body()
    }

    override suspend fun deletePerfumeCommentLike(commentId: Int): DataResponseDto<Any> {
        return httpClient.delete("/perfume/comments/${commentId}/like").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun putPerfumeCommentModify(
        commentId: Int,
        dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto {
        return httpClient.put("/perfume/comments/${commentId}/modify") {
            url {
                body = dto
            }
        }.body()
    }
}