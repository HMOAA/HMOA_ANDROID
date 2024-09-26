package com.hmoa.core_model.data

data class PerfumeSurveyContents(
    val priceQuestionTitle: String?,
    val priceQuestionOptions: List<String>?,
    val priceQuestionOptionIds: List<Int>?,
    val noteQuestionTitle: String?,
    val noteCategoryTags: List<NoteCategoryTag>?,
    val isPriceMultipleChoice: Boolean,
)

data class NoteCategoryTag(
    val category: String,
    val note: List<String>,
    val isSelected: List<Boolean>
)
