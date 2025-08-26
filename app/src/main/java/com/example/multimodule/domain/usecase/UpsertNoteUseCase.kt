package com.example.multimodule.domain.usecase

import com.example.multimodule.domain.model.Note
import com.example.multimodule.domain.repository.NoteRepository

class UpsertNoteUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(note: Note) = repo.upsert(note)
}