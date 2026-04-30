package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanAdapter(private val days: List<DayPlan>) :
    RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayLabel: TextView = itemView.findViewById(R.id.dayLabel)
        val dayFocus: TextView = itemView.findViewById(R.id.dayFocus)
        val dayExercises: TextView = itemView.findViewById(R.id.dayExercises)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day_plan, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val day = days[position]
        holder.dayLabel.text = day.dayLabel
        holder.dayFocus.text = day.focus
        holder.dayExercises.text = day.exercises.joinToString("\n") { "• $it" }
    }

    override fun getItemCount() = days.size
}