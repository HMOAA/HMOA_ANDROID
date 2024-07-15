package com.hmoa.core_datastore.Survey

import com.hmoa.core_model.Note


interface SurveyLocalDataStore {
    suspend fun getAllSurveyResult(): List<com.hmoa.core_database.room.RoomNote>
    suspend fun insertSurveyResult(note: Note)

    suspend fun updateSurveyResult(note: Note)
    suspend fun deleteSurveyResult(note: Note)
}