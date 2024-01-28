package com.hmoa.core_data.response

data class MemberLoginResponseDto(
    val authToken:String,
    val existedMember:Boolean,
    val rememberedToken:String
)
