package corenetwork.Member

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import java.io.File

@OptIn(InternalAPI::class)
class MemberServiceImpl constructor(
    private val httpClient: HttpClient
) : MemberService {

    override suspend fun getMember(): MemberResponseDto {
        return httpClient.get("/member").body()
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        val response = httpClient.patch("/member/age") {
            body = request
        }
        return response.body()
    }

    override suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        val response = httpClient.get("/member/communities") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun getCommunityComments(page: Int): List<CommunityCommentByMemberResponseDto> {
        val response = httpClient.get("/member/communityComments") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return httpClient.delete("/member/delete").body()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        val response = httpClient.post("/member/existsnickname") {
            body = request
        }
        return response.body()
    }

    override suspend fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        val response = httpClient.get("/member/hearts") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        val response = httpClient.patch("/member/join") {
            body = request
        }
        return response.body()
    }

    override suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        val response = httpClient.patch("/member/nickname") {
            body = request
        }
        return response.body()
    }

    override suspend fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        val response = httpClient.get("/member/perfumeComments") {
            url.parameters.append("page", page.toString())
        }
        return response.body()
    }

    override suspend fun postProfilePhoto(image: File): DataResponseDto<Any> {
        //image를 어떻게 올리고 있을 까요? 링크로? 아니면 binary 파일로 변환?
        val response = httpClient.post("/member/profile-photo")
        return response.body()
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return httpClient.delete("/member/profile-photo").body()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        val response = httpClient.patch("/member/sex") {
            body = request
        }
        return response.body()
    }
}