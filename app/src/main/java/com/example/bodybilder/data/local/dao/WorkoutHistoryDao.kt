package com.example.bodybilder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutHistoryDao {
    // درج یک رکورد جدید (اگر id تکراری باشه، جایگزین کن)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: WorkoutHistoryEntity): Long  // برمی‌گردونه id جدید

    // درج لیست رکوردها
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(histories: List<WorkoutHistoryEntity>)

    // خواندن تاریخچه برای یک exercise خاص (مرتب‌شده بر اساس تاریخ descending)
    @Query("SELECT * FROM workout_history WHERE exerciseId = :exerciseId ORDER BY date DESC")
    fun getHistoryForExercise(exerciseId: Int): Flow<List<WorkoutHistoryEntity>>

    // خواندن همه تاریخچه‌ها (برای تست یا کلی)
    @Query("SELECT * FROM workout_history ORDER BY date DESC")
    fun getAllHistory(): Flow<List<WorkoutHistoryEntity>>

    // آپدیت یک رکورد
    @Update
    suspend fun update(history: WorkoutHistoryEntity)

    // حذف یک رکورد
    @Delete
    suspend fun delete(history: WorkoutHistoryEntity)

    // حذف همه رکوردهای یک exercise (مثل وقتی تمرین رو پاک می‌کنی)
    @Query("DELETE FROM workout_history WHERE exerciseId = :exerciseId")
    suspend fun deleteAllForExercise(exerciseId: Int)
}
