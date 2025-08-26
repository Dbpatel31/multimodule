package com.example.multimodule.domain.repository

interface AuthRepository {
    suspend fun signIn(email:String, password:String)
    suspend fun signUp(email: String,password: String)
    fun signOut()
    fun uid(): String?
}