package com.hmoa.core_data.response

import com.hmoa.core_data.Provider

data class MemberResponseDto(
    val age:Int,
    val memberId:Int,
    val memberImageUrl:String,
    val nickname:String,
    val provider:Provider,
    val sex:Boolean
)
