package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseDto<T>(
    val data: T
)
