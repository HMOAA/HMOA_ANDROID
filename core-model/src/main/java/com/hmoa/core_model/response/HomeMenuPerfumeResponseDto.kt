package com.hmoa.core_model.response

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class HomeMenuPerfumeResponseDto(
    val brandName: String,
    val imgUrl: String,
    val perfumeId: Int,
    val perfumeName: String
)
