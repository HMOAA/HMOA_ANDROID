package com.hmoa.core_common

import com.hmoa.core_model.data.OrderStatus

fun formatWon(value: Int): String = String.format("%,d", value)

fun concatWithComma(values: List<String>): String{
    var result = ""
    values.forEach{
        result += it
        if (values.lastIndex != values.indexOf(it)){
            result += ", "
        }
    }
    return result
}

fun OrderStatus.toDisplayString(): String{
    return when(this){
        OrderStatus.CREATED -> "주문 생성"
        OrderStatus.PAY_FAILED -> "결제 실패"
        OrderStatus.PAY_COMPLETE -> "결제 완료"
        OrderStatus.SHIPPING_COMPLETE -> "배송 완료"
        OrderStatus.SHIPPING_PROGRESS -> "배송 중"
        OrderStatus.PURCHASE_CONFIRMATION -> "구매 확정"
    }
}