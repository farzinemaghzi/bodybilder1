package com.example.bodybilder.exercises.contract

import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory

interface ExercisesContract {

    interface View {
        fun showExercises(exercises: List<Exercises>)
        fun showBodyPartName(name: String)
        fun showError(message: String)
        fun onWorkoutHistorySaved()

    }

    interface Presenter {
        fun loadExercises()
        fun onExerciseSelected(exercise: Exercises)
        fun onDestroy()
        fun saveWorkoutHistory(exerciseId: Int, reps: Int, weights: Int, date: String)
    }

    interface Repository {
        suspend fun getExercises(bodyPartId: Int): List<Exercises>
        suspend fun insertInitialData()
        suspend fun saveWorkoutHistory(history: WorkoutHistory)

    }

}