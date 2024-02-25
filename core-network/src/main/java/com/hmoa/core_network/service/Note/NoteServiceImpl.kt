package corenetwork.Note

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class NoteServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : NoteService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.get("/note") {
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getNote(noteId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.get("/note/${noteId}").body()
    }

    override suspend fun deleteNote(noteId: Int): DataResponseDto<Any> {
        return jsonContentHttpClient.delete("/note/${noteId}").body()
    }

    override suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        val response = jsonContentHttpClient.put("/note/${noteId}/update") {
            url.parameters.append("content", content)
        }
        return response.body()
    }

    override suspend fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        val parameter = Parameters.build {
            append("content", content)
            append("noteSubtitle", noteSubtitle)
            append("noteTitle", noteTitle)
        }
        val response = jsonContentHttpClient.post("/note/new") {
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }
}