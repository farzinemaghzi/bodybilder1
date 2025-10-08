package com.example.bodybilder.exercises.presenter

import android.content.Context
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.model.BodyPart
import com.example.bodybilder.data.repository.ExercisesRepositoryImpl
import com.example.bodybilder.exercises.contract.BodyPartContract
import com.example.bodybuilder.data.repository.BodyPartRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BodyPartPresenter(
    private val view: BodyPartContract.View,
    private val context: Context
) : BodyPartContract.Presenter {
    private val repository: BodyPartContract.Repository = BodyPartRepositoryImpl(context)
    private val exercisesRepository = ExercisesRepositoryImpl(context)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val bodyPartDao = DatabaseProvider.getDatabase(context).bodyPartDao()

    init {
        scope.launch {
            repository.insertInitialData()
            exercisesRepository.insertInitialData() // اضافه کردن داده‌های اولیه Exercises
            loadBodyParts()
        }
    }

    override fun loadBodyParts() {
        scope.launch {
            try {
                val bodyParts = repository.getBodyParts()
                view.showBodyParts(bodyParts)
            } catch (e: Exception) {
                view.showError("خطا در بارگذاری داده‌ها: ${e.message}")
            }
        }
    }

    override fun onBodyPartSelected(bodyPart: BodyPart) {
        scope.launch(Dispatchers.IO) {
            val entity = bodyPartDao.getAll().find { it.name == bodyPart.name }
            entity?.let {
                val exercises = exercisesRepository.getExercises(it.id)
                withContext(Dispatchers.Main) {
                    view.showError("تمرین‌ها برای ${bodyPart.name}: ${exercises.joinToString { it.name }}")
                }
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}