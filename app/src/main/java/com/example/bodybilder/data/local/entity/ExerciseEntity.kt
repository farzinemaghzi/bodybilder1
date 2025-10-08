package com.example.bodybilder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val reps: Int,
    val sets: Int,
    val imageResId: Int,
    val bodyPartId: Int,
    val description: String,
    val videoUrl: String? = null,
    val imageUrl: Int? = null,
    val stepImages: String,
    val isCustom: Boolean = true
)