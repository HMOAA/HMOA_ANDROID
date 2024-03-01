package com.hmoa.core_domain.repository

import com.hmoa.core_model.response.DataResponseDto

interface NoteRepository {
    suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any>
    suspend fun getNote(noteId: Int): DataResponseDto<Any>
    suspend fun deleteNote(noteId: Int): DataResponseDto<Any>
    suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any>
    suspend fun postNote(content: String, noteSubtitle: String, noteTitle: String): DataResponseDto<Any>
}