package com.example.weightroom_help

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.workoutRecyclerView)
        val muscleSpinner = findViewById<Spinner>(R.id.muscleSpinner)
        val equipmentSpinner = findViewById<Spinner>(R.id.equipmentSpinner)
        val generateButton = findViewById<Button>(R.id.generateButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val muscles = listOf("Chest", "Legs", "Back", "Shoulders")
        val equipment = listOf("Barbell", "Dumbbell", "Machine", "Bodyweight")

        muscleSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, muscles)
        equipmentSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, equipment)

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

        val db = DatabaseHelper(this)

        generateButton.setOnClickListener {
            val selectedMuscle = muscleSpinner.selectedItem.toString()
            val selectedEquipment = equipmentSpinner.selectedItem.toString()
            val results = db.getFiltered(selectedMuscle, selectedEquipment)
            recyclerView.adapter = WorkoutAdapter(results)
        }
    }
}