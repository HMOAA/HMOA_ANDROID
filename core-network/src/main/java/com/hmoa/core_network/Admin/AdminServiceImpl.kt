package corenetwork.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class AdminServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : corenetwork.Admin.AdminService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.delete("/admin/${homeMenuId}/delete")
        return response.body()
    }

    override suspend fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/admin/${homeMenuId}/modify") {
            body = homeMenuSaveRequestDto
        }
        return response.body()
    }

    override suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/admin/homePerfume") {
            body = dto
        }
        return response.body()
    }

    override suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        val parameter = Parameters.build {
            append("homeId", homeId.toString())
            append("perfumeId", perfumeId.toString())
        }
        val response = jsonContentHttpClient.get("/admin/homePerfume/add") {
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }
}