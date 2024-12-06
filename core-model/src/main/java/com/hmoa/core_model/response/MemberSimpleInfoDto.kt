package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberSimpleInfoDto(
    val name: String,
    val phoneNumber: String
)
