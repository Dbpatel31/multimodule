package com.example.multimodule.di

import com.example.multimodule.domain.repository.AuthRepository
import com.example.multimodule.domain.repository.NoteRepository
import com.example.multimodule.domain.usecase.DeleteNoteUseCase
import com.example.multimodule.domain.usecase.ObserveNoteUseCase
import com.example.multimodule.domain.usecase.SignInUseCase
import com.example.multimodule.domain.usecase.SignUpUseCase
import com.example.multimodule.domain.usecase.SyncNoteUseCase
import com.example.multimodule.domain.usecase.UpsertNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {
    @Provides
    fun provideSignIn(repo: AuthRepository) = SignInUseCase(repo)
    @Provides
    fun provideSignUp(repo: AuthRepository) = SignUpUseCase(repo)
    @Provides fun provideObserve(repo: NoteRepository) = ObserveNoteUseCase(repo)
    @Provides fun provideUpsert(repo: NoteRepository) = UpsertNoteUseCase(repo)
    @Provides fun provideDelete(repo: NoteRepository) = DeleteNoteUseCase(repo)
    @Provides fun provideSync(repo: NoteRepository) = SyncNoteUseCase(repo)
}