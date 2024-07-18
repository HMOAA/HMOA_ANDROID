package com.hmoa.core_datastore.Survey

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.ContentRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.request.SurveySaveAnswerRequestDtos
import com.hmoa.core_model.request.SurveySaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import com.hmoa.core_network.service.SurveyService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(private val surveyService: SurveyService) : SurveyDataStore {
    override suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto> {
        val result = ResultResponse<SurveyQuestionsResponseDto>()
        surveyService.getSurveyQuestions().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto> {
        val result = ResultResponse<RecommendNotesResponseDto>()
        surveyService.postSurveyResponds(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun saveSurvey(dto: SurveySaveRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveSurvey(dto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun saveAnswerNote(dto: SurveySaveAnswerRequestDtos): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveAnswerNote(dto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun saveAnswerByQuestionId(
        dto: ContentRequestDto,
        questionId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveAnswerByQuestionId(dto, questionId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun saveQuestionBySurveyId(
        dto: ContentRequestDto,
        surveyId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveQuestionBySurveyId(dto, surveyId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
}