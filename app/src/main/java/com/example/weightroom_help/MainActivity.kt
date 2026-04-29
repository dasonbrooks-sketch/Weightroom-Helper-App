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
import androidx.room.Room
import kotlinx.coroutines.*

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

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "exercise-db"
        ).build()

        val dao = db.exerciseDao()

        CoroutineScope(Dispatchers.IO).launch {
            if (dao.getAll().isEmpty()) {
                dao.insert(Exercise(name = "Bench Press", muscleGroup = "Chest", equipment = "Barbell"))
                dao.insert(Exercise(name = "Incline Press", muscleGroup = "Chest", equipment = "Barbell"))
                dao.insert(Exercise(name = "Push Ups", muscleGroup = "Chest", equipment = "Bodyweight"))
                dao.insert(Exercise(name = "Chest Fly", muscleGroup = "Chest", equipment = "Dumbbell"))
                dao.insert(Exercise(name = "Cable Crossover", muscleGroup = "Chest", equipment = "Machine"))
                dao.insert(Exercise(name = "Squats", muscleGroup = "Legs", equipment = "Barbell"))
                dao.insert(Exercise(name = "Leg Press", muscleGroup = "Legs", equipment = "Machine"))
                dao.insert(Exercise(name = "Lunges", muscleGroup = "Legs", equipment = "Dumbbell"))
                dao.insert(Exercise(name = "Bodyweight Squat", muscleGroup = "Legs", equipment = "Bodyweight"))
                dao.insert(Exercise(name = "Pull Ups", muscleGroup = "Back", equipment = "Bodyweight"))
                dao.insert(Exercise(name = "Lat Pulldown", muscleGroup = "Back", equipment = "Machine"))
                dao.insert(Exercise(name = "Bent Over Row", muscleGroup = "Back", equipment = "Barbell"))
                dao.insert(Exercise(name = "Dumbbell Row", muscleGroup = "Back", equipment = "Dumbbell"))
                dao.insert(Exercise(name = "Shoulder Press", muscleGroup = "Shoulders", equipment = "Dumbbell"))
                dao.insert(Exercise(name = "Lateral Raises", muscleGroup = "Shoulders", equipment = "Dumbbell"))
                dao.insert(Exercise(name = "Barbell OHP", muscleGroup = "Shoulders", equipment = "Barbell"))
                dao.insert(Exercise(name = "Machine Shoulder Press", muscleGroup = "Shoulders", equipment = "Machine"))
            }
        }

        generateButton.setOnClickListener {
            val selectedMuscle = muscleSpinner.selectedItem.toString()
            val selectedEquipment = equipmentSpinner.selectedItem.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val results = dao.getFiltered(selectedMuscle, selectedEquipment)
                val workoutNames = results.map { it.name }

                withContext(Dispatchers.Main) {
                    recyclerView.adapter = WorkoutAdapter(workoutNames)
                }
            }
        }
    }
}