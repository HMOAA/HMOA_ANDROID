package com.hmoa.feature_home.model

data class HomePerfumes(
    val perfumeList: List<PerfumeInfo>,
    val title: String
)

data class PerfumeInfo(
    val brandName: String,
    val imgUrl: String,
    val perfumeId: Int,
    val perfumeName: String
)
