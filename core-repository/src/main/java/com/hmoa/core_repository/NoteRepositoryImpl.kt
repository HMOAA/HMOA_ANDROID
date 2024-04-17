package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Note.NoteDataStore
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.NoteDescResponseDto
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDataStore: NoteDataStore
) : com.hmoa.core_domain.repository.NoteRepository {

    override suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        return noteDataStore.getNoteAll(pageNum)
    }

    override suspend fun getNote(noteId: Int): ResultResponse<DataResponseDto<NoteDescResponseDto>> {
        return noteDataStore.getNote(noteId)
    }

    override suspend fun deleteNote(noteId: Int): DataResponseDto<Any> {
        return noteDataStore.deleteNote(noteId)
    }

    override suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        return noteDataStore.putNote(content, noteId)
    }

    override suspend fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        return noteDataStore.postNote(content, noteSubtitle, noteTitle)
    }
}