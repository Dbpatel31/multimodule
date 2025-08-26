package com.example.multimodule.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
   @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
   fun observeNotes(): Flow<List<NoteEnitity>>

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun upsert(note:NoteEnitity)

   @Delete
   suspend fun delete(note:NoteEnitity)

   @Query("SELECT * FROM  notes WHERE dirty=1")
   suspend fun getDirty(): List<NoteEnitity>

   @Query("SELECT * FROM notes WHERE id = :id")
   suspend fun getById(id:String): NoteEnitity?

   @Query("DELETE FROM notes WHERE id = :id")
   suspend fun deleteById(id: String)

   @Query("DELETE FROM notes")
   suspend fun clearAll()
}