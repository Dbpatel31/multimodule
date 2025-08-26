package com.example.multimodule.domain.usecase

import com.example.multimodule.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repo: AuthRepository) {
  suspend operator fun invoke(email:String, password:String)= repo.signIn(email,password)
}