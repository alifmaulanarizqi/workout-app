package com.alifmaulanarizqi.workoutapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("SELECT * FROM 'history'")
    fun fetchAllHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT COUNT(id) FROM 'history'")
    suspend fun workoutCounter(): Int

    @Query("SELECT SUM(cal) FROM 'history'")
    suspend fun calCounter(): Double

    @Query("SELECT SUM(time) FROM 'history'")
    suspend fun timeCounter(): Int

}