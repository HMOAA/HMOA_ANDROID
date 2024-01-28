package com.hmoa.core_data.response

import com.hmoa.core_data.Category

data class CommunityByCategoryResponseDto(
    val category: Category,
    val communityId:Int,
    val title:String
)
