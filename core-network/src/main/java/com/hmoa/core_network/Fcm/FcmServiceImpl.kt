package corenetwork.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class FcmServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : FcmService {

    override suspend fun deleteFcmToken(): DataResponseDto<Any> {
        return httpClient.delete("/fcm/delete").body()
    }

    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any> {
        val response = httpClient.post("fcm/save") {
            body = fcmTokenSaveRequest
        }
        return response.body()
    }
}