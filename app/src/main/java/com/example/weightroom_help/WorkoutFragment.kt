package com.example.weightroom_help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutFragment : Fragment() {

    private val currentWorkout = mutableListOf<CurrentExercise>()
    private lateinit var currentWorkoutAdapter: CurrentWorkoutAdapter
    private lateinit var currentRecyclerView: RecyclerView
    private lateinit var currentWorkoutHeader: TextView
    private lateinit var emptyText: TextView
    private var sectionExpanded = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_workout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseHelper(requireContext())

        currentWorkoutHeader = view.findViewById(R.id.currentWorkoutHeader)
        currentRecyclerView = view.findViewById(R.id.currentWorkoutRecyclerView)
        emptyText = view.findViewById(R.id.currentWorkoutEmpty)

        currentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        currentWorkoutAdapter = CurrentWorkoutAdapter(currentWorkout) { position ->
            currentWorkout.removeAt(position)
            currentWorkoutAdapter.notifyItemRemoved(position)
            currentWorkoutAdapter.notifyItemRangeChanged(position, currentWorkout.size)
            emptyText.visibility = if (currentWorkout.isEmpty()) View.VISIBLE else View.GONE
        }
        currentRecyclerView.adapter = currentWorkoutAdapter

        currentWorkoutHeader.setOnClickListener {
            sectionExpanded = !sectionExpanded
            currentRecyclerView.visibility = if (sectionExpanded) View.VISIBLE else View.GONE
            emptyText.visibility = if (sectionExpanded && currentWorkout.isEmpty()) View.VISIBLE else View.GONE
            currentWorkoutHeader.text = if (sectionExpanded) "▼  Current Workout" else "▶  Current Workout"
        }

        val checkChest = view.findViewById<CheckBox>(R.id.checkChest)
        val checkLegs = view.findViewById<CheckBox>(R.id.checkLegs)
        val checkBack = view.findViewById<CheckBox>(R.id.checkBack)
        val checkShoulders = view.findViewById<CheckBox>(R.id.checkShoulders)
        val checkCore = view.findViewById<CheckBox>(R.id.checkCore)

        val checkBarbell = view.findViewById<CheckBox>(R.id.checkBarbell)
        val checkDumbbell = view.findViewById<CheckBox>(R.id.checkDumbbell)
        val checkMachine = view.findViewById<CheckBox>(R.id.checkMachine)
        val checkBodyweight = view.findViewById<CheckBox>(R.id.checkBodyweight)

        val generateButton = view.findViewById<Button>(R.id.generateButton)
        val workoutRecyclerView = view.findViewById<RecyclerView>(R.id.workoutRecyclerView)
        workoutRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        generateButton.setOnClickListener {
            val selectedMuscles = mutableListOf<String>()
            if (checkChest.isChecked) selectedMuscles.add("Chest")
            if (checkLegs.isChecked) selectedMuscles.add("Legs")
            if (checkBack.isChecked) selectedMuscles.add("Back")
            if (checkShoulders.isChecked) selectedMuscles.add("Shoulders")
            if (checkCore.isChecked) selectedMuscles.add("Core")

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

            val taggedExercises = mutableListOf<Pair<String, String>>()
            for (muscle in selectedMuscles) {
                for (equipment in selectedEquipment) {
                    val names = db.getFiltered(muscle, equipment)
                    names.forEach { taggedExercises.add(Pair(it, equipment)) }
                }
            }

            if (taggedExercises.isEmpty()) {
                Toast.makeText(requireContext(), "No exercises found for that combination", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val generated = taggedExercises.shuffled().take(5)

            workoutRecyclerView.adapter = WorkoutAdapter(generated) { name, equipment ->
                val alreadyAdded = currentWorkout.any { it.name == name }
                if (alreadyAdded) {
                    Toast.makeText(requireContext(), "$name already in your workout", Toast.LENGTH_SHORT).show()
                } else {
                    val repsNote = if (equipment == "Bodyweight") {
                        "3 sets x 10 reps — increase rep range if easy"
                    } else {
                        "3 sets x 10 reps — increase weight if easy"
                    }
                    currentWorkout.add(CurrentExercise(name, repsNote))
                    currentWorkoutAdapter.notifyItemInserted(currentWorkout.size - 1)
                    emptyText.visibility = View.GONE
                    if (!sectionExpanded) {
                        sectionExpanded = true
                        currentRecyclerView.visibility = View.VISIBLE
                        currentWorkoutHeader.text = "▼  Current Workout"
                    }
                    Toast.makeText(requireContext(), "$name added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}