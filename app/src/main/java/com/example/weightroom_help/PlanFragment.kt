package com.example.weightroom_help

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlanFragment : Fragment() {

    private val exercisePool = mapOf(
        "Chest" to mapOf(
            "Barbell" to listOf("Bench Press", "Incline Press"),
            "Dumbbell" to listOf("Chest Fly", "Dumbbell Press"),
            "Machine" to listOf("Cable Crossover", "Machine Chest Press"),
            "Bodyweight" to listOf("Push Ups", "Wide Push Ups")
        ),
        "Legs" to mapOf(
            "Barbell" to listOf("Squats", "Romanian Deadlift"),
            "Dumbbell" to listOf("Lunges", "Dumbbell Squat"),
            "Machine" to listOf("Leg Press", "Leg Curl"),
            "Bodyweight" to listOf("Bodyweight Squat", "Jump Squats")
        ),
        "Back" to mapOf(
            "Barbell" to listOf("Bent Over Row", "Deadlift"),
            "Dumbbell" to listOf("Dumbbell Row", "Single Arm Row"),
            "Machine" to listOf("Lat Pulldown", "Seated Cable Row"),
            "Bodyweight" to listOf("Pull Ups", "Inverted Rows")
        ),
        "Shoulders" to mapOf(
            "Barbell" to listOf("Barbell OHP", "Behind the Neck Press"),
            "Dumbbell" to listOf("Shoulder Press", "Lateral Raises"),
            "Machine" to listOf("Machine Shoulder Press", "Cable Lateral Raise"),
            "Bodyweight" to listOf("Pike Push Ups", "Handstand Hold")
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_plan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daysSpinner = view.findViewById<Spinner>(R.id.daysSpinner)
        val equipmentSpinner = view.findViewById<Spinner>(R.id.equipmentSpinner)
        val generateButton = view.findViewById<Button>(R.id.generatePlanButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.planRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dayOptions = listOf("2 days a week", "3 days a week", "5 days a week")
        val equipmentOptions = listOf("Barbell", "Dumbbell", "Machine", "Bodyweight")

        daysSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            dayOptions
        )
        equipmentSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            equipmentOptions
        )

        daysSpinner.post {
            (daysSpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }
        equipmentSpinner.post {
            (equipmentSpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }

        daysSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                (view as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        equipmentSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                (view as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        generateButton.setOnClickListener {
            val selectedDays = daysSpinner.selectedItem.toString()
            val selectedEquipment = equipmentSpinner.selectedItem.toString()
            val plan = generatePlan(selectedDays, selectedEquipment)
            recyclerView.adapter = PlanAdapter(plan)
        }
    }

    private fun getExercises(muscle: String, equipment: String): List<String> {
        return exercisePool[muscle]?.get(equipment) ?: listOf("No exercises found")
    }

    private fun generatePlan(days: String, equipment: String): List<DayPlan> {
        return when (days) {
            "2 days a week" -> listOf(
                DayPlan(
                    dayLabel = "Day 1",
                    focus = "Full Body",
                    exercises = listOf(
                        getExercises("Chest", equipment)[0],
                        getExercises("Legs", equipment)[0],
                        getExercises("Back", equipment)[0],
                        getExercises("Shoulders", equipment)[0]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 2",
                    focus = "Full Body",
                    exercises = listOf(
                        getExercises("Chest", equipment)[1],
                        getExercises("Legs", equipment)[1],
                        getExercises("Back", equipment)[1],
                        getExercises("Shoulders", equipment)[1]
                    )
                )
            )
            "3 days a week" -> listOf(
                DayPlan(
                    dayLabel = "Day 1",
                    focus = "Chest & Shoulders",
                    exercises = listOf(
                        getExercises("Chest", equipment)[0],
                        getExercises("Chest", equipment)[1],
                        getExercises("Shoulders", equipment)[0],
                        getExercises("Shoulders", equipment)[1]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 2",
                    focus = "Legs & Back",
                    exercises = listOf(
                        getExercises("Legs", equipment)[0],
                        getExercises("Legs", equipment)[1],
                        getExercises("Back", equipment)[0],
                        getExercises("Back", equipment)[1]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 3",
                    focus = "Full Body",
                    exercises = listOf(
                        getExercises("Chest", equipment)[0],
                        getExercises("Legs", equipment)[0],
                        getExercises("Back", equipment)[0],
                        getExercises("Shoulders", equipment)[0]
                    )
                )
            )
            "5 days a week" -> listOf(
                DayPlan(
                    dayLabel = "Day 1",
                    focus = "Chest & Shoulders",
                    exercises = listOf(
                        getExercises("Chest", equipment)[0],
                        getExercises("Chest", equipment)[1],
                        getExercises("Shoulders", equipment)[0],
                        getExercises("Shoulders", equipment)[1]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 2",
                    focus = "Legs & Back",
                    exercises = listOf(
                        getExercises("Legs", equipment)[0],
                        getExercises("Legs", equipment)[1],
                        getExercises("Back", equipment)[0],
                        getExercises("Back", equipment)[1]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 3",
                    focus = "Chest & Shoulders",
                    exercises = listOf(
                        getExercises("Chest", equipment)[1],
                        getExercises("Chest", equipment)[0],
                        getExercises("Shoulders", equipment)[1],
                        getExercises("Shoulders", equipment)[0]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 4",
                    focus = "Legs & Back",
                    exercises = listOf(
                        getExercises("Legs", equipment)[1],
                        getExercises("Legs", equipment)[0],
                        getExercises("Back", equipment)[1],
                        getExercises("Back", equipment)[0]
                    )
                ),
                DayPlan(
                    dayLabel = "Day 5",
                    focus = "Full Body",
                    exercises = listOf(
                        getExercises("Chest", equipment)[0],
                        getExercises("Legs", equipment)[0],
                        getExercises("Back", equipment)[0],
                        getExercises("Shoulders", equipment)[0]
                    )
                )
            )
            else -> emptyList()
        }
    }
}