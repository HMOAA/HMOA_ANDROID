package com.hmoa.core_datastore.Survey

import ResultResponse
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto


interface SurveyLocalDataStore {
    suspend fun getAllSurveyResult(): List<NoteResponseDto>
    suspend fun insertSurveyResult(note: NoteResponseDto)

    suspend fun updateSurveyResult(note: NoteResponseDto)
    suspend fun deleteSurveyResult(note: NoteResponseDto)
    suspend fun deleteAllNotes()
    fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getNoteSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto>

    fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getPriceSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto>
}