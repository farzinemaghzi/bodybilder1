package com.example.bodybilder.exercises.presenter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.local.dao.BodyPartDao
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory
import com.example.bodybilder.data.repository.ExercisesRepositoryImpl
import com.example.bodybilder.exercises.contract.ExercisesContract
import com.example.bodybilder.exercises.view.ExerciseDetailFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExercisesPresenter(
    private val view: ExercisesContract.View,
    private val context: Context,
    private val bodyPartId: Int?
) : ExercisesContract.Presenter {
    private val repository: ExercisesContract.Repository = ExercisesRepositoryImpl(context)
    private val bodyPartDao = DatabaseProvider.getDatabase(context).bodyPartDao()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        scope.launch {
            repository.insertInitialData() // اطمینان از وجود داده‌های اولیه
            loadExercises()
        }
    }

    override fun loadExercises() {
        scope.launch {
            try {
                bodyPartId?.let { id ->
                    // لود اسم BodyPart
                    val bodyPart = bodyPartDao.getById(id)
                    view.showBodyPartName(bodyPart?.name ?: "Unknown")

                    // لود تمرین‌ها
                    val exercises = repository.getExercises(id)
                    view.showExercises(exercises)
                } ?: view.showError("شناسه بخش بدن نامعتبر است")
            } catch (e: Exception) {
                view.showError("خطا در بارگذاری تمرین‌ها: ${e.message}")
            }
        }
    }

    override fun onExerciseSelected(exercise: Exercises) {
        // انتقال به ExerciseDetailFragment
        val fragment = ExerciseDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("exercise", exercise)
            }
        }
        (context as? FragmentActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


    override fun saveWorkoutHistory(exerciseId: Int, reps: Int, weights: Int, date: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.saveWorkoutHistory(
                        WorkoutHistory(
                            exerciseId = exerciseId,
                            date = date,
                            reps = reps,
                            weights = weights
                        )
                    )
                }
                view.onWorkoutHistorySaved()
            } catch (e: Exception) {
                view.showError("خطا در ذخیره تاریخچه تمرین: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}