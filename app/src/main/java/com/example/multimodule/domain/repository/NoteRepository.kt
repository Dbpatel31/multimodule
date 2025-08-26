package com.example.multimodule.domain.repository


import com.example.multimodule.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
   fun observeNotes() : Flow<List<Note>>
   suspend fun upsert(note: Note)
   suspend fun delete(id:String)
   suspend fun sync()
}