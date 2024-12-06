package com.hmoa.core_domain.entity.data

typealias QuestionPageIndex = Int

data class HbtiQuestionItems(
    val hbtiQuestions: MutableMap<QuestionPageIndex, HbtiQuestionItem>,
    val questionCounts: Int
)
