package com.example.bodybilder.exercises.presenter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.model.BodyPart
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.repository.ExercisesRepositoryImpl
import com.example.bodybilder.exercises.contract.BodyPartContract
import com.example.bodybilder.exercises.contract.ExercisesContract
import com.example.bodybilder.exercises.view.ExercisesFragment
import com.example.bodybuilder.data.repository.BodyPartRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.joinToString

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
            exercisesRepository.insertInitialData() // اطمینان از وجود داده‌های اولیه Exercises
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
                withContext(Dispatchers.Main) {
                    // انتقال به ExercisesFragment با FragmentTransaction
                    val fragment = ExercisesFragment().apply {
                        arguments = Bundle().apply {
                            putInt("bodyPartId", it.id)
                        }
                    }
                    (context as? FragmentActivity)?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, fragment)
                        ?.addToBackStack(null) // اضافه کردن به Back Stack
                        ?.commit()
                }
            } ?: view.showError("بخش بدن یافت نشد")
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}