package com.example.multimodule.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val auth: FirebaseAuth) {

     val currentUid: String? get()= auth.currentUser?.uid

     suspend fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).await()
     }

     suspend fun signUp(email: String, password: String){
          Log.d("AuthDebug", "Signing up: $email")
          auth.createUserWithEmailAndPassword(email,password).await()
     }

     fun signOut()= auth.signOut()
}