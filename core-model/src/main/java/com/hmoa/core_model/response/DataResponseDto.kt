package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseDto<T>(
    val data: T
)
