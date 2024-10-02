package com.hmoa.core_datastore.Survey

import ResultResponse
import com.hmoa.core_database.lrucache.PerfumeRecommendCacheManager
import com.hmoa.core_database.room.NoteDao
import com.hmoa.core_datastore.mapToNote
import com.hmoa.core_datastore.mapToRoomDBNote
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto
import javax.inject.Inject

class SurveyLocalDataStoreImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val perfumeRecommendCacheManager: PerfumeRecommendCacheManager
) : SurveyLocalDataStore {
    override suspend fun getAllSurveyResult(): List<NoteResponseDto> {
        return noteDao.getAllNotes().map { mapToNote(it) }
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

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        perfumeRecommendCacheManager.saveNoteSortedPerfumeRecommendsResult(dto)
    }

    override fun getNoteSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto> {
        var result = ResultResponse<PerfumeRecommendsResponseDto>()
        val data = perfumeRecommendCacheManager.getNoteSortedPerfumeRecommendsResult()
        if (data != null) {
            result.data = data
        }
        return result
    }

    override fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        perfumeRecommendCacheManager.savePriceSortedPerfumeRecommendsResult(dto)
    }

    override fun getPriceSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto> {
        var result = ResultResponse<PerfumeRecommendsResponseDto>()
        val data = perfumeRecommendCacheManager.getPriceSortedPerfumeRecommendsResult()
        if (data != null) {
            result.data = data
        }
        return result
    }
}