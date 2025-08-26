package com.example.multimodule.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
 tableName = "notes",
    indices = [Index("updatedAt")]
)
data class NoteEnitity (
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val dirty: Boolean
)