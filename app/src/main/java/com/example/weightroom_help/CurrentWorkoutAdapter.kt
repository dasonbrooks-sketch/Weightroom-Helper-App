package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class CurrentExercise(
    val name: String,
    val repsNote: String
)

class CurrentWorkoutAdapter(
    private val items: MutableList<CurrentExercise>,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<CurrentWorkoutAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.currentWorkoutName)
        val reps: TextView = itemView.findViewById(R.id.currentWorkoutReps)
        val removeButton: Button = itemView.findViewById(R.id.removeWorkoutButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_current_workout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.reps.text = item.repsNote
        holder.removeButton.setOnClickListener { onRemove(position) }
    }

    override fun getItemCount() = items.size
}