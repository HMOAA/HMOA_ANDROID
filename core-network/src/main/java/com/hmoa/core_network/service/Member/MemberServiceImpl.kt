package corenetwork.Member

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class MemberServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider,
    private val httpClient: HttpClient
) : MemberService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    val formUrlEncodedContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun getMember(): MemberResponseDto {
        return jsonContentHttpClient.get("/member").body()
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        val response = jsonContentHttpClient.patch("/member/age") {
            body = request
        }
        return response.body()
    }

    override suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        val response = jsonContentHttpClient.get("/member/communities") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun getCommunityComments(page: Int): List<CommunityCommentByMemberResponseDto> {
        val response = jsonContentHttpClient.get("/member/communityComments") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/member/delete").body()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): DataResponseDto<Boolean> {
        val response = httpClient.post("/member/existsnickname") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        val result = DataResponseDto<Boolean>
        return response.body()
    }

    override suspend fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        val response = jsonContentHttpClient.get("/member/hearts") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        val response = jsonContentHttpClient.patch("/member/join") {
            body = request
        }
        return response.body()
    }

    override suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        val response = jsonContentHttpClient.patch("/member/nickname") {
            body = request
        }
        return response.body()
    }

    override suspend fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        val response = jsonContentHttpClient.get("/member/perfumeComments") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun postProfilePhoto(image: String): DataResponseDto<Any> {
        val response = formUrlEncodedContentHttpClient.post("/member/profile-photo")
        return response.body()
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/member/profile-photo").body()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        val response = jsonContentHttpClient.patch("/member/sex") {
            body = request
        }
        return response.body()
    }
}