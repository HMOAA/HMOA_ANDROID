package com.hmoa.core_network.service

import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyService {
    @GET("/survey/note")
    suspend fun getSurveyQuestions(): ApiResponse<SurveyQuestionsResponseDto>

    @POST("/survey/note/respond")
    suspend fun postSurveyResponds(@Body dto: SurveyRespondRequestDto): ApiResponse<RecommendNotesResponseDto>
}