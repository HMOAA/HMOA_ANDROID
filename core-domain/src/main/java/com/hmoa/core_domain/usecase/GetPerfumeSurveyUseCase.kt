package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.NoteCategoryTag
import com.hmoa.core_model.data.PerfumeSurveyContents
import com.hmoa.core_model.response.PerfumeSurveyResponseDto
import javax.inject.Inject

class GetPerfumeSurveyUseCase @Inject constructor(private val surveyRepository: SurveyRepository) {
    suspend operator fun invoke(): ResultResponse<PerfumeSurveyContents> {
        val perfumeSurvey = surveyRepository.getPerfumeSurvey()
        var result = ResultResponse<PerfumeSurveyContents>(data = null, errorMessage = perfumeSurvey.errorMessage)

        if (perfumeSurvey.data != null) {
            result.data = mapToPerfumeSurveyContents(perfumeSurvey.data!!)
        }

        return result
    }

    fun mapToPerfumeSurveyContents(value: PerfumeSurveyResponseDto): PerfumeSurveyContents {
        return PerfumeSurveyContents(
            priceQuestionTitle = value.priceQuestion.content,
            priceQuestionOptions = value.priceQuestion.answers.map { it.option },
            priceQuestionOptionIds = value.priceQuestion.answers.map { it.optionId },
            noteQuestionTitle = value.noteQuestion.content,
            noteCategoryTags = value.noteQuestion.answer.map {
                NoteCategoryTag(category = it.category, note = it.notes, isSelected = List(it.notes.size){false})
            },
            isMultipleAnswerAvailable = value.priceQuestion.isMultipleChoice
        )
    }
}