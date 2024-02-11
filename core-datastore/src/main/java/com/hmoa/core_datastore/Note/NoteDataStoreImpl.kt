package com.hmoa.core_datastore.Note

import com.hmoa.core_model.response.DataResponseDto

private class NoteDataStoreImpl : NoteDataStore {
    override fun getNoteAll(pageNum: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getNote(noteId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun deleteNote(noteId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun putNote(content: String, noteId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postNote(
        content: String,
        noteSubtitle: String,
        noteTitle: String
    ): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }
}