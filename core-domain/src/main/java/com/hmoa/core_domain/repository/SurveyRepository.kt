package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.Note
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto

interface SurveyRepository {
    suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto>
    suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto>

    suspend fun getAllSurveyResult(): List<Note>
    suspend fun insertSurveryResult(note: Note)

    suspend fun updateSurveyResult(note: Note)
    suspend fun deleteSurveyResult(note: Note)
}