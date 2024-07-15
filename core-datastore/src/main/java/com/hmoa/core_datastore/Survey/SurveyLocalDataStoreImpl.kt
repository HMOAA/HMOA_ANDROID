package com.hmoa.core_datastore.Survey

import com.hmoa.core_database.room.NoteDao
import com.hmoa.core_datastore.mapToRoomDBNote
import com.hmoa.core_model.Note
import javax.inject.Inject

class SurveyLocalDataStoreImpl @Inject constructor(private val noteDao: NoteDao) : SurveyLocalDataStore {
    override suspend fun getAllSurveyResult(): List<com.hmoa.core_database.room.RoomNote> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertSurveyResult(note: Note) {

        noteDao.insert(mapToRoomDBNote(note))
    }

    override suspend fun updateSurveyResult(note: Note) {
        noteDao.update(mapToRoomDBNote(note))
    }

    override suspend fun deleteSurveyResult(note: Note) {
        noteDao.delete(mapToRoomDBNote(note))
    }
}