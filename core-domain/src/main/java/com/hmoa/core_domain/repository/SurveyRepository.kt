package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto

interface SurveyRepository {
    suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto>
    suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto>

    suspend fun getAllSurveyResult(): List<NoteResponseDto>
    suspend fun insertSurveryResult(note: NoteResponseDto)

    suspend fun updateSurveyResult(note: NoteResponseDto)
    suspend fun deleteSurveyResult(note: NoteResponseDto)
    suspend fun deleteAllNotes()
}