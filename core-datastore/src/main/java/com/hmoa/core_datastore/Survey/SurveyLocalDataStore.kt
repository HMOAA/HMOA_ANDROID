package com.hmoa.core_datastore.Survey

import com.hmoa.core_model.request.NoteResponseDto


interface SurveyLocalDataStore {
    suspend fun getAllSurveyResult(): List<com.hmoa.core_database.room.RoomNote>
    suspend fun insertSurveyResult(note: NoteResponseDto)

    suspend fun updateSurveyResult(note: NoteResponseDto)
    suspend fun deleteSurveyResult(note: NoteResponseDto)
}