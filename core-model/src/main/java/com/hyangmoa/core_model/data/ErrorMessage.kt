package com.hyangmoa.core_model.data

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val code: String,
    val message: String
)
