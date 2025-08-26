package com.example.multimodule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
entities =[NoteEnitity::class],
version = 1,
exportSchema =false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao() : NoteDao
}