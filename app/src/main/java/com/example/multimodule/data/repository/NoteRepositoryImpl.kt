package com.example.multimodule.data.repository

import android.provider.ContactsContract
import com.example.multimodule.data.local.NoteDao

import com.example.multimodule.data.local.NoteEnitity
import com.example.multimodule.data.remote.FirebaseNoteDataSource
import com.example.multimodule.data.remote.FirebaseNoteDto
import com.example.multimodule.domain.model.Note
import com.example.multimodule.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val fs: FirebaseNoteDataSource,
    private val dao: NoteDao,
   private val auth:AuthRepositoryImpl
): NoteRepository {

    private fun NoteEnitity.toDomain():com.example.multimodule.domain.model.Note= com.example.multimodule.domain.model.Note(id, title, content, createdAt, updatedAt)
    private fun Note.toEntity(dirty: Boolean) = NoteEnitity(id, title, content, createdAt, updatedAt, dirty)
    private fun NoteEnitity.toDto() = FirebaseNoteDto(id, title, content, createdAt, updatedAt)
    private fun FirebaseNoteDto.toEntity() =
        NoteEnitity(id, title, content, createdAt, updatedAt, dirty = false)

    override fun observeNotes(): Flow<List<Note>> = dao.observeNotes().map { list->list.map { it.toDomain() } }

    override suspend fun upsert(note: Note) {
             val now=System.currentTimeMillis()
             val withTimes=note.copy(
               id = if (note.id.isBlank()) UUID.randomUUID().toString() else note.id,
               createdAt = if (note.createdAt==0L) now else note.createdAt,
                 updatedAt = now
             )
        dao.upsert(withTimes.toEntity(dirty = true))

        sync()
    }

    override suspend fun delete(id: String) {
         dao.deleteById(id)
          auth.uid()?.let { uid-> fs.delete(uid, id) }
    }

    override suspend fun sync() {
        val uid = auth.uid() ?: return
        val dirty = dao.getDirty()

        dirty.forEach { fs.upsert(uid, it.toDto()) }
        dirty.forEach { dao.upsert(it.copy(dirty = false)) }
        val remoteList = fs.getAll(uid)

        for(r in remoteList){
            val local= dao.getById(r.id)
            if (local==null){
                dao.upsert(r.toEntity())
            }
            else{
                val winner= if(r.updatedAt>=local.updatedAt) r.toEntity() else local.copy(dirty=false)
                dao.upsert(winner.copy(dirty=false))
            }

        }

    }

}