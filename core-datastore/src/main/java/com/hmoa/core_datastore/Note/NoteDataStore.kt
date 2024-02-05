package com.hmoa.core_datastore.Note

import com.hmoa.core_model.response.DataResponseDto

interface NoteDataStore {
    fun getNoteAll(pageNum : Int) : DataResponseDto<Any>
    fun getNote(noteId : Int) : DataResponseDto<Any>
    fun deleteNote(noteId : Int) : DataResponseDto<Any>
    fun putNote(content : String, noteId : Int) : DataResponseDto<Any>
    fun postNote(content : String, noteSubtitle : String, noteTitle : String) : DataResponseDto<Any>
}