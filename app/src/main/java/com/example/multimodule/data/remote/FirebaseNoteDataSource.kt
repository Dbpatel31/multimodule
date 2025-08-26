package com.example.multimodule.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseNoteDataSource @Inject constructor(
    private  val db: FirebaseFirestore
) {

    private fun notesCol(uid: String)= db.collection("users").document(uid).collection("notes")

    suspend fun upsert(uid: String, dto: FirebaseNoteDto){

        val docId = if(dto.id.isBlank()) notesCol(uid).document().id else dto.id
        notesCol(uid).document(docId).set(dto.copy(id = docId)).await()

    }

    suspend fun getAll(uid: String):List<FirebaseNoteDto>{
       val snap=notesCol(uid).get().await()
        return snap.documents.mapNotNull {
               it.toObject(FirebaseNoteDto::class.java)
        }
    }

    suspend fun delete(uid:String, id:String){
        notesCol(uid).document(id).delete().await()
    }

}