package com.example.bodybilder.exercises.presenter

import android.util.Log
import com.example.bodybilder.data.repository.WorkoutHistoryRepository
import com.example.bodybilder.exercises.contract.ExerciseDetailContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ExerciseDetailPresenter (
    val view: ExerciseDetailContract.view,
    val repository: WorkoutHistoryRepository,
    val exerciseId: Int
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())  // برای async در Main thread


    // لود کردن لیست تاریخچه موقع باز شدن Fragment
    fun loadHistory() {
        coroutineScope.launch {
            try {
                view.showLoading()
                val historyFlow = repository.getHistoryForExercise(exerciseId)
                historyFlow.collect { list ->
                    view.hideLoading()
                    view.showHistory(list)  // آپدیت RecyclerView
                }
            } catch (e: Exception) {
                view.hideLoading()
                view.showError("خطا در لود: ${e.message}")
                Log.e("Presenter", "Load error", e)
            }
        }
    }

    // ذخیره inputها (از Fragment صدا زده می‌شه، مثلاً onSaveClicked)
    fun saveWorkout(reps: Int, weights: Int) {
        if (reps <= 0 || weights <= 0) {
            view.showError("تعداد تکرار و وزن باید مثبت باشه!")
            return
        }

        coroutineScope.launch {
            try {
                view.showLoading()
                repository.saveWorkout(exerciseId, reps, weights)
                view.hideLoading()
                view.showSuccess("تمرین ذخیره شد!")
                loadHistory()  // خودکار refresh لیست
            } catch (e: Exception) {
                view.hideLoading()
                view.showError("خطا در ذخیره: ${e.message}")
                Log.e("Presenter", "Save error", e)
            }
        }
    }

    // تمیز کردن (در onDestroy Fragment)
    fun onDestroy() {
        coroutineScope.cancel()
    }
}