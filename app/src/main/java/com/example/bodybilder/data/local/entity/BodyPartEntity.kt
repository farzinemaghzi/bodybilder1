package com.example.bodybilder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "body_parts")
data class BodyPartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val exerciseCount: Int,
    val imageResId: Int
)