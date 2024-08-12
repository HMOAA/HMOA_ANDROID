package com.hmoa.core_common

enum class ErrorMessageType(val code: Int, val message: String) {
    EXPIRED_TOKEN(401, "ACCESS Token이 만료되었습니다."),
    WRONG_TYPE_TOKEN(401, "변조된 토큰입니다."),
    UNKNOWN_ERROR(404, "jwt가 존재하지 않습니다."),
    MEMBER_NOT_FOUND(404, "등록된 멤버가 없습니다.")
}