package com.hmoa.core_model.response

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class HomeMenuDefaultResponseDto(
    val perfumeList: ImmutableList<HomeMenuPerfumeResponseDto>,
    val title: String
)
