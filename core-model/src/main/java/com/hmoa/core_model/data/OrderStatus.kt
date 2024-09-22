package com.hmoa.core_model.data

enum class OrderStatus {
    CREATED, //주문 생성
    PAY_FAILED, //결제 실패
    PAY_COMPLETE, //결제 완료
    SHIPPING_PROGRESS, //배송 중
    SHIPPING_COMPLETE, //배송 완료
    PURCHASE_CONFIRMATION //구매 확정
}