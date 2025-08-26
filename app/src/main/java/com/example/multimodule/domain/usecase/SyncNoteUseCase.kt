package com.example.multimodule.domain.usecase

import com.example.multimodule.domain.repository.NoteRepository

class SyncNoteUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke() = repo.sync()
}