package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.ContentRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.request.SurveySaveAnswerRequestDtos
import com.hmoa.core_model.request.SurveySaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto

interface SurveyRepository {
    suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto>
    suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto>
    suspend fun saveSurvey(dto : SurveySaveRequestDto): ResultResponse<DataResponseDto<Any>>
    suspend fun saveAnswerNote(dto: SurveySaveAnswerRequestDtos): ResultResponse<DataResponseDto<Any>>
    suspend fun saveAnswerByQuestionId(dto: ContentRequestDto, questionId: Int): ResultResponse<DataResponseDto<Any>>
    suspend fun saveQuestionBySurveyId(dto: ContentRequestDto, surveyId: Int): ResultResponse<DataResponseDto<Any>>
}