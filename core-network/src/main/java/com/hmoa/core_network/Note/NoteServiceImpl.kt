package com.hmoa.core_network.Note

import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.Parameters
import javax.inject.Inject

class NoteServiceImpl @Inject constructor(
    private val httpClient : HttpClient
) : NoteService {

    override suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        val response = httpClient.get("/note"){
            url.parameters.append("pageNum", pageNum.toString())
        }
        return response.body()
    }

    override suspend fun getNote(noteId: Int): DataResponseDto<Any> {
        return httpClient.get("/note/${noteId}").body()
    }

    override suspend fun deleteNote(noteId: Int): DataResponseDto<Any> {
        return httpClient.delete("/note/${noteId}").body()
    }

    override suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        val response = httpClient.put("/note/${noteId}/update"){
            url.parameters.append("content", content)
        }
        return response.body()
    }

    override suspend fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        val parameter = Parameters.build{
            append("content", content)
            append("noteSubtitle", noteSubtitle)
            append("noteTitle", noteTitle)
        }
        val response = httpClient.post("/note/new"){
            url.parameters.appendAll(parameter)
        }
        return response.body()
    }
}