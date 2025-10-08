package com.example.bodybilder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bodybilder.data.local.dao.BodyPartDao
import com.example.bodybilder.data.local.entity.BodyPartEntity


@Database(entities = [BodyPartEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bodyPartDao(): BodyPartDao
}