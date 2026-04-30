package com.example.weightroom_help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutFragment : Fragment() {

    private val exercisePool = mapOf(
        "Chest" to mapOf(
            "Barbell" to listOf("Bench Press", "Incline Bench Press", "Close Grip Bench Press"),
            "Dumbbell" to listOf("Dumbbell Chest Press", "Chest Fly", "Incline Dumbbell Press"),
            "Machine" to listOf("Cable Crossover", "Machine Chest Press", "Pec Deck"),
            "Bodyweight" to listOf("Push Ups", "Wide Push Ups", "Diamond Push Ups")
        ),
        "Legs" to mapOf(
            "Barbell" to listOf("Squats", "Romanian Deadlift", "Front Squat"),
            "Dumbbell" to listOf("Dumbbell Lunges", "Dumbbell Squat", "Bulgarian Split Squat"),
            "Machine" to listOf("Leg Press", "Leg Curl", "Leg Extension"),
            "Bodyweight" to listOf("Bodyweight Squat", "Jump Squats", "Walking Lunges")
        ),
        "Back" to mapOf(
            "Barbell" to listOf("Bent Over Row", "Deadlift", "Pendlay Row"),
            "Dumbbell" to listOf("Dumbbell Row", "Single Arm Row", "Dumbbell Deadlift"),
            "Machine" to listOf("Lat Pulldown", "Seated Cable Row", "Machine Row"),
            "Bodyweight" to listOf("Pull Ups", "Chin Ups", "Inverted Rows")
        ),
        "Shoulders" to mapOf(
            "Barbell" to listOf("Barbell OHP", "Behind the Neck Press", "Upright Row"),
            "Dumbbell" to listOf("Dumbbell Shoulder Press", "Lateral Raises", "Front Raises"),
            "Machine" to listOf("Machine Shoulder Press", "Cable Lateral Raise", "Cable Face Pull"),
            "Bodyweight" to listOf("Pike Push Ups", "Handstand Hold", "YTW Exercise")
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_workout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkChest = view.findViewById<CheckBox>(R.id.checkChest)
        val checkLegs = view.findViewById<CheckBox>(R.id.checkLegs)
        val checkBack = view.findViewById<CheckBox>(R.id.checkBack)
        val checkShoulders = view.findViewById<CheckBox>(R.id.checkShoulders)

        val checkBarbell = view.findViewById<CheckBox>(R.id.checkBarbell)
        val checkDumbbell = view.findViewById<CheckBox>(R.id.checkDumbbell)
        val checkMachine = view.findViewById<CheckBox>(R.id.checkMachine)
        val checkBodyweight = view.findViewById<CheckBox>(R.id.checkBodyweight)

        val generateButton = view.findViewById<Button>(R.id.generateButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.workoutRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        generateButton.setOnClickListener {
            val selectedMuscles = mutableListOf<String>()
            if (checkChest.isChecked) selectedMuscles.add("Chest")
            if (checkLegs.isChecked) selectedMuscles.add("Legs")
            if (checkBack.isChecked) selectedMuscles.add("Back")
            if (checkShoulders.isChecked) selectedMuscles.add("Shoulders")

            val selectedEquipment = mutableListOf<String>()
            if (checkBarbell.isChecked) selectedEquipment.add("Barbell")
            if (checkDumbbell.isChecked) selectedEquipment.add("Dumbbell")
            if (checkMachine.isChecked) selectedEquipment.add("Machine")
            if (checkBodyweight.isChecked) selectedEquipment.add("Bodyweight")

            if (selectedMuscles.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one muscle group", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedEquipment.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one equipment type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val allPossibleExercises = mutableListOf<String>()
            for (muscle in selectedMuscles) {
                for (equipment in selectedEquipment) {
                    val exercises = exercisePool[muscle]?.get(equipment) ?: emptyList()
                    allPossibleExercises.addAll(exercises)
                }
            }

            if (allPossibleExercises.isEmpty()) {
                Toast.makeText(requireContext(), "No exercises found for that combination", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val workout = allPossibleExercises
                .shuffled()
                .take(5)

            recyclerView.adapter = WorkoutAdapter(workout)
        }
    }
}