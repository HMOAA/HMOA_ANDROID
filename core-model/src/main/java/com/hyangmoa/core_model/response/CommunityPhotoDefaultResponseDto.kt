package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityPhotoDefaultResponseDto(
    val photoId: Int,
    val photoUrl: String
)