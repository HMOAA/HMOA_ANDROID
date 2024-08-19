package com.hmoa.core_model.data

typealias QuestionPageIndex = Int

data class HbtiQuestionItems(
    val hbtiQuestions: MutableMap<QuestionPageIndex, HbtiQuestionItem>
)
