package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BootpayOrderResultData(
    val receipt_id: String,
    val order_id: String,
    val price: Int,
    val tax_free: Int,
    val cancelled_price: Int,
    val cancelled_tax_free: Int,
    val order_name: String,
    val company_name: String,
    val gateway_url: String,
    val metadata: BootpayMetaData,
    val sandbox: Boolean,
    val pg: String,
    val method: String,
    val method_origin: String,
    val method_origin_symbol: String,
    val purchased_at: String,
    val requested_at: String,
    val status_locale: String,
    val currency: String,
    val receipt_url: String,
    val status: Int,
    val card_data: CardData,
    val method_symbol: String
)