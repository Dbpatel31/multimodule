package com.example.multimodule.di

import android.content.Context
import androidx.room.Room
import com.example.multimodule.data.local.AppDatabase
import com.example.multimodule.data.local.NoteDao
import com.example.multimodule.data.repository.AuthRepositoryImpl
import com.example.multimodule.data.repository.NoteRepositoryImpl
import com.example.multimodule.domain.repository.AuthRepository
import com.example.multimodule.domain.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context) = Room.databaseBuilder(ctx, AppDatabase::class.java, "notes_db").fallbackToDestructiveMigration().build()

    @Provides @Singleton
    fun provideAuth():FirebaseAuth= FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(ds: com.example.multimodule.data.remote.FirebaseAuthDataSource): AuthRepository =
        AuthRepositoryImpl(ds)

    @Provides @Singleton
    fun provideFirestore(): FirebaseFirestore= FirebaseFirestore.getInstance().apply {

    }

    @Provides @Singleton
    fun provideNoteRepository(impl:NoteRepositoryImpl) :NoteRepository= impl

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao {
        return db.noteDao()
    }
}
