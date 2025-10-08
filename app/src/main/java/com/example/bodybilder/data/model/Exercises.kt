package com.example.bodybilder.data.model

import android.os.Parcelable
import com.example.bodybilder.data.local.entity.ExerciseEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class Exercises(
    val id: Int = 0, // برای ویرایش
    val name: String,
    val reps: Int,
    val sets: Int,
    val imageResId: Int, // URI عکس اصلی
    val description: String,
    val stepImages: List<Int>,
    val isCustom: Boolean = false
) : Parcelable

fun ExerciseEntity.toExercises(): Exercises {
    return Exercises(
        id = id,
        name = name,
        reps = reps,
        sets = sets,
        imageResId = imageResId,
        description = description,
        stepImages = stepImages.split(",").mapNotNull { it.toIntOrNull() },
        isCustom = isCustom
    )
}

fun Exercises.toEntity(bodyPartId: Int): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        reps = reps,
        sets = sets,
        imageResId = imageResId,
        bodyPartId = bodyPartId,
        description = description,
        stepImages = stepImages.joinToString(","),
        isCustom = isCustom
    )
}