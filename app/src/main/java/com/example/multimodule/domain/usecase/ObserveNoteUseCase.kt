package com.example.multimodule.domain.usecase

import com.example.multimodule.domain.repository.NoteRepository

class ObserveNoteUseCase(private val repo: NoteRepository) {
    operator fun invoke() = repo.observeNotes()
}