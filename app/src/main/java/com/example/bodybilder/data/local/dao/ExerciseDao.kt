package com.example.bodybilder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bodybilder.data.local.entity.ExerciseEntity
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE bodyPartId = :bodyPartId")
    suspend fun getByBodyPartId(bodyPartId: Int): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    suspend fun getById(exerciseId: Int): ExerciseEntity?

    @Update
    suspend fun update(exercise: ExerciseEntity)

    @Insert
    suspend fun insertWorkoutHistory(history: WorkoutHistoryEntity)

    @Query("SELECT * FROM workout_history WHERE exerciseId = :exerciseId ORDER BY date DESC")
    suspend fun getHistoryByExerciseId(exerciseId: Int): List<WorkoutHistoryEntity>
}