package corenetwork.Login

import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class LoginServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : LoginService {

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: String
    ): MemberLoginResponseDto {
        val response = httpClient.post("/login/oauth2/${provider}") {
            body = accessToken
        }
        return response.body()
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        val response = httpClient.post("/login/remembered") {
            body = dto
        }
        return response.body()
    }
}
