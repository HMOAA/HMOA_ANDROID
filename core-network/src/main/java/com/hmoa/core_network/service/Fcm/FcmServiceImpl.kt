package corenetwork.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class FcmServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : FcmService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun deleteFcmToken(): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/fcm/delete").body()
    }

    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any> {
        val response = jsonContentHttpClient.post("fcm/save") {
            body = fcmTokenSaveRequest
        }
        return response.body()
    }
}