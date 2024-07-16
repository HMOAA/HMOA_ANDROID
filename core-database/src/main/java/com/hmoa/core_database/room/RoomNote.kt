package com.hmoa.core_database.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomNote(
    @PrimaryKey val id: Int,
    val content: String,
    val noteName: String,
    val notePhotoUrl: String
)
