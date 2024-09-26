package com.hmoa.core_network.service

import com.hmoa.core_model.request.*
import com.hmoa.core_model.response.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SurveyService {
    @GET("/survey/note")
    suspend fun getSurveyQuestions(): ApiResponse<SurveyQuestionsResponseDto>

    @POST("/survey/note/respond")
    suspend fun postSurveyResponds(@Body dto: SurveyRespondRequestDto): ApiResponse<RecommendNotesResponseDto>

    @POST("/survey/save")
    suspend fun saveSurvey(@Body dto: SurveySaveRequestDto): ApiResponse<DataResponseDto<Any>>

    @POST("/survey/save-answer-note")
    suspend fun saveAnswerNote(@Body dto: SurveySaveAnswerRequestDtos): ApiResponse<DataResponseDto<Any>>

    @POST("/survey/save-answer/{questionId")
    suspend fun saveAnswerByQuestionId(
        @Body dto: ContentRequestDto,
        @Path("questionId") questionId: Int,
    ): ApiResponse<DataResponseDto<Any>>

    @POST("/survey/save-question/{surveyId}")
    suspend fun saveQuestionBySurveyId(
        @Body dto: ContentRequestDto,
        @Path("surveyId") surveyId: Int
    ): ApiResponse<DataResponseDto<Any>>

    @GET("/survey/perfume")
    suspend fun getPerfumeSurvey(): ApiResponse<PerfumeSurveyResponseDto>

    @POST("/survey/perfume/respond")
    suspend fun postPerfumeSurveyAnswer(
        @Body dto: PerfumeSurveyAnswerRequestDto,
        @Path("isContainAll") isContainAll: Boolean
    ): ApiResponse<PerfumeRecommendsResponseDto>
}