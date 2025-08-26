package com.example.multimodule.domain.usecase

import com.example.multimodule.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(id: String) = repo.delete(id)
}