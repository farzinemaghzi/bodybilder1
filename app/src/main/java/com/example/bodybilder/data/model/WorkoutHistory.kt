package com.example.bodybilder.data.model

import android.os.Parcelable
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkoutHistory(
    val id: Int = 0,
    val exerciseId: Int,
    val date: String, // فرمت: YYYY-MM-DD
    val reps: Int,
    val weights: Int
) : Parcelable

fun WorkoutHistoryEntity.toWorkoutHistory(): WorkoutHistory {
    return WorkoutHistory(
        id = id,
        exerciseId = exerciseId,
        date = date,
        reps = reps,
        weights = weights
    )
}

fun WorkoutHistory.toWorkoutHistoryEntity(): WorkoutHistoryEntity {
    return WorkoutHistoryEntity(
        id = id,
        exerciseId = exerciseId,
        date = date,
        reps = reps,
        weights = weights
    )
}

fun List<WorkoutHistoryEntity>.toWorkoutHistoryList(): List<WorkoutHistory> {
    return this.map { it.toWorkoutHistory() }
}