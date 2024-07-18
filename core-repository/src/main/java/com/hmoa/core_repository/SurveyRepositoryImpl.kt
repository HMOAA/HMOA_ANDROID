package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Survey.SurveyDataStore
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.ContentRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.request.SurveySaveAnswerRequestDtos
import com.hmoa.core_model.request.SurveySaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
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
    override suspend fun saveSurvey(dto: SurveySaveRequestDto): ResultResponse<DataResponseDto<Any>> {
        return surveyDataStore.saveSurvey(dto)
    }
    override suspend fun saveAnswerNote(dto: SurveySaveAnswerRequestDtos): ResultResponse<DataResponseDto<Any>> {
        return surveyDataStore.saveAnswerNote(dto)
    }
    override suspend fun saveAnswerByQuestionId(
        dto: ContentRequestDto,
        questionId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        return surveyDataStore.saveAnswerByQuestionId(dto, questionId)
    }
    override suspend fun saveQuestionBySurveyId(
        dto: ContentRequestDto,
        surveyId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        return surveyDataStore.saveQuestionBySurveyId(dto, surveyId)
    }
}