package com.example.multimodule.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.multimodule.databinding.ItemNoteBinding
import com.example.multimodule.domain.model.Note

class NoteListAdapter(
    private val onClick: (Note) -> Unit,
    private val onLongClick: (Note) -> Unit
): ListAdapter<Note, NoteListAdapter.VH>(DIFF) {

    companion object{
      private val DIFF = object : DiffUtil.ItemCallback<Note>(){
          override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
              return oldItem.id== newItem.id
          }

          override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
               return oldItem.title == newItem.title && oldItem.content == newItem.content
          }

      }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListAdapter.VH {
        val b = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: NoteListAdapter.VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val b: ItemNoteBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(n: Note) {
            b.tvTitle.text = n.title
            b.tvContent.text = n.content
            b.root.setOnClickListener { onClick(n) }
            b.root.setOnLongClickListener { onLongClick(n); true }
        }
    }
}