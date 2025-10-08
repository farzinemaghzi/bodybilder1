package com.example.bodybilder

import android.app.Application
import androidx.room.Room
import com.example.bodybilder.data.local.AppDatabase




class BodyBuilderApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}