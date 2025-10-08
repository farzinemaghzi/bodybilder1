package com.example.bodybilder.data.local


import com.example.bodybilder.data.local.AppDatabase
import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "body_builder_database"
            )
                .build()
                .also { instance = it }
        }
    }
}