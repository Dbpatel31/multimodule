package com.example.multimodule.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.impl.utils.isDefaultProcess
import com.example.multimodule.data.local.NoteDao
import com.example.multimodule.domain.model.Note
import com.example.multimodule.domain.repository.AuthRepository
import com.example.multimodule.domain.usecase.DeleteNoteUseCase
import com.example.multimodule.domain.usecase.ObserveNoteUseCase
import com.example.multimodule.domain.usecase.SyncNoteUseCase
import com.example.multimodule.domain.usecase.UpsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
   observe: ObserveNoteUseCase,
   private val upsert:UpsertNoteUseCase,
   private val delete:DeleteNoteUseCase,
private  val sync: SyncNoteUseCase,
    private val auth: AuthRepository,
   private val noteDao: NoteDao
): ViewModel() {

    val notes : StateFlow<List<Note>> = observe().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

   fun save(title:String, content:String, id:String="",created: Long = 0L) = viewModelScope.launch {
         upsert(Note(id= id, title=title, content=content, createdAt=created, updatedAt = 0L))
       sync()
   }

    fun delete(id:String) = viewModelScope.launch {
        delete.invoke(id)
        sync()
    }
    fun logout() = viewModelScope.launch {
        auth.signOut()
        noteDao.clearAll()
    }
}