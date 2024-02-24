package corenetwork.Main

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import com.hmoa.core_network.service.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class MainServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : MainService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun getFirst(): HomeMenuFirstResponseDto {
        return jsonContentHttpClient.get("/main/first").body()
    }

    override suspend fun getFirstMenu(): HomeMenuAllResponseDto {
        return jsonContentHttpClient.get("/main/firstMenu").body()
    }

    override suspend fun getSecond(): HomeMenuDefaultResponseDto {
        return jsonContentHttpClient.get("/main/second").body()
    }

    override suspend fun getSecondMenu(): List<HomeMenuAllResponseDto> {
        return jsonContentHttpClient.get("/main/secondMenu").body()
    }

    override suspend fun getThirdMenu(): List<HomeMenuAllResponseDto> {
        return jsonContentHttpClient.get("/main/thirdMenu").body()
    }
}