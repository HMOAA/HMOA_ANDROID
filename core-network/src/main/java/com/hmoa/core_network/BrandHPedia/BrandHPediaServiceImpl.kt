package corenetwork.BrandHPedia

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class BrandHPediaServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : corenetwork.BrandHPedia.BrandHPediaService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()
    val formUrlEncodedContentHttpClient = httpClientProvider.getHttpClientWithFormUrlEncodedHeader()

    override suspend fun getBrandStoryAll(pageNum: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/brandstory") {
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/brandstory/${brandStoryId}").body()
    }

    override suspend fun deleteBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/brandstory/${brandStoryId}").body()
    }

    override suspend fun updateBrandStory(
        brandStoryId: Int,
        content: String
    ): DataResponseDto<Any> {
        val response = jsonContentHttpClient.put("/brandstory/${brandStoryId}/update") {
            url.parameters.append("content", content)
        }
        return response.body()
    }

    override suspend fun postBrandStory(
        brandStorySubtitle: String,
        brandStoryTitle: String,
        content: String
    ): DataResponseDto<Any> {
        val parameter = Parameters.build {
            append("brandStorySubtitle", brandStorySubtitle)
            append("brandStoryTitle", brandStoryTitle)
            append("content", content)
        }
        val response = jsonContentHttpClient.post("/brandstory/new") {
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }

    override suspend fun postTestSave(): DataResponseDto<Any> {
        return jsonContentHttpClient.post("/brandstory/testSave").body()
    }
}