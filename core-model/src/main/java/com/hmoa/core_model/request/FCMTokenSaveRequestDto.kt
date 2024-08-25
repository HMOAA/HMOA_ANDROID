package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class FCMTokenSaveRequestDto(
    val fcmtoken: String
)
