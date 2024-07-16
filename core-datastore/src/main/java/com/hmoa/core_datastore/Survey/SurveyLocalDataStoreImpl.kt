package com.hmoa.core_datastore.Survey

import com.hmoa.core_database.room.NoteDao
import com.hmoa.core_datastore.mapToRoomDBNote
import com.hmoa.core_model.request.NoteResponseDto
import javax.inject.Inject

class SurveyLocalDataStoreImpl @Inject constructor(private val noteDao: NoteDao) : SurveyLocalDataStore {
    override suspend fun getAllSurveyResult(): List<com.hmoa.core_database.room.RoomNote> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertSurveyResult(note: NoteResponseDto) {

        noteDao.insert(mapToRoomDBNote(note))
    }

    override suspend fun updateSurveyResult(note: NoteResponseDto) {
        noteDao.update(mapToRoomDBNote(note))
    }

    override suspend fun deleteSurveyResult(note: NoteResponseDto) {
        noteDao.delete(mapToRoomDBNote(note))
    }
}