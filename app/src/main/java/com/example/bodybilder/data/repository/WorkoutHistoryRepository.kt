package com.example.bodybilder.data.repository

import com.example.bodybilder.data.local.dao.WorkoutHistoryDao
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

class WorkoutHistoryRepository (private val dao : WorkoutHistoryDao) {

    suspend fun saveWorkout (exerciseId : Int, reps : Int, weights : Int){
        val today = java.time.LocalDate.now().toString() //// فرمت YYYY-MM-DD (مثل "2025-10-07")
        val history = WorkoutHistoryEntity(
            exerciseId = 1,
            date = today,
            reps = reps,
            weights = weights
        )
        dao.insert(history)

    }

    // خواندن تاریخچه برای یک exercise خاص (Flow برای observe در UI)
    fun getHistoryForExercise(exerciseId: Int): Flow<List<WorkoutHistoryEntity>> {
        return dao.getHistoryForExercise(exerciseId)
    }

    // همه تاریخچه‌ها (اگر لازم باشه، مثلاً برای بک‌آپ)
    fun getAllHistory(): Flow<List<WorkoutHistoryEntity>> {
        return dao.getAllHistory()
    }

    // آپدیت یک رکورد (مثل ویرایش reps)
    suspend fun updateWorkout(history: WorkoutHistoryEntity) {
        dao.update(history)
    }

    // حذف یک رکورد
    suspend fun deleteWorkout(history: WorkoutHistoryEntity) {
        dao.delete(history)
    }

    // حذف همه برای یک exercise
    suspend fun deleteAllForExercise(exerciseId: Int) {
        dao.deleteAllForExercise(exerciseId)
    }

}