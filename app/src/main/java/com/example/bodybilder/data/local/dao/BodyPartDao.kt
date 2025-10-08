package com.example.bodybilder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bodybilder.data.local.entity.BodyPartEntity



@Dao
interface BodyPartDao {
    @Insert
    suspend fun insert(bodyPart: BodyPartEntity)

    @Query("SELECT * FROM body_parts WHERE id = :id")
    suspend fun getById(id: Int): BodyPartEntity?

    @Query("SELECT * FROM body_parts")
    suspend fun getAll(): List<BodyPartEntity>
}