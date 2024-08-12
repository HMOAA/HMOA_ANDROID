package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumerDescResponseDto(
    val perfumerId : Int,
    val perfumerTitle : String,
    val perfumerSubTitle : String,
    val content : String
)
