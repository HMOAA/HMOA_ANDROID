package com.hmoa.core_network.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import javax.inject.Inject

@OptIn(InternalAPI::class)
class AdminServiceImpl @Inject constructor(
    private val httpClient : HttpClient
): AdminService {
    override suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        val response = httpClient.delete("/admin/{homeMenuId}/delete"){
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
        val response = httpClient.get("/admin/${homeMenuId}/modify"){
            body = homeMenuSaveRequestDto
        }
        return response.body()
    }

    override suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        val response = httpClient.get("/admin/homePerfume"){
            body = dto
        }
        return response.body()
    }

    override suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        val parameter = Parameters.build{
            append("homeId", homeId.toString())
            append("perfumeId", perfumeId.toString())
        }
        val response = httpClient.get("/admin/homePerfume/add"){
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }
}