package com.example.multimodule.ui.notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.multimodule.R
import com.example.multimodule.databinding.ActivityEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteActivity : AppCompatActivity() {
    private lateinit var b: ActivityEditNoteBinding
    private val vm: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        b = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(b.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val id = intent.getStringExtra("id") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        val createdAt = intent.getLongExtra("createdAt", 0L)

        b.etTitle.setText(title)
        b.etContent.setText(content)

        b.btnSave.setOnClickListener {
            vm.save(b.etTitle.text.toString(), b.etContent.text.toString(), id, createdAt)
            finish()
        }
    }
}