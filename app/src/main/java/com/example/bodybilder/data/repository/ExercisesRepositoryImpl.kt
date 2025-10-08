package com.example.bodybilder.data.repository

import android.content.Context
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.local.entity.ExerciseEntity
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory
import com.example.bodybilder.data.model.toExercises
import com.example.bodybilder.data.model.toWorkoutHistoryEntity
import com.example.bodybilder.exercises.contract.ExercisesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExercisesRepositoryImpl(private val context: Context) : ExercisesContract.Repository {
    private val exerciseDao = DatabaseProvider.getDatabase(context).exerciseDao()

    override suspend fun getExercises(bodyPartId: Int): List<Exercises> {
        return withContext(Dispatchers.IO) {
            exerciseDao.getByBodyPartId(bodyPartId).map { it.toExercises() }
        }
    }

    override suspend fun insertInitialData() {
        withContext(Dispatchers.IO) {
            if (exerciseDao.getAll().isEmpty()) {
                val initialExercises = listOf(
                    // Chest (bodyPartId = 1)
                    ExerciseEntity(
                        name = "Bench Press",
                        reps = 10,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 1,
                        description = "Bench press is a classic exercise for building chest strength and mass. It" +
                                "Bench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. ItBench press is a classic exercise for building chest strength and mass. It involves lying on a bench and pressing a barbell or dumbbells up and down.",
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
                    // Back (bodyPartId = 2)
                    ExerciseEntity(
                        name = "Pull Up",
                        reps = 8,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 2,
                        description = "Pull-ups are a bodyweight exercise that targets the back muscles. They involve hanging from a bar and pulling your body up until your chin is above the bar.",
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
                        description = "Deadlifts are a compound exercise that targets the back, legs, and core. They involve lifting a barbell from the ground to hip level while keeping your back straight.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Legs (bodyPartId = 3)
                    ExerciseEntity(
                        name = "Squat",
                        reps = 12,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 3,
                        description = "Squats are a fundamental exercise for building leg strength and mass. They involve lowering your body by bending your knees and hips, then returning to a standing position.",
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
                        description = "Lunges are a lower body exercise that targets the legs and glutes. They involve stepping forward with one leg and lowering your body until both knees are bent at about 90 degrees.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Arms (bodyPartId = 4)
                    ExerciseEntity(
                        name = "Bicep Curl",
                        reps = 12,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 4,
                        description = "Bicep curls are an isolation exercise that targets the biceps. They involve holding a dumbbell in each hand and curling them towards your shoulders.",
                        videoUrl = "https://www.youtube.com/watch?v=0u8b1a2k3d4",
                        stepImages = "${R.drawable.team_member_4},${R.drawable.team_member_4},${R.drawable.team_member_4}",
                        isCustom = false
                    ),
                    // Shoulders (bodyPartId = 5)
                    ExerciseEntity(
                        name = "Shoulder Press",
                        reps = 10,
                        sets = 3,
                        imageResId = R.drawable.team_member_4,
                        bodyPartId = 5,
                        description = "Shoulder press is an exercise that targets the shoulders and triceps. It involves pressing a barbell or dumbbells overhead while standing or sitting.",
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
            exerciseDao.insertWorkoutHistory(history.toWorkoutHistoryEntity())
        }
    }



}