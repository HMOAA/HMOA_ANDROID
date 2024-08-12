package com.hyangmoa.core_datastore.Note

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.NoteDescResponseDto
import com.hyangmoa.core_network.service.NoteService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NoteDataStoreImpl @Inject constructor(
    private val noteService: NoteService
) : NoteDataStore {
    override suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        return noteService.getNoteAll(pageNum)
    }

    override suspend fun getNote(noteId: Int): ResultResponse<DataResponseDto<NoteDescResponseDto>> {
        val result = ResultResponse<DataResponseDto<NoteDescResponseDto>>()
        noteService.getNote(noteId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deleteNote(noteId: Int): DataResponseDto<Any> {
        return noteService.deleteNote(noteId)
    }

    override suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        return noteService.putNote(content, noteId)
    }

    override suspend fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        return noteService.postNote(content, noteSubtitle, noteTitle)
    }
}