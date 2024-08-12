package com.hyangmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccessTokenRequestDto(
    val code: String,
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String,
    val grant_type: String
)