package com.example.bodybilder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bodybilder.data.local.dao.BodyPartDao
import com.example.bodybilder.data.local.dao.ExerciseDao
import com.example.bodybilder.data.local.dao.WorkoutHistoryDao
import com.example.bodybilder.data.local.entity.BodyPartEntity
import com.example.bodybilder.data.local.entity.ExerciseEntity
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity


@Database(entities = [BodyPartEntity::class, ExerciseEntity::class, WorkoutHistoryEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bodyPartDao(): BodyPartDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutHistoryDao(): WorkoutHistoryDao

}