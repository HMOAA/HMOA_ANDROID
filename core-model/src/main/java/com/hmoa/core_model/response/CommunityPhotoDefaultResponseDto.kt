package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityPhotoDefaultResponseDto(
    val photoId: Int,
    val photoUrl: String
)