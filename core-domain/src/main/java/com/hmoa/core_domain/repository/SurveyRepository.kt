package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.PerfumeRecommendType
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.request.PerfumeSurveyAnswerRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.*

interface SurveyRepository {
    suspend fun getSurveyQuestions(): ResultResponse<SurveyQuestionsResponseDto>
    suspend fun postSurveyResponds(dto: SurveyRespondRequestDto): ResultResponse<RecommendNotesResponseDto>

    suspend fun getAllSurveyResult(): List<NoteResponseDto>
    suspend fun insertSurveryResult(note: NoteResponseDto)

    suspend fun updateSurveyResult(note: NoteResponseDto)
    suspend fun deleteSurveyResult(note: NoteResponseDto)
    suspend fun deleteAllNotes()
    suspend fun getPerfumeSurvey(): ResultResponse<PerfumeSurveyResponseDto>

    suspend fun postPerfumeSurveyAnswers(
        dto: PerfumeSurveyAnswerRequestDto,
        recommendType: PerfumeRecommendType
    ): ResultResponse<PerfumeRecommendsResponseDto>

    fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getNoteSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto>

    fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getPriceSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto>
    suspend fun getHbtiHomeMetaDataResult(): ResultResponse<HbtiHomeMetaDataResponse>
}