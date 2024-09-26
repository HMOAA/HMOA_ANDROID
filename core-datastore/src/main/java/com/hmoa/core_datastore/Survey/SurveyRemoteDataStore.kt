package com.hmoa.core_datastore.Survey

import ResultResponse
import com.hmoa.core_model.request.*
import com.hmoa.core_model.response.*

interface SurveyRemoteDataStore {
    suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto>
    suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto>
    suspend fun saveSurvey(dto: SurveySaveRequestDto): ResultResponse<DataResponseDto<Any>>
    suspend fun saveAnswerNote(dto: SurveySaveAnswerRequestDtos): ResultResponse<DataResponseDto<Any>>
    suspend fun saveAnswerByQuestionId(dto: ContentRequestDto, questionId: Int): ResultResponse<DataResponseDto<Any>>
    suspend fun saveQuestionBySurveyId(dto: ContentRequestDto, surveyId: Int): ResultResponse<DataResponseDto<Any>>

    suspend fun getPerfumeSurvey(): ResultResponse<PerfumeSurveyResponseDto>
    suspend fun postPerfumeSurveyAnswers(
        dto: PerfumeSurveyAnswerRequestDto,
        isContainAll: Boolean
    ): ResultResponse<PerfumeRecommendsResponseDto>
}