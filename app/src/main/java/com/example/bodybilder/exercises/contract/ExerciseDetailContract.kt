package com.example.bodybilder.exercises.contract

import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity

interface ExerciseDetailContract {
    interface view {
        fun showHistory(list: List<WorkoutHistoryEntity>)  // نمایش لیست در RecyclerView
        fun showLoading()  // مثلاً ProgressBar نشون بده
        fun hideLoading()
        fun showError(message: String)  // toast یا snackbar
        fun showSuccess(message: String)  // "ذخیره شد!"
    }

}