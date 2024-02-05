package com.hmoa.core_repository.Note

import com.hmoa.core_datastore.Note.NoteDataStore
import com.hmoa.core_model.response.DataResponseDto

class NoteRepositoryImpl(
    private val noteDataStore: NoteDataStore
) : NoteRepository {

    override fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        return noteDataStore.getNoteAll(pageNum)
    }

    override fun getNote(noteId: Int): DataResponseDto<Any> {
        return noteDataStore.getNote(noteId)
    }

    override fun deleteNote(noteId: Int): DataResponseDto<Any> {
        return noteDataStore.deleteNote(noteId)
    }

    override fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        return noteDataStore.putNote(content, noteId)
    }

    override fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        return noteDataStore.postNote(content, noteSubtitle, noteTitle)
    }
}