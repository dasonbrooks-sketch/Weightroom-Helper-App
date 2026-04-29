package com.example.weightroom_help

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<Exercise>

    @Query("SELECT * FROM exercises WHERE muscleGroup = :muscle AND equipment = :equipment")
    suspend fun getFiltered(muscle: String, equipment: String): List<Exercise>

}