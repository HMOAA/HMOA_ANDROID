package com.hmoa.core_datastore.Survey

import ResultResponse
import com.hmoa.core_model.request.ContentRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.request.SurveySaveAnswerRequestDtos
import com.hmoa.core_model.request.SurveySaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.SurveyService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class SurveyRemoteDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val authenticator: Authenticator
) : SurveyRemoteDataStore {
    override suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto> {
        val result = ResultResponse<SurveyQuestionsResponseDto>()
        surveyService.getSurveyQuestions().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.getSurveyQuestions().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto> {
        val result = ResultResponse<RecommendNotesResponseDto>()
        surveyService.postSurveyResponds(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.postSurveyResponds(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun saveSurvey(dto: SurveySaveRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveSurvey(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.saveSurvey(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun saveAnswerNote(dto: SurveySaveAnswerRequestDtos): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveAnswerNote(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.saveAnswerNote(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun saveAnswerByQuestionId(
        dto: ContentRequestDto,
        questionId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveAnswerByQuestionId(dto, questionId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.saveAnswerByQuestionId(dto, questionId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun saveQuestionBySurveyId(
        dto: ContentRequestDto,
        surveyId: Int
    ): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        surveyService.saveQuestionBySurveyId(dto, surveyId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    surveyService.saveQuestionBySurveyId(dto, surveyId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}