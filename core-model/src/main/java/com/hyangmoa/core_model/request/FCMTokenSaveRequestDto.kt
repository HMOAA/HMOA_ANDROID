package com.hyangmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class FCMTokenSaveRequestDto(
    val fcmToken: String
)
