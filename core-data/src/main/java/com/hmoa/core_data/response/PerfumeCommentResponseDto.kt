package com.hmoa.core_data.response

data class PerfumeCommentResponseDto(
    val content:String,
    val createdAt:String,
    val heartCount:Int,
    val id:Int,
    val liked:Boolean,
    val nickname:String,
    val perfumeId:Int,
    val profileImg:String,
    val writed:Boolean
)
