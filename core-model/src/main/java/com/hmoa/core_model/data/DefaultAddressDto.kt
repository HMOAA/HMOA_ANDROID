package com.hmoa.core_model.data

import kotlinx.serialization.Serializable

@Serializable
data class DefaultAddressDto(
    val addressName: String,
    val detailAddress: String,
    val landlineNumber: String,
    val name: String,
    val phoneNumber: String,
    val request: String,
    val streetAddress: String,
    val zipCode: String
)