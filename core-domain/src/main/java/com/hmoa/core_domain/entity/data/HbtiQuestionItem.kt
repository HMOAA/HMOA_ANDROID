package com.hmoa.core_domain.entity.data

data class HbtiQuestionItem(
    val questionId: Int,
    val questionContent:String,
    val optionIds: List<Int>,
    val optionContents: List<String>,
    val isMultipleChoice: Boolean,
    val selectedOptionIds: MutableList<Int>
)