package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Survey.SurveyLocalDataStore
import com.hmoa.core_datastore.Survey.SurveyRemoteDataStore
import com.hmoa.core_datastore.mapToNote
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.Note
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyRemoteDataStore: SurveyRemoteDataStore,
    private val surveyLocalDataStore: SurveyLocalDataStore
) : SurveyRepository {
    override suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto> {
        return surveyRemoteDataStore.getSurveyQuestions()
    }

    override suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto> {
        return surveyRemoteDataStore.postSurveyResponds(dto)
    }

    override suspend fun getAllSurveyResult(): List<Note> {
        return surveyLocalDataStore.getAllSurveyResult().map { mapToNote(it) }
    }

    override suspend fun insertSurveryResult(note: Note) {
        surveyLocalDataStore.insertSurveyResult(note)
    }

    override suspend fun updateSurveyResult(note: Note) {
        surveyLocalDataStore.updateSurveyResult(note)
    }

    override suspend fun deleteSurveyResult(note: Note) {
        surveyLocalDataStore.deleteSurveyResult(note)
    }

}