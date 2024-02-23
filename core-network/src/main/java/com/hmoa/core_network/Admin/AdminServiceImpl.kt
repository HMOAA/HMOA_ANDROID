package corenetwork.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

@OptIn(InternalAPI::class)
class AdminServiceImpl constructor(
    private val httpClient: HttpClient
) : corenetwork.Admin.AdminService {
    override suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        val response = httpClient.delete("/admin/{homeMenuId}/delete") {
            url {
                parameters.append("homeMenuId", homeMenuId.toString())
            }
        }
        return response.body()
    }

    override suspend fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        val response = httpClient.get("/admin/${homeMenuId}/modify") {
            body = homeMenuSaveRequestDto
        }
        return response.body()
    }

    override suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        val response = httpClient.get("/admin/homePerfume") {
            body = dto
        }
        return response.body()
    }

    override suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        val parameter = Parameters.build {
            append("homeId", homeId.toString())
            append("perfumeId", perfumeId.toString())
        }
        val response = httpClient.get("/admin/homePerfume/add") {
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }
}