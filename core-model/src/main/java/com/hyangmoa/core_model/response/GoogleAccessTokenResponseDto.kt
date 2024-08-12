package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccessTokenResponseDto(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val token_type: String,
    val id_token: String
)