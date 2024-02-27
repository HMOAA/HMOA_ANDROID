package corenetwork.Perfume

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

class PerfumeServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : PerfumeService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        return jsonContentHttpClient.get("/perfume/${perfumeId}").body()
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto {
        return jsonContentHttpClient.get("/perfume/${perfumeId}/2").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto {
        val response = jsonContentHttpClient.post("/perfume/${perfumeId}/age") {
            url {
                body = dto
            }
        }
        return response.body()
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return jsonContentHttpClient.delete("/perfume/${perfumeId}/age").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto {
        val response = jsonContentHttpClient.post("/perfume/${perfumeId}/gender") {
            url {
                body = dto
            }
        }
        return response.body()
    }

    override suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto {
        return jsonContentHttpClient.delete("/perfume/${perfumeId}/gender").body()
    }

    override suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return jsonContentHttpClient.put("/perfume/${perfumeId}/like").body()
    }

    override suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/perfume/${perfumeId}/like").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeWeather(
        perfumeId: String,
        dto: PerfumeWeatherRequestDto
    ): PerfumeWeatherResponseDto {
        return jsonContentHttpClient.post("/perfume/${perfumeId}/weather") {
            body = dto
        }.body()
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return jsonContentHttpClient.delete("/perfume/${perfumeId}/weather").body()
    }

    override suspend fun getLikePerfumes(): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/perfume/like").body()
    }
}