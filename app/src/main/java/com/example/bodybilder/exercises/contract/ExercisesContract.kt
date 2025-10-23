package com.example.bodybilder.exercises.contract

import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory

interface ExercisesContract {

    interface View {
        fun showExercises(exercises: List<Exercises>)
        fun showBodyPartName(name: String)
        fun showError(message: String)
        fun onWorkoutHistorySaved()
        fun showHistory(historyList: List<WorkoutHistoryEntity>)  // جدید: نمایش لیست تاریخچه

    }

    interface Presenter {
        fun loadExercises()
        fun onExerciseSelected(exercise: Exercises)
        fun onDestroy()
        fun saveWorkoutHistory(exerciseId: Int, reps: Int, weights: Int, date: String)
        fun loadHistoryForExercise(exerciseId: Int)  // جدید: لود تاریخچه برای exercise خاص
    }

    interface Repository {
        suspend fun getExercises(bodyPartId: Int): List<Exercises>
        suspend fun insertInitialData()
        suspend fun saveWorkoutHistory(history: WorkoutHistory)
        suspend fun getWorkoutHistoryForExercise(exerciseId: Int): List<WorkoutHistoryEntity>  // جدید: برمی‌گردونه لیست فیلترشده

    }

}