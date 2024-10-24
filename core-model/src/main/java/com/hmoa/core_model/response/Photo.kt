package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val photoId: Int,
    val photoUrl: String
)
