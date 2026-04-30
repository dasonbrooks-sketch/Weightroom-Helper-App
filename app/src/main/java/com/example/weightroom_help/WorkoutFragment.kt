package com.example.weightroom_help

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_workout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.workoutRecyclerView)
        val muscleSpinner = view.findViewById<Spinner>(R.id.muscleSpinner)
        val equipmentSpinner = view.findViewById<Spinner>(R.id.equipmentSpinner)
        val generateButton = view.findViewById<Button>(R.id.generateButton)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val muscles = listOf("Chest", "Legs", "Back", "Shoulders")
        val equipment = listOf("Barbell", "Dumbbell", "Machine", "Bodyweight")

        muscleSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, muscles)
        equipmentSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, equipment)

        muscleSpinner.post {
            (muscleSpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }
        equipmentSpinner.post {
            (equipmentSpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }

        muscleSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
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

        val db = DatabaseHelper(requireContext())

        generateButton.setOnClickListener {
            val selectedMuscle = muscleSpinner.selectedItem.toString()
            val selectedEquipment = equipmentSpinner.selectedItem.toString()
            val results = db.getFiltered(selectedMuscle, selectedEquipment)
            recyclerView.adapter = WorkoutAdapter(results)
        }
    }
}