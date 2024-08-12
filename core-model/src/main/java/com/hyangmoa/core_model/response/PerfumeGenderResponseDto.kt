package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeGenderResponseDto(
    val man: Int,
    val neuter: Int,
    val selected: Int,
    val woman: Int,
    val writed: Boolean
)
