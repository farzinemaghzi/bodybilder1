package com.example.bodybilder.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return if (value.isEmpty()) emptyList() else value.split(",").mapNotNull { it.toIntOrNull() }
    }
}