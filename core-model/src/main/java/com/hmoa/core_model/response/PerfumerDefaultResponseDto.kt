package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumerDefaultResponseDto(
    val perfumerId: Int,
    val perfumeSubTitle: String,
    val perfumerTitle: String
)
