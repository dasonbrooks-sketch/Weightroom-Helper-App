package com.example.weightroom_help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(
    private val workoutList: List<Pair<String, String>>, // name to equipment
    private val onAdd: (name: String, equipment: String) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutName: TextView = itemView.findViewById(R.id.workoutName)
        val workoutSetsReps: TextView = itemView.findViewById(R.id.workoutSetsReps)
        val addButton: Button = itemView.findViewById(R.id.addToCurrentButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val (name, equipment) = workoutList[position]
        holder.workoutName.text = name

        val coreExercises = listOf(
            "Plank", "Dead Bug", "Bird Dog", "Hollow Hold",
            "Side Plank", "Ab Wheel Rollout", "Leg Raises",
            "Crunch", "Sit Up", "Russian Twist", "Mountain Climber"
        )

        holder.workoutSetsReps.text = when {
            coreExercises.any { name.contains(it, ignoreCase = true) } ->
                "2 sets x 30 reps/sec — increase reps or time if easy"
            equipment == "Bodyweight" ->
                "3 sets x 10 reps — increase rep range if easy"
            else ->
                "3 sets x 10 reps — increase weight if easy"
        }

        holder.addButton.setOnClickListener { onAdd(name, equipment) }
    }

    override fun getItemCount() = workoutList.size
}