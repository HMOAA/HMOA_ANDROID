package com.hmoa.core_model.data

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val code: Int,
    val message: String
)
