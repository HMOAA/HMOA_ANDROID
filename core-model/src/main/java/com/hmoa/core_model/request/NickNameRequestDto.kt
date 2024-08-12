package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class NickNameRequestDto(
    val nickname: String?
)
