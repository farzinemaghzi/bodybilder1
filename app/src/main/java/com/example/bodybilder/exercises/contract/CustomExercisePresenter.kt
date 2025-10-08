package com.example.bodybilder.exercises.contract

import android.content.Context
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomExercisePresenter(
    private val view: CustomExerciseContract.View,
    private val context: Context
) : CustomExerciseContract.Presenter {

    private val bodyPartDao = DatabaseProvider.getDatabase(context).bodyPartDao()
    private val exerciseDao = DatabaseProvider.getDatabase(context).exerciseDao()

    override fun loadBodyParts() {
        CoroutineScope(Dispatchers.Main).launch {
            val bodyParts = withContext(Dispatchers.IO) {
                bodyPartDao.getAll()
            }
            view.showBodyParts(bodyParts)
        }
    }

    override fun saveExercise(exercise: Exercises, bodyPartId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.IO) {
                    exerciseDao.insert(exercise.toEntity(bodyPartId))
                }
                view.showSuccess()
            } catch (e: Exception) {
                view.showError("خطا در ذخیره تمرین: ${e.message}")
            }
        }
    }
}