package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CardData(
    val card_approve_no: String,
    val card_company: String,
    val card_company_code: String,
    val card_no: String,
    val card_quota: String,
    val coupon: Double,
    val point: Double,
    val tid: String
)