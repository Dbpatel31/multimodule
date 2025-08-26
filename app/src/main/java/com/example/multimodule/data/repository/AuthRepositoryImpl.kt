package com.example.multimodule.data.repository

import com.example.multimodule.data.remote.FirebaseAuthDataSource
import com.example.multimodule.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val ds: FirebaseAuthDataSource
):AuthRepository {
    override suspend fun signIn(email: String, password: String) = ds.signIn(email,password)

    override suspend fun signUp(email: String, password: String) = ds.signUp(email,password)

    override fun signOut() = ds.signOut()
    override fun uid(): String? = ds.currentUid

}