package com.example.multimodule.ui.notes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multimodule.R
import com.example.multimodule.databinding.ActivityNoteBinding
import com.example.multimodule.sync.SyncScheduler
import com.example.multimodule.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {
     private  lateinit var b:ActivityNoteBinding
     private val vm: NotesViewModel by viewModels()
    private val adapter= NoteListAdapter(
       onClick ={
           note->
           startActivity(Intent(this, EditNoteActivity::class.java).apply {
                  putExtra("id", note.id)
                  putExtra("title", note.title)
                  putExtra("content", note.content)
                  putExtra("createdAt", note.createdAt)
           })
       },

        onLongClick ={note -> vm.delete(note.id)}
    )
    @Inject
    lateinit var scheduler: SyncScheduler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        b = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(b.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        b.rvNotes.layoutManager = LinearLayoutManager(this)
        b.rvNotes.adapter = adapter

        b.btnAdd.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
        }
        b.btnLogout.setOnClickListener {
            vm.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        lifecycleScope.launch {
         vm.notes.collect{
           notesList ->
           adapter.submitList(notesList)
         }

        }
        scheduler.schedulePeriodic()
    }
}