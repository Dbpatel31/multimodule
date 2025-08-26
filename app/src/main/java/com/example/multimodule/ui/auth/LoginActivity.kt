package com.example.multimodule.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multimodule.databinding.ActivityLoginBinding
import com.example.multimodule.domain.usecase.SignInUseCase
import com.example.multimodule.domain.usecase.SignUpUseCase
import com.example.multimodule.ui.notes.NoteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), CoroutineScope {
    private val job= SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate+ job

            private lateinit var b:ActivityLoginBinding
    @Inject lateinit var signIn: SignInUseCase
    @Inject lateinit var signUp: SignUpUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        b= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        b.btnSignIn.setOnClickListener { launch { auth { signIn(b.etEmail.text.toString(), b.etPassword.text.toString()) } } }
        b.btnSignUp.setOnClickListener {
            val email = b.etEmail.text.toString()
            val password = b.etPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                launch {
                    try {
                        signUp(email, password)
                        startActivity(Intent(this@LoginActivity, NoteActivity::class.java))
                        finish()
                    } catch(e: Exception) {
                        b.etPassword.error = e.message ?: "Auth failed"
                    }
                }
            } else {
                b.etEmail.error = "Enter email"
                b.etPassword.error = "Enter password"
            }
        }

    }

    private suspend fun auth(block: suspend ()->Unit){
                 try{
                   block()
startActivity(Intent(this, NoteActivity::class.java))
finish()
                   }
                 catch (t:Throwable){
       b.etPassword.error= t.message?: "Auth failed"
                 }
    }


    override fun onDestroy() {
        super.onDestroy();
        job.cancel()
    }

}