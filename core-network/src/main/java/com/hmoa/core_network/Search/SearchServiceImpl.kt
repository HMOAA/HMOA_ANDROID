package corenetwork.Search

import com.hmoa.core_model.response.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class SearchServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : SearchService {

    override suspend fun getBrand(searchWord: String): BrandSearchResponseDto {
        val response = httpClient.get("/search/brand") {
            url {
                parameters.append("searchWord", searchWord)
            }
        }
        return response.body()
    }

    override suspend fun getBrandAll(consonant: Int): List<BrandDefaultResponseDto> {
        val response = httpClient.get("/search/brandAll") {
            url {
                parameters.append("consonant", consonant.toString())
            }
        }
        return response.body()
    }

    override suspend fun getBrandStory(
        page: Int,
        searchWord: String
    ): List<BrandStoryDefaultResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/brandStory") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getCommunity(
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/community") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        val parameter = Parameters.build {
            append("category", category)
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/community/category") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/note") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/perfume") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getPerfumeName(
        page: Int,
        searchWord: String
    ): List<PerfumeNameSearchResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/perfumeName") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getPerfumer(
        page: Int,
        searchWord: String
    ): List<PerfumerDefaultResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/perfumer") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }

    override suspend fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto> {
        val parameter = Parameters.build {
            append("page", page.toString())
            append("searchWord", searchWord)
        }
        val response = httpClient.get("/search/term") {
            url {
                parameters.appendAll(parameter)
            }
        }
        return response.body()
    }
}