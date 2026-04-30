package com.example.weightroom_help

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "exercise-db", null, 1) {

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
            Triple("Bench Press", "Chest", "Barbell"),
            Triple("Incline Press", "Chest", "Barbell"),
            Triple("Push Ups", "Chest", "Bodyweight"),
            Triple("Chest Fly", "Chest", "Dumbbell"),
            Triple("Cable Crossover", "Chest", "Machine"),
            Triple("Squats", "Legs", "Barbell"),
            Triple("Leg Press", "Legs", "Machine"),
            Triple("Lunges", "Legs", "Dumbbell"),
            Triple("Bodyweight Squat", "Legs", "Bodyweight"),
            Triple("Pull Ups", "Back", "Bodyweight"),
            Triple("Lat Pulldown", "Back", "Machine"),
            Triple("Bent Over Row", "Back", "Barbell"),
            Triple("Dumbbell Row", "Back", "Dumbbell"),
            Triple("Shoulder Press", "Shoulders", "Dumbbell"),
            Triple("Lateral Raises", "Shoulders", "Dumbbell"),
            Triple("Barbell OHP", "Shoulders", "Barbell"),
            Triple("Machine Shoulder Press", "Shoulders", "Machine")
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

    fun getFiltered(muscle: String, equipment: String): List<String> {
        val results = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM exercises WHERE muscleGroup = ? AND equipment = ?",
            arrayOf(muscle, equipment)
        )
        while (cursor.moveToNext()) {
            results.add(cursor.getString(0))
        }
        cursor.close()
        return results
    }
}