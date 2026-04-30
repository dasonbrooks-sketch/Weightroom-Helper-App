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

        val coreExercises = listOf(
            "Plank", "Dead Bug", "Bird Dog", "Hollow Hold",
            "Side Plank", "Ab Wheel Rollout", "Leg Raises",
            "Crunch", "Sit Up", "Russian Twist", "Mountain Climber"
        )

        holder.dayExercises.text = day.exercises.joinToString("\n") { exercise ->
            when {
                coreExercises.any { exercise.contains(it, ignoreCase = true) } ->
                    "• $exercise — 2 sets x 30 reps/sec"
                else ->
                    "• $exercise — 3 sets x 10 reps"
            }
        }
    }

    override fun getItemCount() = days.size
}