package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class HbtiHomeMetaDataResponse(
    val backgroundImgUrl: String,
    val firstImageUrl: String,
    val secondImageUrl: String,
    val isOrdered: Boolean
)
