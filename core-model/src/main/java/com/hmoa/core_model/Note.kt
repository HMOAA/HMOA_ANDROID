package com.hmoa.core_model

data class Note(
    val id: String,//고유 인덱스
    var content: String,
    var noteName: String,
    var notePhotoUrl: String
)
