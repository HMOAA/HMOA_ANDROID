package com.hmoa.core_data.response

import com.hmoa.core_data.Category

data class CommunityDefaultResponseDto(
    val author:String,
    val category:Category,
    val communityPhotos:CommunityPhotoDefaultResponseDto,
    val content:String,
    val id:Int,
    val imagesCount:Int,
    val myProfileImgUrl:String,
    val profileImgUrl:String,
    val time:String,
    val title:String,
    val writed:Boolean
)
