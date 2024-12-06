package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Survey.SurveyLocalDataStore
import com.hmoa.core_datastore.Survey.SurveyRemoteDataStore
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.PerfumeRecommendType
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.request.PerfumeSurveyAnswerRequestDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.*
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

    override suspend fun getAllSurveyResult(): List<NoteResponseDto> {
        return surveyLocalDataStore.getAllSurveyResult()
    }

    override suspend fun insertSurveryResult(note: NoteResponseDto) {
        surveyLocalDataStore.insertSurveyResult(note)
    }

    override suspend fun updateSurveyResult(note: NoteResponseDto) {
        surveyLocalDataStore.updateSurveyResult(note)
    }

    override suspend fun deleteSurveyResult(note: NoteResponseDto) {
        surveyLocalDataStore.deleteSurveyResult(note)
    }

    override suspend fun deleteAllNotes() {
        surveyLocalDataStore.deleteAllNotes()
    }

    override suspend fun getPerfumeSurvey(): ResultResponse<PerfumeSurveyResponseDto> {
        return surveyRemoteDataStore.getPerfumeSurvey()
    }

    override suspend fun postPerfumeSurveyAnswers(
        dto: PerfumeSurveyAnswerRequestDto,
        recommendType: PerfumeRecommendType
    ): ResultResponse<PerfumeRecommendsResponseDto> {
        return surveyRemoteDataStore.postPerfumeSurveyAnswers(dto, recommendType)
    }

    override fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        surveyLocalDataStore.saveNoteSortedPerfumeRecommendsResult(dto)
    }

    override fun getNoteSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto> {
        return surveyLocalDataStore.getNoteSortedPerfumeRecommendsResult()
    }

    override fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        surveyLocalDataStore.savePriceSortedPerfumeRecommendsResult(dto)
    }

    override fun getPriceSortedPerfumeRecommendsResult(): ResultResponse<PerfumeRecommendsResponseDto> {
        return surveyLocalDataStore.getPriceSortedPerfumeRecommendsResult()
    }

    override suspend fun getHbtiHomeMetaDataResult(): ResultResponse<HbtiHomeMetaDataResponse> {
        return surveyRemoteDataStore.getHbtiHomeMetaDataResult()
    }
}