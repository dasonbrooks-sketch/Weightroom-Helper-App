package com.example.weightroom_help

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlanFragment : Fragment() {

    private lateinit var db: DatabaseHelper

    private fun getExercises(muscle: String, equipment: List<String>): List<String> {
        val results = db.getFiltered(muscle, equipment).shuffled()
        return if (results.isNotEmpty()) results else listOf("No exercises found")
    }

    private fun generatePlan(days: String, equipment: List<String>): List<DayPlan> {
        // Core is treated as a finisher on every day it has exercises available
        fun pick(muscle: String, index: Int = 0): String {
            val list = getExercises(muscle, equipment)
            return list.getOrElse(index) { list[0] }
        }

        return when (days) {
            "2 days a week" -> listOf(
                DayPlan("Day 1", "Full Body + Core", listOf(
                    pick("Chest"), pick("Legs"), pick("Back"), pick("Shoulders"), pick("Core")
                )),
                DayPlan("Day 2", "Full Body + Core", listOf(
                    pick("Chest", 1), pick("Legs", 1), pick("Back", 1), pick("Shoulders", 1), pick("Core", 1)
                ))
            )
            "3 days a week" -> listOf(
                DayPlan("Day 1", "Chest & Shoulders", listOf(
                    pick("Chest"), pick("Chest", 1), pick("Shoulders"), pick("Shoulders", 1)
                )),
                DayPlan("Day 2", "Legs & Back", listOf(
                    pick("Legs"), pick("Legs", 1), pick("Back"), pick("Back", 1)
                )),
                DayPlan("Day 3", "Full Body + Core", listOf(
                    pick("Chest"), pick("Legs"), pick("Back"), pick("Shoulders"), pick("Core")
                ))
            )
            "5 days a week" -> listOf(
                DayPlan("Day 1", "Chest & Shoulders", listOf(
                    pick("Chest"), pick("Chest", 1), pick("Shoulders"), pick("Shoulders", 1)
                )),
                DayPlan("Day 2", "Legs & Back", listOf(
                    pick("Legs"), pick("Legs", 1), pick("Back"), pick("Back", 1)
                )),
                DayPlan("Day 3", "Chest & Shoulders + Core", listOf(
                    pick("Chest", 1), pick("Chest"), pick("Shoulders", 1), pick("Shoulders"), pick("Core")
                )),
                DayPlan("Day 4", "Legs & Back + Core", listOf(
                    pick("Legs", 1), pick("Legs"), pick("Back", 1), pick("Back"), pick("Core", 1)
                )),
                DayPlan("Day 5", "Full Body + Core", listOf(
                    pick("Chest"), pick("Legs"), pick("Back"), pick("Shoulders"), pick("Core", 2)
                ))
            )
            else -> emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_plan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DatabaseHelper(requireContext())

        val daysSpinner = view.findViewById<Spinner>(R.id.daysSpinner)
        val checkPlanBarbell = view.findViewById<CheckBox>(R.id.checkPlanBarbell)
        val checkPlanDumbbell = view.findViewById<CheckBox>(R.id.checkPlanDumbbell)
        val checkPlanMachine = view.findViewById<CheckBox>(R.id.checkPlanMachine)
        val checkPlanBodyweight = view.findViewById<CheckBox>(R.id.checkPlanBodyweight)
        val generateButton = view.findViewById<Button>(R.id.generatePlanButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.planRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dayOptions = listOf("2 days a week", "3 days a week", "5 days a week")
        daysSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            dayOptions
        )

        daysSpinner.post {
            (daysSpinner.selectedView as? android.widget.TextView)?.setTextColor(Color.WHITE)
        }
        daysSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                (view as? android.widget.TextView)?.setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        generateButton.setOnClickListener {
            val selectedEquipment = mutableListOf<String>()
            if (checkPlanBarbell.isChecked) selectedEquipment.add("Barbell")
            if (checkPlanDumbbell.isChecked) selectedEquipment.add("Dumbbell")
            if (checkPlanMachine.isChecked) selectedEquipment.add("Machine")
            if (checkPlanBodyweight.isChecked) selectedEquipment.add("Bodyweight")

            if (selectedEquipment.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one equipment type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val plan = generatePlan(daysSpinner.selectedItem.toString(), selectedEquipment)
            recyclerView.adapter = PlanAdapter(plan)
        }
    }
}