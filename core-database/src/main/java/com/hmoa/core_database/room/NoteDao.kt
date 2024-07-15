package com.hmoa.core_database.room

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: RoomNote)

    @Update
    suspend fun update(note: RoomNote)

    @Delete
    suspend fun delete(note: RoomNote)

    @Query("SELECT * FROM RoomNote")
    fun getAllNotes(): List<RoomNote>
}