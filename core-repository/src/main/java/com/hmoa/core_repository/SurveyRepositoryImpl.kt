package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Survey.SurveyDataStore
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(private val surveyDataStore: SurveyDataStore) : SurveyRepository {
    override suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto> {
        return surveyDataStore.getSurveyQuestions()
    }

    override suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto> {
        return surveyDataStore.postSurveyResponds(dto)
    }

}