package com.example.weightroom_help

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "exercise-db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE exercises (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                muscleGroup TEXT,
                equipment TEXT
            )
        """)
        seedData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS exercises")
        onCreate(db)
    }

    private fun seedData(db: SQLiteDatabase) {
        val exercises = listOf(
            // Chest
            Triple("Bench Press", "Chest", "Barbell"),
            Triple("Incline Press", "Chest", "Barbell"),
            Triple("Close Grip Bench Press", "Chest", "Barbell"),
            Triple("Dumbbell Chest Press", "Chest", "Dumbbell"),
            Triple("Chest Fly", "Chest", "Dumbbell"),
            Triple("Incline Dumbbell Press", "Chest", "Dumbbell"),
            Triple("Cable Crossover", "Chest", "Machine"),
            Triple("Machine Chest Press", "Chest", "Machine"),
            Triple("Pec Deck", "Chest", "Machine"),
            Triple("Push Ups", "Chest", "Bodyweight"),
            Triple("Wide Push Ups", "Chest", "Bodyweight"),
            Triple("Diamond Push Ups", "Chest", "Bodyweight"),
            // Legs
            Triple("Squats", "Legs", "Barbell"),
            Triple("Romanian Deadlift", "Legs", "Barbell"),
            Triple("Front Squat", "Legs", "Barbell"),
            Triple("Dumbbell Lunges", "Legs", "Dumbbell"),
            Triple("Dumbbell Squat", "Legs", "Dumbbell"),
            Triple("Bulgarian Split Squat", "Legs", "Dumbbell"),
            Triple("Leg Press", "Legs", "Machine"),
            Triple("Leg Curl", "Legs", "Machine"),
            Triple("Leg Extension", "Legs", "Machine"),
            Triple("Bodyweight Squat", "Legs", "Bodyweight"),
            Triple("Jump Squats", "Legs", "Bodyweight"),
            Triple("Walking Lunges", "Legs", "Bodyweight"),
            // Back
            Triple("Bent Over Row", "Back", "Barbell"),
            Triple("Deadlift", "Back", "Barbell"),
            Triple("Pendlay Row", "Back", "Barbell"),
            Triple("Dumbbell Row", "Back", "Dumbbell"),
            Triple("Single Arm Row", "Back", "Dumbbell"),
            Triple("Dumbbell Deadlift", "Back", "Dumbbell"),
            Triple("Lat Pulldown", "Back", "Machine"),
            Triple("Seated Cable Row", "Back", "Machine"),
            Triple("Machine Row", "Back", "Machine"),
            Triple("Pull Ups", "Back", "Bodyweight"),
            Triple("Chin Ups", "Back", "Bodyweight"),
            Triple("Inverted Rows", "Back", "Bodyweight"),
            // Shoulders
            Triple("Barbell OHP", "Shoulders", "Barbell"),
            Triple("Behind the Neck Press", "Shoulders", "Barbell"),
            Triple("Upright Row", "Shoulders", "Barbell"),
            Triple("Dumbbell Shoulder Press", "Shoulders", "Dumbbell"),
            Triple("Lateral Raises", "Shoulders", "Dumbbell"),
            Triple("Front Raises", "Shoulders", "Dumbbell"),
            Triple("Machine Shoulder Press", "Shoulders", "Machine"),
            Triple("Cable Lateral Raise", "Shoulders", "Machine"),
            Triple("Cable Face Pull", "Shoulders", "Machine"),
            Triple("Pike Push Ups", "Shoulders", "Bodyweight"),
            Triple("Handstand Hold", "Shoulders", "Bodyweight"),
            Triple("YTW Exercise", "Shoulders", "Bodyweight")
        )
        exercises.forEach { (name, muscle, equipment) ->
            val values = ContentValues().apply {
                put("name", name)
                put("muscleGroup", muscle)
                put("equipment", equipment)
            }
            db.insert("exercises", null, values)
        }
    }

    /** Single equipment type — used internally */
    fun getFiltered(muscle: String, equipment: String): List<String> {
        val results = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM exercises WHERE muscleGroup = ? AND equipment = ?",
            arrayOf(muscle, equipment)
        )
        while (cursor.moveToNext()) results.add(cursor.getString(0))
        cursor.close()
        return results
    }

    /** Multiple equipment types — used by WorkoutFragment and PlanFragment */
    fun getFiltered(muscle: String, equipmentList: List<String>): List<String> {
        if (equipmentList.isEmpty()) return emptyList()
        val placeholders = equipmentList.joinToString(",") { "?" }
        val args = arrayOf(muscle) + equipmentList.toTypedArray()
        val results = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM exercises WHERE muscleGroup = ? AND equipment IN ($placeholders)",
            args
        )
        while (cursor.moveToNext()) results.add(cursor.getString(0))
        cursor.close()
        return results
    }
}