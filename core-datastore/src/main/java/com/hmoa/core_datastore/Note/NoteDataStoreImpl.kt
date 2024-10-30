package com.hmoa.core_datastore.Note

import ResultResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.NoteDescResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.NoteService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class NoteDataStoreImpl @Inject constructor(
    private val noteService: NoteService,
    private val authenticator: Authenticator
) : NoteDataStore {
    override suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        return noteService.getNoteAll(pageNum)
    }

    override suspend fun getNote(noteId: Int): ResultResponse<DataResponseDto<NoteDescResponseDto>> {
        val result = ResultResponse<DataResponseDto<NoteDescResponseDto>>()
        noteService.getNote(noteId)
            .suspendOnSuccess {
                result.data = this.data
            }.suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        noteService.getNote(noteId).suspendOnSuccess { result.data = this.data }
                    }
                )
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