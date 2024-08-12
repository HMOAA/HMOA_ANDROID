package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.NoteDescResponseDto

interface NoteRepository {
    suspend fun getNoteAll(pageNum: Int): DataResponseDto<Any>
    suspend fun getNote(noteId: Int): ResultResponse<DataResponseDto<NoteDescResponseDto>>
    suspend fun deleteNote(noteId: Int): DataResponseDto<Any>
    suspend fun putNote(content: String, noteId: Int): DataResponseDto<Any>
    suspend fun postNote(content: String, noteSubtitle: String, noteTitle: String): DataResponseDto<Any>
}