package com.example.trainitcampv1.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.databinding.RowItemNotesBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    var listNotes = ArrayList<Notes>()

    inner class MyViewHolder(val binding: RowItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNotes[position]
        holder.binding.apply {
            mNotes = data
            executePendingBindings()
        }
    }

    override fun getItemCount() = listNotes.size

    fun setData(data: List<Notes>?) {
        if (data == null) return
        val notesDiffUtil = DiffCallback(listNotes, data)
        val notesDiffResult = DiffUtil.calculateDiff(notesDiffUtil)
        listNotes.clear()
        listNotes.addAll(data)
        notesDiffResult.dispatchUpdatesTo(this)
    }
}