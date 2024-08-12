package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumerDefaultResponseDto(
    val perfumerId: Int,
    val perfumerSubTitle: String,
    val perfumerTitle: String
)
