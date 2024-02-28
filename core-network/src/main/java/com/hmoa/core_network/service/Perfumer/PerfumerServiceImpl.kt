package corenetwork.Perfumer

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

internal class PerfumerServiceImpl @Inject constructor(private val httpClientProvider: HttpClientProvider) :
    PerfumerService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/perfumer") {
            url {
                parameters.append("pageNum", pageNum)
            }
        }.body()
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/perfumer/${perfumerId}").body()
    }
}