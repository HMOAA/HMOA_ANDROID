package com.hmoa.core_model.data

enum class OrderStatus {
    CREATED, //주문 생성
    PAY_FAILED, //결제 실패
    PAY_COMPLETE, //결제 완료
    PAY_CANCEL, //결제 취소 (환불 완료)
    RETURN_PROGRESS, // 반품 진행 중
    RETURN_COMPLETE, // 반품 완료
    SHIPPING_PROGRESS, //배송 중
    SHIPPING_COMPLETE, //배송 완료
    PURCHASE_CONFIRMATION, //구매 확정
}