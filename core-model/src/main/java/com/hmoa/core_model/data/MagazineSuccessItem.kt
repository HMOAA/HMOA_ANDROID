package com.hmoa.core_model.data

data class MagazineSuccessItem(
    val title : String,
    val createAt : String,
    val previewImgUrl : String,
    val preview : String,
    val contents : List<MagazineContentItem>,
    val tags : List<String>,
    val viewCount : Int,
    val likeCount : Int,
    val isLiked : Boolean,
)