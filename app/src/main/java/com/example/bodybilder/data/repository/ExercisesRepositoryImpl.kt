package com.example.bodybilder.data.repository

import android.content.Context
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.local.dao.WorkoutHistoryDao  // **جدید: import برای DAO جدید**
import com.example.bodybilder.data.local.entity.ExerciseEntity
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory
import com.example.bodybilder.data.model.toExercises  // فرض: extension برای map به model
import com.example.bodybilder.data.model.toWorkoutHistoryEntity
import com.example.bodybilder.exercises.contract.ExercisesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ExercisesRepositoryImpl(private val context: Context) : ExercisesContract.Repository {
    private val exerciseDao = DatabaseProvider.getDatabase(context).exerciseDao()
    private val workoutHistoryDao = DatabaseProvider.getDatabase(context).workoutHistoryDao()  // **جدید: DAO برای history**

    override suspend fun getExercises(bodyPartId: Int): List<Exercises> {
        return withContext(Dispatchers.IO) {
            exerciseDao.getByBodyPartId(bodyPartId).map { it.toExercises() }
        }
    }

    override suspend fun insertInitialData() {
        withContext(Dispatchers.IO) {
            if (exerciseDao.getAll().isEmpty()) {
                val initialExercises = listOf(
                    // Chest (bodyPartId = 1) - ۲ تا مثال
                    ExerciseEntity(
                        name = "Bench Press",
                        reps = 10,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 1,
                        description = "Bench press is a classic exercise for building chest strength and mass. It involves lying on a bench and pressing a barbell or dumbbells up and down.",
                        videoUrl = "https://www.youtube.com/shorts/_FkbD0FhgVE",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    ExerciseEntity(
                        name = "Push Up",
                        reps = 15,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 1,
                        description = "Push-ups are a bodyweight exercise that targets the chest, shoulders, and triceps. They involve lowering and raising your body using your arms while keeping your body straight.",
                        videoUrl = "https://www.youtube.com/shorts/4Bc1tPaYkOo",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Back (bodyPartId = 2) - ۲ تا
                    ExerciseEntity(
                        name = "Pull Up",
                        reps = 8,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 2,
                        description = "Pull-ups target the back muscles. Hang from a bar and pull your body up until your chin is above the bar.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    ExerciseEntity(
                        name = "Deadlift",
                        reps = 6,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 2,
                        description = "Deadlifts target the back, legs, and core. Lift a barbell from the ground to hip level while keeping your back straight.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Legs (bodyPartId = 3) - ۲ تا
                    ExerciseEntity(
                        name = "Squat",
                        reps = 12,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 3,
                        description = "Squats build leg strength and mass. Lower your body by bending knees and hips, then stand up.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    ExerciseEntity(
                        name = "Lunges",
                        reps = 10,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 3,
                        description = "Lunges target legs and glutes. Step forward with one leg and lower until knees are at 90 degrees.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Arms (bodyPartId = 4) - ۱ تا
                    ExerciseEntity(
                        name = "Bicep Curl",
                        reps = 12,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 4,
                        description = "Bicep curls target the biceps. Curl dumbbells towards your shoulders.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Shoulders (bodyPartId = 5) - ۱ تا
                    ExerciseEntity(
                        name = "Shoulder Press",
                        reps = 10,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 5,
                        description = "Shoulder press targets shoulders and triceps. Press barbell or dumbbells overhead.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    )
                )
                initialExercises.forEach { exerciseDao.insert(it) }
            }
        }
    }

    override suspend fun saveWorkoutHistory(history: WorkoutHistory) {
        withContext(Dispatchers.IO) {
            val entity = history.toWorkoutHistoryEntity()  // فرض: extension function برای convert
            workoutHistoryDao.insert(entity)  // **اصلاح: از workoutHistoryDao استفاده کن**
        }
    }

    // **جدید: متد getWorkoutHistoryForExercise (فیلتر بر اساس exerciseId)**
    override suspend fun getWorkoutHistoryForExercise(exerciseId: Int): List<WorkoutHistoryEntity> {
        return withContext(Dispatchers.IO) {
            workoutHistoryDao.getHistoryForExercise(exerciseId).first()  // فیلتر در DAO: WHERE exerciseId = :exerciseId
        }
    }
}