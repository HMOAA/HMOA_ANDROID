package com.hmoa.core_database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomNote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}